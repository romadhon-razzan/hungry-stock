package id.co.ptn.hungrystock.ui.onboarding.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseFragment
import id.co.ptn.hungrystock.databinding.FragmentPageFiveBinding
import id.co.ptn.hungrystock.models.onboard.Books
import id.co.ptn.hungrystock.router.Router
import id.co.ptn.hungrystock.ui.onboarding.adapters.BookListAdapter
import id.co.ptn.hungrystock.ui.onboarding.view_model.OnboardViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class PageFiveFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentPageFiveBinding
    private var viewModel: OnboardViewModel? = null
    private var bookListAdapter: BookListAdapter? = null


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
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()),R.layout.fragment_page_five, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[OnboardViewModel::class.java]
        binding.lifecycleOwner = this
        init()
    }

    private fun init() {
        initList()
        initListener()
    }

    private fun initListener() {
        binding.btLoginRegister.setOnClickListener { Router(requireContext()).toAuth() }
    }

    private fun initList() {
        viewModel?.reqOnboardResponse()?.value?.data?.data?.books?.let { books ->
            if (books.isNotEmpty()){
                bookListAdapter = BookListAdapter(books as MutableList<Books>, object : BookListAdapter.Listener{
                    override fun itemClicked(books: Books) {
                        books.link_tokopedia?.let { url -> openUrlPage(url) }
                    }
                })
                binding.recyclerView.apply {
                    layoutManager = GridLayoutManager(requireContext(), 2)
                    adapter = bookListAdapter
                }
            }
        }
    }
}