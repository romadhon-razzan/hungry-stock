package id.co.ptn.hungrystock.ui.main.learning

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.bases.WebViewFragment
import id.co.ptn.hungrystock.core.network.RunningServiceType
import id.co.ptn.hungrystock.core.network.running_service
import id.co.ptn.hungrystock.databinding.ActivityLearningDetailBinding
import id.co.ptn.hungrystock.models.main.home.ResponseEventsData
import id.co.ptn.hungrystock.ui.main.learning.adapters.SimillarLearningListAdapter
import id.co.ptn.hungrystock.ui.main.learning.viewmodel.LearningDetailViewModel
import id.co.ptn.hungrystock.utils.MediaUtils
import id.co.ptn.hungrystock.utils.Status

@AndroidEntryPoint
class LearningDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityLearningDetailBinding
    private val viewModel: LearningDetailViewModel by viewModels()

    private lateinit var learningListAdapter: SimillarLearningListAdapter

    private var event: ResponseEventsData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_learning_detail)
        binding.vm = viewModel
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        binding.lifecycleOwner = this
        init()
    }

    private fun init() {
        changeStatusBar()
        setToolbar(binding.toolbar,getString(R.string.title_learning_detail))
        initIntent()
        setListener()
        setObserve()
        setView()
        apiGetEventsRelated()
    }

    private fun initIntent() {
        intent.extras?.let {
            if (it.containsKey("event")){
                event = Gson().fromJson(it.getString("event"), ResponseEventsData::class.java)
            }
        }
       }

    private fun setView() {
        MediaUtils(this).setImageFromUrl(binding.image, event?.image_file ?: "", R.drawable.img_event_placeholder)
        viewModel.setTitle(event?.title ?: "")
        viewModel.setSubTitle("Bersama ${event?.speakers ?: "-"}")
        supportFragmentManager.commit {
            val bundle = Bundle()
            bundle.putString("content", event?.description ?: "")
            add<WebViewFragment>(R.id.frame_desc, null, bundle)
        }
        initList()
    }

    private fun setListener() {
        binding.imagePlay.setOnClickListener {
            val intent = router.toPlayVideo()
            intent.putExtra("url", event?.video_file ?: "")
            startActivity(intent)
        }
    }

    private fun initList() {
        learningListAdapter = SimillarLearningListAdapter(viewModel.getEventsRelated(), object : SimillarLearningListAdapter.LearningListener{
            override fun itemClicked(learning: ResponseEventsData) {
                try {
                    val intent = router.toLearningDetail()
                    intent.putExtra("event", Gson().toJson(learning))
                    startActivity(intent)
                }catch (e: Exception){
                    e.printStackTrace()
                    showSnackBar(binding.container, resources.getString(R.string.message_system_wrong))
                }
            }
        })
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@LearningDetailActivity, 2)
            adapter = learningListAdapter
        }
    }


    private fun setObserve() {
        viewModel.reqOtpResponse().observe(this){
            when(it.status){
                Status.SUCCESS -> {
                    viewModel.setLoadingReqDetail(false)
                    if (running_service == RunningServiceType.EVENT_RELATED){
                        viewModel.apiGetEventsRelated(event?.code ?: "", it.data?.data ?: "")
                    }
                }
                Status.LOADING -> {
                    viewModel.setLoadingReqDetail(true)
                }
                Status.ERROR -> {
                    viewModel.setLoadingReqDetail(false)
                }
            }
        }
        viewModel.reqEventsRelatedResponse().observe(this){
            when(it.status){
                Status.SUCCESS -> {
                    viewModel.setLoadingReqDetail(false)
                    if (it.data?.successData?.isNotEmpty() == true){
                        val responseEventsRelatedData = it.data.successData[0]
                        viewModel.getEventsRelated().clear()
                        viewModel.getEventsRelated().addAll(responseEventsRelatedData.data)
                        initList()
                    }
                }
                Status.LOADING -> {
                    viewModel.setLoadingReqDetail(true)
                }
                Status.ERROR -> {
                    viewModel.setLoadingReqDetail(false)
                }
            }
        }
    }


    /**
     * Api
     * */

    private fun apiGetEventsRelated() {
        running_service = RunningServiceType.EVENT_RELATED
        viewModel.apiGetOtp()
    }
}