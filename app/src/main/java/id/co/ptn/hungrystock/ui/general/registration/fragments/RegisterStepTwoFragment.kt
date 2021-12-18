package id.co.ptn.hungrystock.ui.general.registration.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.bases.BaseFragment
import id.co.ptn.hungrystock.databinding.FragmentPageOneBinding
import id.co.ptn.hungrystock.databinding.FragmentRegistrationStepTwoBinding
import id.co.ptn.hungrystock.models.registration.MainRegistration
import id.co.ptn.hungrystock.models.registration.MainRegistration.Companion.DOMISILI
import id.co.ptn.hungrystock.models.registration.MainRegistration.Companion.LONGINVEST
import id.co.ptn.hungrystock.models.registration.MainRegistration.Companion.PENDIDIKAN
import id.co.ptn.hungrystock.models.registration.MainRegistration.Companion.PROFESI
import id.co.ptn.hungrystock.models.registration.RegistrationItem
import id.co.ptn.hungrystock.router.Router
import id.co.ptn.hungrystock.ui.general.registration.RegistrationActivity
import id.co.ptn.hungrystock.ui.general.registration.adapters.RegistrationStepTwoAdapter

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
        binding.btRegister.setOnClickListener { activity.toRegistrationSuccess() }
        binding.btBack.setOnClickListener { activity.changePage(0) }
    }

    private fun initList() {
        registrationStepTwoAdapter = RegistrationStepTwoAdapter(items)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = registrationStepTwoAdapter
        }
    }

    private fun initListData() {
        val domisili: MutableList<RegistrationItem> = mutableListOf()
        domisili.add(RegistrationItem("JABODETABEK", false))
        domisili.add(RegistrationItem("Yang lain", false))
        items.add(MainRegistration(DOMISILI,"Domisili", domisili,""))

        val longInvest: MutableList<RegistrationItem> = mutableListOf()
        longInvest.add(RegistrationItem("<1 Tahun", false))
        longInvest.add(RegistrationItem("2 - 5 Tahun", false))
        longInvest.add(RegistrationItem("Diatas 5 Tahun", false))
        items.add(MainRegistration(LONGINVEST,"Sudah berapa lama investasi saham?",longInvest,""))

        val profesi: MutableList<RegistrationItem> = mutableListOf()
        profesi.add(RegistrationItem("Pengusaha", false))
        profesi.add(RegistrationItem("Profesional", false))
        profesi.add(RegistrationItem("Pensiunan", false))
        profesi.add(RegistrationItem("Mahasiswa / Pelajar", false))
        profesi.add(RegistrationItem("Yang lain", false))
        items.add(MainRegistration(PROFESI,"Profesi anda saat ini?",profesi,""))

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
        items.add(MainRegistration(PENDIDIKAN,"Latar belakang pendidikan",pendidikan,""))

        val portfolio: MutableList<RegistrationItem> = mutableListOf()
        portfolio.add(RegistrationItem("< Rp 250 juta", false))
        portfolio.add(RegistrationItem("Rp 250 juta - 500 juta", false))
        portfolio.add(RegistrationItem("Rp 500 juta - 1 miliar", false))
        portfolio.add(RegistrationItem("Rp 1 - 2.5 miliar", false))
        portfolio.add(RegistrationItem("Diatas Rp 2.5 miliar", false))
        items.add(MainRegistration(PENDIDIKAN,"Ukuran Portfolio Investasi Saham (untuk keperluan temu emiten)",portfolio,""))
    }

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
}