package id.co.ptn.hungrystock.ui.general.registration.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseFragment
import id.co.ptn.hungrystock.databinding.FragmentRegistrationStepTwoBinding
import id.co.ptn.hungrystock.models.registration.MainRegistration
import id.co.ptn.hungrystock.models.registration.MainRegistration.Companion.DOMISILI
import id.co.ptn.hungrystock.models.registration.MainRegistration.Companion.LONGINVEST
import id.co.ptn.hungrystock.models.registration.MainRegistration.Companion.PENDIDIKAN
import id.co.ptn.hungrystock.models.registration.MainRegistration.Companion.PROFESI
import id.co.ptn.hungrystock.models.registration.RegistrationItem
import id.co.ptn.hungrystock.ui.general.registration.RegistrationActivity
import id.co.ptn.hungrystock.ui.general.registration.adapters.RegistrationStepTwoAdapter
import java.io.File

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class RegisterStepTwoFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentRegistrationStepTwoBinding
    private lateinit var registrationStepTwoAdapter: RegistrationStepTwoAdapter
    private var items: MutableList<MainRegistration> = mutableListOf()
    private lateinit var activity: RegistrationActivity

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
        init()
    }

    private fun init() {
        activity = (requireActivity() as RegistrationActivity)
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
        items.add(MainRegistration(PENDIDIKAN,"Ukuran Portfolio Investasi Saham (untuk keperluan temu emiten)",portfolio,"", false,
            otherActive = false
        ))
    }

    private fun setProofPaymentPhoto(uri: Uri) {
        binding.image.setImageURI(uri)
        binding.cardProofPayment.visibility = View.VISIBLE
        binding.error.text.text = ""
    }

    private fun addPhotoButtonPressed() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    private fun registerPressed() {
        var totalError = 0
        items.forEachIndexed { index, _ ->
            if (items[index].value.isEmpty())
                totalError += 1
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
        else
            activity.toRegistrationSuccess()
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result?.data?.data?.let { uri ->
                setProofPaymentPhoto(uri)
            }
        }
    }
}