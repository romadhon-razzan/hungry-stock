package id.co.ptn.hungrystock.ui.main.hsro

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.databinding.HsroFragmentBinding
import id.co.ptn.hungrystock.models.main.hsro.Hsro
import id.co.ptn.hungrystock.ui.main.hsro.adapters.HsroListAdapter

@AndroidEntryPoint
class HsroFragment : Fragment() {

    companion object {
        fun newInstance() = HsroFragment()
    }

    private lateinit var binding: HsroFragmentBinding
    private val viewModel: HsroViewModel by viewModels()
    private lateinit var hsroListAdapter: HsroListAdapter
    private var items: MutableList<Hsro> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.hsro_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initListData()
        initList()
    }

    private fun initListData() {
        items.add(Hsro("JECC", "PT. Jaya Real Property Tbk"))
        items.add(Hsro("JECC", "PT. Jaya Real Property Tbk"))
        items.add(Hsro("JECC", "PT. Jaya Real Property Tbk"))
        items.add(Hsro("JECC", "PT. Jaya Real Property Tbk"))
        items.add(Hsro("JECC", "PT. Jaya Real Property Tbk"))
    }

    private fun initList() {
        hsroListAdapter = HsroListAdapter(items)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = hsroListAdapter
        }
    }

}