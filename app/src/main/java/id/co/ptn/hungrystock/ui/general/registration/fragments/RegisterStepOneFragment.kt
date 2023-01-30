package id.co.ptn.hungrystock.ui.general.registration.fragments

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.techiness.progressdialoglibrary.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseFragment
import id.co.ptn.hungrystock.databinding.FragmentRegistrationStepOneBinding
import id.co.ptn.hungrystock.models.registration.RequestRegistrationStepOne
import id.co.ptn.hungrystock.models.registration.ResponseRegisterError
import id.co.ptn.hungrystock.ui.general.registration.RegistrationActivity
import id.co.ptn.hungrystock.ui.general.view_model.RegistrationViewModel
import id.co.ptn.hungrystock.utils.EmailValidation
import id.co.ptn.hungrystock.utils.Status
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class RegisterStepOneFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentRegistrationStepOneBinding
    private var viewModel: RegistrationViewModel? = null
    private var dialogLoad: ProgressDialog? = null

    var cal = Calendar.getInstance()
    var filePhotoProfile: File? = null
    var paramBirtDate = ""

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
        viewModel = ViewModelProvider(requireActivity())[RegistrationViewModel::class.java]
        init()
    }

    private fun init() {
//        binding.etFullName.setText("Akbar")
//        binding.etEmail.setText("romadhon28akbar@gmail.com")
//        binding.etWa.setText("082110735124")
//        binding.etPassword.setText("12345678")
//        binding.etConfPassword.setText("12345678")
        setObserve()
        initListener()
    }

    private fun initListener() {
        binding.cUpload.btAddPhoto.setOnClickListener { addPhotoButtonPressed() }
        binding.tvDateBorn.setOnClickListener { dateButtonPressed() }
        binding.btNext.setOnClickListener {
            nextButtonPressed()
        }
    }

    private fun setUserPhoto(uri: Uri) {
        val filePath = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = requireActivity().contentResolver.query(uri, filePath, null, null, null)
        c?.moveToFirst()
        val columnIndex: Int? = c?.getColumnIndex(filePath[0])
        val picturePath: String? = columnIndex?.let { c.getString(it) }
        c?.close()

        filePhotoProfile = File(picturePath!!)
        binding.cUpload.image.setImageURI(uri)
        binding.cUpload.photoError.text.text = ""
    }

    private fun setBirthDate() {
        val formatOutgoing = SimpleDateFormat("yyyy-MM-dd")
        val tz = TimeZone.getTimeZone("Asia/Jakarta")
        formatOutgoing.timeZone = tz
        val s = formatOutgoing.format(cal.time)
        paramBirtDate = s
        binding.tvDateBorn.text = s
    }

    private fun addPhotoButtonPressed() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.TIRAMISU){
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(), READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    openGallery()
                }
                shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE
                ) -> {
                    Log.d("STORAGE","Masuk result")
                    showRationalePermission()
                }
                else -> {
                    requestPermissionLauncher.launch(READ_EXTERNAL_STORAGE)
                }
            }
        } else {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, EXTERNAL_CONTENT_URI)
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
            if (filePhotoProfile != null){
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
                                                apiRegistrationStepOne()
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
            } else {
                showSnackBar(binding.container, requireActivity().getString(R.string.message_profile_photo_empty))
                binding.cUpload.photoError.text.text = requireActivity().getString(R.string.message_profile_photo_empty)
            }

        }catch (e: Exception){
            e.printStackTrace()
        }
    }


    private val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            setBirthDate()
        }

    private fun showRationalePermission() {
        binding.container.showSnackBar (
            binding.container,
            requireActivity().getString(R.string.permission_storage_required),
            Snackbar.LENGTH_INDEFINITE, "Ok") {
            requireContext().openAppSystemSettings()
        }
    }
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                openGallery()
            } else {
                showRationalePermission()
            }
        }


    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result?.data?.data?.let { uri ->
                setUserPhoto(uri)
            }
        }
    }

    private fun setErrorFromApi(errors: ResponseRegisterError) {

        try {
            if (errors.foto_profil.isNotEmpty()){
                binding.cUpload.photoError.text.text = errors.foto_profil[0]
            }
        }catch (e: Exception){
            e.printStackTrace()
            binding.cUpload.photoError.text.text = ""
        }

        try {
            if (errors.email.isNotEmpty()){
                binding.emailError.text.text = errors.email[0]
            }
        }catch (e: Exception){
            e.printStackTrace()
            binding.emailError.text.text = ""
        }

        try {
            if (errors.nomor_whatsapp.isNotEmpty()){
                binding.waError.text.text = errors.nomor_whatsapp[0]
            }
        }catch (e: Exception){
            e.printStackTrace()
            binding.waError.text.text = ""
        }
    }

    private fun setObserve() {
        viewModel?.reqRegisterResponse()?.observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    (requireActivity() as RegistrationActivity).changePage(1)
                    dialogLoad?.dismiss()
                }
                Status.LOADING -> {}
                Status.ERROR -> {
                    dialogLoad?.dismiss()
                    it.data?.errors?.let { e->
                        Log.d("ERROR", Gson().toJson(e))
                        setErrorFromApi(e)
                        showSnackBar(binding.container, "Registrasi gagal")
                    }
                }
            }
        }
    }
    /**
     * Api
     * */

    private fun apiRegistrationStepOne() {
        dialogLoad = dialogLoading()
        dialogLoad?.show()
        Handler(Looper.getMainLooper()).postDelayed({
            val file = filePhotoProfile
            val requestFile: RequestBody? = file?.let { it.asRequestBody("multipart/form-data".toMediaTypeOrNull()) }
            var fp: MultipartBody.Part? = null
            if (file != null)
            fp = MultipartBody.Part.createFormData("foto_profil", file.name, requestFile!!)

            val name = binding.etFullName.text.toString()
                .toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val tl = paramBirtDate.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val nw =
                binding.etWa.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val e =
                binding.etEmail.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val p = binding.etPassword.text.toString()
                .toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val cp = binding.etConfPassword.text.toString()
                .toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val s = "1".toRequestBody("multipart/form-data".toMediaTypeOrNull())

            if (fp != null) {
                (requireActivity() as RegistrationActivity).requestRegistrationStepOne = RequestRegistrationStepOne(
                    fp,name,nw,e,tl,p
                )
                viewModel?.apiRegistrationStepOne(s,fp, name, tl,nw,e,p, cp)
            }
        }, 1000)
    }


}