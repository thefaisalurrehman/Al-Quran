package com.holyquran.alquran.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.holyquran.alquran.databinding.FragmentAyatsBinding
import com.holyquran.alquran.models.adapter.AyatsAdapter
import com.holyquran.alquran.ui.activity.MainActivity
import com.holyquran.alquran.ui.vm.SurahViewModel

class AyatsFragment : Fragment() {
    private lateinit var binding: FragmentAyatsBinding
    private lateinit var adapter: AyatsAdapter
    private var viewModel: SurahViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAyatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        binding.run {
            viewModel?.getAyaList()?.let {
                adapter = AyatsAdapter(it)
                recyclerview.layoutManager = LinearLayoutManager(requireContext())
                recyclerview.adapter = adapter
            }

        }
    }


}