package id.co.ptn.hungrystock.ui.onboarding.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseFragment
import id.co.ptn.hungrystock.bases.WebViewFragment
import id.co.ptn.hungrystock.databinding.FragmentPageThreeBinding
import id.co.ptn.hungrystock.ui.onboarding.view_model.OnboardViewModel
import id.co.ptn.hungrystock.utils.Status
import id.co.ptn.hungrystock.utils.getDateMMMMddyyyy
import java.lang.Exception
import java.lang.StringBuilder

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class PageThreeFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentPageThreeBinding? = null
    private var viewModel: OnboardViewModel? = null

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
    ): View? {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_page_three, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[OnboardViewModel::class.java]
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this
        setObserve()
        apiGetOnboard()
    }

    private fun setObserve() {
        viewModel?.reqOnboardResponse()?.observe(viewLifecycleOwner){
            when(it.status) {
                Status.SUCCESS -> {
                    binding?.progressBar?.visibility = View.GONE
                    it.data?.data?.latestEvent?.let { event ->
                        event.photo_url?.let { photo ->
                            binding?.image?.let { i -> Glide.with(requireActivity()).load(photo).into(i) }
                        } ?: throw Exception("Something wrong")
                        event.title?.let { title -> viewModel?.setTitle(title) } ?: throw Exception("Something wrong")
                        event.event_date?.let { date ->
                            val stringBuilder = StringBuilder()
                            stringBuilder.append(getDateMMMMddyyyy(date))

                            try {
                                stringBuilder.append(" ")
                                event.event_hour_start?.let { start ->
                                    stringBuilder.append(start)
                                    stringBuilder.append(" - ")
                                } ?: throw Exception("Something wrong")
                                event.event_hour_end?.let { end ->
                                    stringBuilder.append(end)
                                    stringBuilder.append(" - ")
                                } ?: throw Exception("Something wrong")
                                stringBuilder.append(" WIB")
                            }catch (e: Exception){
                                e.printStackTrace()
                            }
                            viewModel?.setDate(stringBuilder.toString())
                        } ?: throw Exception("Something wrong")
                        event.speaker?.let { speaker -> viewModel?.setSpeaker(speaker) } ?: throw Exception("Something wrong")
                        event.content?.let { content ->
                            childFragmentManager.commit {
                                val bundle = Bundle()
                                bundle.putString("content", content)
                                bundle.putString("font_size","small")
                                add<WebViewFragment>(R.id.frame_web, null, bundle)
                            }
                        } ?: throw Exception("Something wrong")
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    binding?.progressBar?.visibility = View.GONE
                }
            }
        }
    }

    /**
     * Api
     * */

    private fun apiGetOnboard() {
        viewModel?.apiGetOnboard()
    }

}