package id.co.ptn.hungrystock.ui.main.learning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.databinding.ActivityLearningDetailBinding
import id.co.ptn.hungrystock.models.main.learning.Learning
import id.co.ptn.hungrystock.ui.main.learning.adapters.LearningListAdapter
import id.co.ptn.hungrystock.ui.main.learning.viewmodel.LearningDetailViewModel
import id.co.ptn.hungrystock.ui.main.learning.viewmodel.LearningViewModel

@AndroidEntryPoint
class LearningDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityLearningDetailBinding
    private val viewModel: LearningDetailViewModel by viewModels()
    private lateinit var learningListAdapter: LearningListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_learning_detail)
        binding.vm = viewModel
        init()
    }

    private fun init() {
        changeStatusBar()
        setToolbar(binding.toolbar,getString(R.string.title_learning_detail))
        initIntent()
        initData()
        initList()
    }

    private fun initIntent() {
        viewModel.setTitle("This is the item title with supposed a long line")
        viewModel.setSubTitle("Latest Learning Materials")
        viewModel.setDescription("HUNGRYSTOCK adalah komunitas investor saham Indonesia yang didirikan oleh Lukas Setia Atmaja tahun 2019. Visi Hungrystock adalah menciptakan investor saham yang memiliki knowledge, skill, dan wisdom, yang merupakan unci sukses dalam berinvestasi saham. Fokus strategi investasi HUNGRYSTOCK adalah value growth investing.")
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

    private fun initData() {
        viewModel.getLearnings().clear()
        viewModel.getLearnings().add(0, Learning("This is the item title with supposed a long line","1:02:05"))
        viewModel.getLearnings().add(1, Learning("This is the item title with supposed a long line","1:02:05"))
        viewModel.getLearnings().add(2, Learning("This is the item title with supposed a long line","1:02:05"))
        viewModel.getLearnings().add(3, Learning("This is the item title with supposed a long line","1:02:05"))
    }

    /**
     * Api
     * */
}