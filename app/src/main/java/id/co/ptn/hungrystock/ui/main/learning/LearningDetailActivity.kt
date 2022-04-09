package id.co.ptn.hungrystock.ui.main.learning

import android.os.Bundle
import android.util.Log
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
import id.co.ptn.hungrystock.databinding.ActivityLearningDetailBinding
import id.co.ptn.hungrystock.models.main.home.PastEvent
import id.co.ptn.hungrystock.models.main.learning.Learning
import id.co.ptn.hungrystock.ui.main.learning.adapters.LearningListAdapter
import id.co.ptn.hungrystock.ui.main.learning.viewmodel.LearningDetailViewModel
import id.co.ptn.hungrystock.utils.Status

@AndroidEntryPoint
class LearningDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityLearningDetailBinding
    private val viewModel: LearningDetailViewModel by viewModels()
    private lateinit var learningListAdapter: LearningListAdapter

    private var event: PastEvent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_learning_detail)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        init()
    }

    private fun init() {
        changeStatusBar()
        setToolbar(binding.toolbar,getString(R.string.title_learning_detail))
        initIntent()
        apiGetLearningDetail()
        setObserve()
        initList()
    }

    private fun initIntent() {
        intent.extras?.let {
            if (it.containsKey("event")){
                event = Gson().fromJson(it.getString("event"), PastEvent::class.java)
            }
        }
       }

    private fun setView() {
        viewModel.reqLearningDetailResponse().value?.data?.data?.let { v ->
            v.learning.title?.let { t -> viewModel.setTitle(t)}
            v.learning.speaker?.let { sp -> viewModel.setSubTitle("Bersama $sp")  }
            v.learning.content?.let { c ->
                supportFragmentManager.commit {
                    val bundle = Bundle()
                    bundle.putString("content", c)
                    add<WebViewFragment>(R.id.frame_desc, null, bundle)
                }
            }
        }
    }

    private fun initList() {
        learningListAdapter = LearningListAdapter(viewModel.getLearnings(), object : LearningListAdapter.LearningListener{
            override fun itemClicked(learning: Learning) {

            }
        })
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@LearningDetailActivity, 2)
            adapter = learningListAdapter
        }
    }

    private fun setObserve() {
        viewModel.reqLearningDetailResponse().observe(this){
            when(it.status){
                Status.SUCCESS -> {
                    viewModel.setLoadingReqDetail(false)
                    setView()
                }
                Status.LOADING -> {

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

    private fun apiGetLearningDetail() {
        event?.slug?.let { viewModel.apiGetLearningDetail(it) }
    }
}