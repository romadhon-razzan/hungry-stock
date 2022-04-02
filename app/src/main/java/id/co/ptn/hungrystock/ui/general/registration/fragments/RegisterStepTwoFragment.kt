package id.co.ptn.hungrystock.ui.general.registration.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.techiness.progressdialoglibrary.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseFragment
import id.co.ptn.hungrystock.databinding.FragmentRegistrationStepTwoBinding
import id.co.ptn.hungrystock.models.registration.MainRegistration
import id.co.ptn.hungrystock.models.registration.MainRegistration.Companion.DOMISILI
import id.co.ptn.hungrystock.models.registration.MainRegistration.Companion.LONGINVEST
import id.co.ptn.hungrystock.models.registration.MainRegistration.Companion.PENDIDIKAN
import id.co.ptn.hungrystock.models.registration.MainRegistration.Companion.PORTOFOLIO
import id.co.ptn.hungrystock.models.registration.MainRegistration.Companion.PROFESI
import id.co.ptn.hungrystock.models.registration.RegistrationItem
import id.co.ptn.hungrystock.models.registration.ResponseRegisterError
import id.co.ptn.hungrystock.ui.general.registration.RegistrationActivity
import id.co.ptn.hungrystock.ui.general.registration.adapters.RegistrationStepTwoAdapter
import id.co.ptn.hungrystock.ui.general.view_model.RegistrationViewModel
import id.co.ptn.hungrystock.utils.Status
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class RegisterStepTwoFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentRegistrationStepTwoBinding
    private var viewModel: RegistrationViewModel? = null
    private var dialogLoad: ProgressDialog? = null
    private lateinit var registrationStepTwoAdapter: RegistrationStepTwoAdapter
    private var items: MutableList<MainRegistration> = mutableListOf()
    private lateinit var activity: RegistrationActivity
    var fileProofPayment: File? = null

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterStepTwoFragment().apply {
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
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.fragment_registration_step_two, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
        init()
    }

    private fun init() {
        activity = (requireActivity() as RegistrationActivity)
        setObserve()
        initListener()
        initListData()
        initList()
    }

    private fun initListener() {
        binding.btAddPhoto.setOnClickListener { addPhotoButtonPressed() }
        binding.btRegister.setOnClickListener {
           registerPressed()
        }
        binding.btBack.setOnClickListener { activity.changePage(0) }
    }

    private fun initList() {
        registrationStepTwoAdapter = RegistrationStepTwoAdapter(items, object: RegistrationStepTwoAdapter.Listener{
            override fun onOtherField(position: Int, value: String) {
                items[position].value = value
            }
        })
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = registrationStepTwoAdapter
        }
    }

    private fun initListData() {
        val domisili: MutableList<RegistrationItem> = mutableListOf()
        domisili.add(RegistrationItem("JABODETABEK", false))
        domisili.add(RegistrationItem("Yang lain", false))
        items.add(MainRegistration(DOMISILI,"Domisili", domisili,"", false, false))

        val longInvest: MutableList<RegistrationItem> = mutableListOf()
        longInvest.add(RegistrationItem("<1 Tahun", false))
        longInvest.add(RegistrationItem("2 - 5 Tahun", false))
        longInvest.add(RegistrationItem("Diatas 5 Tahun", false))
        items.add(MainRegistration(LONGINVEST,"Sudah berapa lama investasi saham?",longInvest,"", false,
            otherActive = false
        ))

        val profesi: MutableList<RegistrationItem> = mutableListOf()
        profesi.add(RegistrationItem("Pengusaha", false))
        profesi.add(RegistrationItem("Profesional", false))
        profesi.add(RegistrationItem("Pensiunan", false))
        profesi.add(RegistrationItem("Mahasiswa / Pelajar", false))
        profesi.add(RegistrationItem("Yang lain", false))
        items.add(MainRegistration(PROFESI,"Profesi anda saat ini?",profesi,"", false,
            otherActive = false
        ))

        val pendidikan: MutableList<RegistrationItem> = mutableListOf()
        pendidikan.add(RegistrationItem("Akuntansi/Keuangan", false))
        pendidikan.add(RegistrationItem("Ekonomi/Manajemen Non-Keuangan", false))
        pendidikan.add(RegistrationItem("Teknik", false))
        pendidikan.add(RegistrationItem("Hukum", false))
        pendidikan.add(RegistrationItem("Psikologi", false))
        pendidikan.add(RegistrationItem("Kedokteran atau Kesehatan", false))
        pendidikan.add(RegistrationItem("Ilmu Komputer", false))
        pendidikan.add(RegistrationItem("Ilmu Komunikasi", false))
        pendidikan.add(RegistrationItem("Yang lain", false))
        items.add(MainRegistration(PENDIDIKAN,"Latar belakang pendidikan",pendidikan,"", false,
            otherActive = false
        ))

        val portfolio: MutableList<RegistrationItem> = mutableListOf()
        portfolio.add(RegistrationItem("< Rp 250 juta", false))
        portfolio.add(RegistrationItem("Rp 250 juta - 500 juta", false))
        portfolio.add(RegistrationItem("Rp 500 juta - 1 miliar", false))
        portfolio.add(RegistrationItem("Rp 1 - 2.5 miliar", false))
        portfolio.add(RegistrationItem("Diatas Rp 2.5 miliar", false))
        items.add(MainRegistration(PORTOFOLIO,"Ukuran Portfolio Investasi Saham (untuk keperluan temu emiten)",portfolio,"", false,
            otherActive = false
        ))
    }

    private fun setProofPaymentPhoto(uri: Uri) {
        val filePath = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = requireActivity().contentResolver.query(uri, filePath, null, null, null)
        c?.moveToFirst()
        val columnIndex: Int? = c?.getColumnIndex(filePath[0])
        val picturePath: String? = columnIndex?.let { c.getString(it) }
        c?.close()

        fileProofPayment = File(picturePath!!)
        binding.image.setImageURI(uri)
        binding.cardProofPayment.visibility = View.VISIBLE
        binding.error.text.text = ""
    }

    private fun addPhotoButtonPressed() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                openGallery()
            }
            shouldShowRequestPermissionRationale(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                showRationalePermission()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    private fun registerPressed() {
        var domisili = ""
        var lamaInvestasi = ""
        var profesi = ""
        var pendidikan = ""
        var portofolio = ""

        var totalError = 0
        items.forEachIndexed { index, _ ->
            if (items[index].value.isEmpty())
                totalError += 1
            else {
                when(items[index].id){
                    DOMISILI -> domisili = items[index].value
                    LONGINVEST -> lamaInvestasi = items[index].value
                    PROFESI -> profesi = items[index].value
                    PENDIDIKAN -> pendidikan = items[index].value
                    PORTOFOLIO -> portofolio = items[index].value
                }
            }
            items[index].error = items[index].value.isEmpty()
            registrationStepTwoAdapter.notifyItemChanged(index)
        }

        if (binding.cardProofPayment.visibility == View.GONE) {
            totalError += 1
            binding.error.text.text = requireActivity().getString(R.string.message_attach_proof_payment_please)
        }
        else binding.error.text.text = ""


        if (totalError > 0)
            showSnackBar(binding.container,requireActivity().getString(R.string.message_all_field_must_fill))
        else {
             apiRegistrationStepTwo(domisili, lamaInvestasi, profesi, pendidikan, portofolio)
        }
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
                setProofPaymentPhoto(uri)
            }
        }
    }

    private fun setErrorFromApi(e: ResponseRegisterError) {

    }

    private fun setObserve() {
        viewModel?.reqRegisterResponse()?.observe(viewLifecycleOwner){
            when (it.status) {
                Status.SUCCESS -> {
                    activity.toRegistrationSuccess()
                    dialogLoad?.dismiss()
                }
                Status.LOADING -> {}
                Status.ERROR -> {
                    it.data?.errors?.let { e->
                        dialogLoad?.dismiss()
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

    private fun apiRegistrationStepTwo(
        domisili: String,
        lamaInvestasi: String,
        profesi: String,
        pendidikan: String,
        portofolio: String) {
        dialogLoad = dialogLoading()
        dialogLoad?.show()
        Handler(Looper.getMainLooper()).postDelayed({
            val file = fileProofPayment
            val requestFile: RequestBody? = file?.let { it.asRequestBody("multipart/form-data".toMediaTypeOrNull()) }
            var bb: MultipartBody.Part? = null
            if (file != null)
                bb = MultipartBody.Part.createFormData("bukti_bayar", file.name, requestFile!!)

            val dm = domisili.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val li = lamaInvestasi.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val pf = profesi.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val pd = pendidikan.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val pr = portofolio.toRequestBody("multipart/form-data".toMediaTypeOrNull())


            val s = "2".toRequestBody("multipart/form-data".toMediaTypeOrNull())

            activity.requestRegistrationStepOne?.let { r1 ->
                bb?.let { b ->
                    viewModel?.apiRegistrationStepTwo(
                        s,
                        r1.photoProfile,
                        b,
                        r1.name,
                        r1.noWa,
                        r1.email,
                        r1.birthDate,
                        r1.password,
                        dm,
                        li,
                        pf,
                        pd,
                        pr
                    )
                }
            }
        }, 1000)
    }
}