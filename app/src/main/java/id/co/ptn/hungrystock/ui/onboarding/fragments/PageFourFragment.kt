package id.co.ptn.hungrystock.ui.onboarding.fragments

import android.os.Bundle
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
import id.co.ptn.hungrystock.databinding.FragmentPageFourBinding
import id.co.ptn.hungrystock.ui.onboarding.view_model.OnboardViewModel
import id.co.ptn.hungrystock.utils.getDateMMMMddyyyy
import java.lang.StringBuilder

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class PageFourFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentPageFourBinding
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
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()),R.layout.fragment_page_four, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[OnboardViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setView()
    }

    private fun setView() {
        viewModel?.reqOnboardResponse()?.value?.data?.data?.headlineWebinar?.let { webinar ->
            webinar.photo_url?.let { photo ->
                binding.image.let { i -> Glide.with(requireActivity()).load(photo).into(i) }
            }
            webinar.title?.let { title -> binding.tvTitle.text = title }

            val stringBuilder = StringBuilder()
            webinar.date_start?.let { date ->
                stringBuilder.append(getDateMMMMddyyyy(date))
            }

            webinar.date_end?.let { date ->
                stringBuilder.append(" - ")
                stringBuilder.append(getDateMMMMddyyyy(date))
            }
            binding.tvDate.text = stringBuilder.toString()
            webinar.speaker?.let { speaker -> binding.tvSpeaker.text = speaker }
            webinar.content?.let { content ->
                childFragmentManager.commit {
                    val bundle = Bundle()
                    bundle.putString("content", content)
                    bundle.putString("font_size","small")
                    add<WebViewFragment>(R.id.frame_web, null, bundle)
                }
            }
        }
    }
}