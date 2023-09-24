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
import id.co.ptn.hungrystock.core.network.RunningServiceType
import id.co.ptn.hungrystock.core.network.running_service
import id.co.ptn.hungrystock.databinding.FragmentPageFiveBinding
import id.co.ptn.hungrystock.helper.extension.isValidUrl
import id.co.ptn.hungrystock.models.landing.ResponseBooksData
import id.co.ptn.hungrystock.models.onboard.Books
import id.co.ptn.hungrystock.router.Router
import id.co.ptn.hungrystock.ui.onboarding.adapters.BookListAdapter
import id.co.ptn.hungrystock.ui.onboarding.view_model.BooksViewModel
import id.co.ptn.hungrystock.ui.onboarding.view_model.OnboardViewModel
import id.co.ptn.hungrystock.utils.Status

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class PageFiveFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentPageFiveBinding
    private var viewModel: BooksViewModel? = null
    private var onboardViewModel: OnboardViewModel? = null
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
        viewModel = ViewModelProvider(requireActivity())[BooksViewModel::class.java]
        onboardViewModel = ViewModelProvider(requireActivity())[OnboardViewModel::class.java]
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
        binding.lifecycleOwner = this
        init()
    }

    private fun init() {
        initListener()
        setObserve()
    }

    private fun initListener() {
        binding.btLoginRegister.setOnClickListener { Router(requireContext()).toAuth() }
    }

    private fun initList() {
        bookListAdapter = BookListAdapter(viewModel?.books ?: mutableListOf(), object : BookListAdapter.Listener{
            override fun itemClicked(books: ResponseBooksData) {
                books.tokopediaUrl?.let { url ->
                    if (url.isNotEmpty() && url.isValidUrl()) {
                        openUrlPage(url, requireContext())
                    }
                }
            }
        })
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = bookListAdapter
        }
    }

    private fun setObserve() {
        viewModel?.reqOtpResponse()?.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (running_service == RunningServiceType.BOOKS){
                        viewModel?.apiGetBooks(it.data?.data ?: "")
                    }
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
        viewModel?.reqBooksResponse()?.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.data?.let { items ->
                        viewModel?.books?.clear()
                        viewModel?.books?.addAll(items)
                        initList()
                    }
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
        onboardViewModel?.pageBooks?.observe(requireActivity()){
            if (it){
                if (viewModel?.books?.isEmpty() == true) {
                    apiGetBooks()
                }
            }
        }
    }


    /**
     * Api
     * */
    private fun apiGetBooks() {
        running_service = RunningServiceType.BOOKS
        viewModel?.apiGetOtp()
    }
}