package id.co.ptn.hungrystock.ui.profile.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.FragmentExpiredDialogBinding
import id.co.ptn.hungrystock.helper.TextViewHelper
import id.co.ptn.hungrystock.utils.ColorUtils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ExpiredFragmentDialog : DialogFragment() {
    private var binding: FragmentExpiredDialogBinding? = null
    private var param1: String? = null
    private var param2: String? = null

    companion object {
        @JvmStatic
        fun newInstance() =
            ExpiredFragmentDialog().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun getTheme(): Int {
        return R.style.DialogThemeFullScreen
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_expired_dialog, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       init()
    }

    private fun init() {
        setView()
        setListener()
    }

    private fun setView() {
        renewalSteps().forEach {
            val textViewHelper = TextViewHelper(requireContext(), R.color.white)
            textViewHelper.setText(it)
            textViewHelper.setPadding(0,10,0,0)
            binding?.containerContent?.addView(textViewHelper.getText())
        }
    }

    private fun setListener() {
        binding?.toolbar?.setNavigationOnClickListener {
            dismiss()
        }
    }

    private fun renewalSteps(): MutableList<String>{
        val steps : MutableList<String> = mutableListOf()
        steps.add("1. Login di www.HungryStock.com")
        steps.add("2. Klik menu Account")
        steps.add("3. Scroll sampai bawah dan nanti ada tempat untuk Upload Bukti Transfer untuk Renewal")
        steps.add("4. Upload bukti transfernya")
        steps.add("5. Hasilnya akan dikonfirmasi di member profile di website HS")
        return steps
    }
}