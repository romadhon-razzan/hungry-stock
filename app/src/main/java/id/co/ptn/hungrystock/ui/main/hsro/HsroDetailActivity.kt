package id.co.ptn.hungrystock.ui.main.hsro

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseActivity
import id.co.ptn.hungrystock.databinding.ActivityHsroDetailBinding
import id.co.ptn.hungrystock.models.main.home.ResponseEventsData
import id.co.ptn.hungrystock.models.main.hsro.HsroIndicator
import id.co.ptn.hungrystock.models.main.learning.Learning
import id.co.ptn.hungrystock.models.main.research.ResearchReport
import id.co.ptn.hungrystock.models.main.research.ResearchReportData
import id.co.ptn.hungrystock.ui.main.hsro.adapters.HsroIndicatorAdapter
import id.co.ptn.hungrystock.ui.main.learning.adapters.LearningListAdapter
import id.co.ptn.hungrystock.ui.main.research.adapters.ResearchReportListAdapter
import id.co.ptn.hungrystock.ui.main.viewmodel.HsroDetailViewModel

@AndroidEntryPoint
class HsroDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityHsroDetailBinding
    private val viewModel: HsroDetailViewModel by viewModels()

    private lateinit var hsroIndicatorAdapter: HsroIndicatorAdapter
    private var indicators: MutableList<HsroIndicator> = mutableListOf()

    private lateinit var learningListAdapter: LearningListAdapter
    private var learnings: MutableList<ResponseEventsData> = mutableListOf()

    private lateinit var researchReportListAdapter: ResearchReportListAdapter
    private var researchReports: MutableList<ResearchReportData> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hsro_detail)
        init()
    }

    private fun init() {
        changeStatusBar()
        setToolbar(binding.toolbar,"JRPT")
        initIntent()
        initIndicatorData()
        initIndicator()
        setConclusion()

        initVideoData()
        iniVideoList()

        initResearchData()
        initResearchList()
    }

    private fun initIntent() {

    }

    private fun initIndicatorData() {
        indicators.clear()
        indicators.add(HsroIndicator("P","JRPT memiliki prospek yang menaarik dimana relaksasi pajak serta keuntungan demografis (banyak milenial) yg akan mencari rumah akan meningkatkan kinerja dari JRPT kedepan. Serta kecerdasan manajemen JRPT untuk bermain khususnya di kalangan milenial/daya beli menengah bawah) akan menjadi salah satu keunggulan JRPT dibandingkan emiten property lainnya."))
        indicators.add(HsroIndicator("B","Balance Sheet yang kuat dimana total aset dibandingkan total liabilitasnya adalah 3.1x dana DER nya hanya 45%"))
        indicators.add(HsroIndicator("V","Valuasi yang cukup murah yakni PBV dibawah 1 PE di bawa 10; EV/EBITDA di bawah 7x"))
        indicators.add(HsroIndicator("CG","JRPT memiliki Corporate Governance yang baik, belum ada satupun kasus yang negatif baik dari segi manajemen maupun perusahaan. Serta termasuk perusahaan yang rajin melakukan buy back saham dan rajin bagi dividen."))
        indicators.add(HsroIndicator("R","Risk terbesar lebih dikarenakan faktor yang tidak bisa di kontrol yakni ketidakpastian pandemi corona"))
    }

    private fun setConclusion() {
        binding.tvConclusion.text = "“Dari kesemuanya menurut saya JRPT merupakan salah satu emiten yang layak invest.”"
    }

    private fun initIndicator() {
        hsroIndicatorAdapter = HsroIndicatorAdapter(indicators)
        binding.recyclerViewIndicator.apply {
            layoutManager = LinearLayoutManager(this@HsroDetailActivity)
            adapter = hsroIndicatorAdapter
        }
    }


    private fun initVideoData() {
        learnings.clear()
//        learnings.add(0, Learning("This is the item title with supposed a long line","1:02:05"))
//        learnings.add(1, Learning("This is the item title with supposed a long line","1:02:05"))
//        learnings.add(2, Learning("This is the item title with supposed a long line","1:02:05"))
    }

    private fun iniVideoList() {
        learningListAdapter = LearningListAdapter(learnings, object : LearningListAdapter.LearningListener{
            override fun itemClicked(learning: ResponseEventsData) {

            }

        })
        binding.recyclerViewVideo.apply {
            layoutManager = GridLayoutManager(this@HsroDetailActivity, 2)
            adapter = learningListAdapter
        }
    }


    private fun initResearchData() {
        researchReports.clear()
//        researchReports.add(ResearchReport("A","SRTG_HungryStock_StockData_020921"))
//        researchReports.add(ResearchReport("A","SRTG_HungryStock_StockData_020921"))
//        researchReports.add(ResearchReport("A","SRTG_HungryStock_StockData_020921"))
    }

    private fun initResearchList() {
        researchReportListAdapter = ResearchReportListAdapter(supportFragmentManager,researchReports)
        binding.recyclerViewResearch.apply {
            layoutManager = GridLayoutManager(this@HsroDetailActivity, 2)
            adapter = researchReportListAdapter
        }
    }
}