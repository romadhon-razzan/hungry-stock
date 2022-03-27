package id.co.ptn.hungrystock.ui.general.registration.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseFragment
import id.co.ptn.hungrystock.config.SUCCESS
import id.co.ptn.hungrystock.databinding.FragmentPageOneBinding
import id.co.ptn.hungrystock.databinding.FragmentRegistrationStepOneBinding
import id.co.ptn.hungrystock.router.Router
import id.co.ptn.hungrystock.ui.general.registration.RegistrationActivity
import id.co.ptn.hungrystock.utils.EmailValidation
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class RegisterStepOneFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentRegistrationStepOneBinding

    var cal = Calendar.getInstance()

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterStepOneFragment().apply {
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
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_registration_step_one, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initListener()
    }

    private fun initListener() {
        binding.cUpload.btAddPhoto.setOnClickListener { addPhotoButtonPressed() }
        binding.tvDateBorn.setOnClickListener { dateButtonPressed() }
        binding.btNext.setOnClickListener {
            (requireActivity() as RegistrationActivity).changePage(1)
//            nextButtonPressed()
        }
    }

    private fun setUserPhoto(uri: Uri) {
        binding.cUpload.image.setImageURI(uri)
    }

    private fun setBirthDate() {
        val formatOutgoing = SimpleDateFormat("yyyy-MM-dd")
        val tz = TimeZone.getTimeZone("Asia/Jakarta")
        formatOutgoing.timeZone = tz
        val s = formatOutgoing.format(cal.time)
        binding.tvDateBorn.text = s
    }

    private fun addPhotoButtonPressed() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    private fun dateButtonPressed() {
        DatePickerDialog(requireContext(),
            dateSetListener,
            // set DatePickerDialog to point to today's date when it loads up
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show()

    }

    private fun nextButtonPressed() {
        try {
            if (binding.etFullName.text.toString().isNotEmpty()){
                binding.nameError.text.text = ""
                if (binding.tvDateBorn.text.toString().isNotEmpty()){
                    binding.bornError.text.text = ""
                    if (binding.etWa.text.toString().isNotEmpty()){
                        binding.waError.text.text = ""
                        if (binding.etEmail.text.toString().isNotEmpty()){
                            binding.emailError.text.text = ""
                            if (EmailValidation.check(binding.etEmail.text.toString())){
                                binding.emailError.text.text = ""
                                if (binding.etPassword.text.toString().isNotEmpty()){
                                    binding.passwordError.text.text = ""
                                    if (binding.etPassword.text.toString().length >= 8){
                                        binding.passwordError.text.text = ""
                                        if (binding.etConfPassword.text.toString() == binding.etPassword.text.toString()){
                                            binding.confirmPasswordError.text.text = ""
                                            (requireActivity() as RegistrationActivity).changePage(1)
                                        } else {
                                            showSnackBar(binding.container, requireActivity().getString(R.string.message_password_not_matches))
                                            binding.confirmPasswordError.text.text = requireActivity().getString(R.string.message_password_not_matches)
                                        }
                                    } else {
                                        showSnackBar(binding.container, requireActivity().getString(R.string.message_min_8_character))
                                        binding.passwordError.text.text = requireActivity().getString(R.string.message_min_8_character)
                                    }
                                } else {
                                    showSnackBar(binding.container, requireActivity().getString(R.string.message_all_field_must_fill))
                                    binding.passwordError.text.text = requireActivity().getString(R.string.message_need_fill)
                                }
                            } else {
                                showSnackBar(binding.container, requireActivity().getString(R.string.message_email_invalid))
                                binding.emailError.text.text = requireActivity().getString(R.string.message_email_invalid)
                            }
                        } else {
                            showSnackBar(binding.container, requireActivity().getString(R.string.message_all_field_must_fill))
                            binding.emailError.text.text = requireActivity().getString(R.string.message_need_fill)
                        }
                    } else {
                        showSnackBar(binding.container, requireActivity().getString(R.string.message_all_field_must_fill))
                        binding.waError.text.text = requireActivity().getString(R.string.message_need_fill)
                    }
                } else {
                    showSnackBar(binding.container, requireActivity().getString(R.string.message_all_field_must_fill))
                    binding.bornError.text.text = requireActivity().getString(R.string.message_need_fill)
                }
            } else {
                showSnackBar(binding.container, requireActivity().getString(R.string.message_all_field_must_fill))
                binding.nameError.text.text = requireActivity().getString(R.string.message_need_fill)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }


    val dateSetListener = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                               dayOfMonth: Int) {
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            setBirthDate()
        }
    }


    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result?.data?.data?.let { uri ->
                setUserPhoto(uri)
            }
        }
    }


}