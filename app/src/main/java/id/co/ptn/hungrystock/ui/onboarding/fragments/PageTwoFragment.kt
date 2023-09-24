package id.co.ptn.hungrystock.ui.onboarding.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseFragment
import id.co.ptn.hungrystock.config.MEMBERSHIP
import id.co.ptn.hungrystock.databinding.FragmentPageTwoBinding
import id.co.ptn.hungrystock.models.OnboardPageTwo
import id.co.ptn.hungrystock.ui.onboarding.adapters.PageTwoAdapter


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class PageTwoFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var binding: FragmentPageTwoBinding? = null
    private var items: MutableList<OnboardPageTwo> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_page_two, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        val wordToSpan: Spannable =
            SpannableString("Informasi dan registrasi sebagai member, lebih lanjut bisa melalui www.hungrystock.com/membership")
        wordToSpan.setSpan(ForegroundColorSpan(Color.BLUE), 67, 97, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding?.tvMoreInfo?.text = wordToSpan
        initListener()
        initList()
    }

    private fun initListener() {
        binding?.cardAnnualFee?.setOnClickListener {
            openUrlPage(MEMBERSHIP, requireContext())
        }
    }

    private fun initList() {
        items.clear()
        items.add(OnboardPageTwo("Top Up Knowledge and Wisdom", "Belajar mengenai skill, wisdom dan psikologi investasi."))
        items.add(OnboardPageTwo("Temu Emiten", "Bertemu langsung dengan jajaran direksi dari sebuah emiten, mengenal dan mempelajari lebih jauh tentang sebuah emiten."))
        items.add(OnboardPageTwo("Bedah Emiten", "Mengenal dan mempelajari lebih jauh tentang sebuah emiten dari kacamata tim HUNGRYSTOCK."))
        items.add(OnboardPageTwo("STOCKSCOPE", "Update kondisi makro ekonomi dan event-event penting lainnya yang mempengaruhi pasar saham."))
        items.add(OnboardPageTwo("Belajar Invest Bareng", "Belajar dan mengikuti kompetisi internal yang diadakan HUNGRYSTOCK mengenai bagaimana menganalisa suatu emiten sebelum memutuskan untuk berinvestasi."))
        items.add(OnboardPageTwo("Research & Data", "Mendapatkan tools untuk screening emiten yang layak diinvestasi. File akan diupdate berkala secara periodik."))

        val pAdapter = PageTwoAdapter(items)
        binding?.recyclerView?.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = pAdapter
        }
    }



    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PageOneFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}