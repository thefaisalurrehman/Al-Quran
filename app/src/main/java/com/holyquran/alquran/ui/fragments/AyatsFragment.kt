package com.holyquran.alquran.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.holyquran.alquran.common.Constants
import com.holyquran.alquran.common.Constants.LAST_READ_AYA_NUMBER
import com.holyquran.alquran.common.MyPreference
import com.holyquran.alquran.databinding.FragmentAyatsBinding
import com.holyquran.alquran.models.adapter.AyatsAdapter
import com.holyquran.alquran.models.interfaces.IAdapterPosition
import com.holyquran.alquran.ui.activity.MainActivity
import com.holyquran.alquran.ui.vm.SurahViewModel


class AyatsFragment : Fragment(), IAdapterPosition {
    private lateinit var binding: FragmentAyatsBinding
    private lateinit var adapter: AyatsAdapter
    private var viewModel: SurahViewModel? = null
    private var isLastRead: Boolean? = false
    private var lastAyaNumber: Int? = 0
    private var currentAyaPosition = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAyatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            isLastRead = bundle.getBoolean(Constants.IS_LAST_READ, false)
            lastAyaNumber = bundle.getInt(LAST_READ_AYA_NUMBER, 0)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        viewModel = (activity as MainActivity).viewModel

        binding.run {
            viewModel?.getAyaList()?.let {
                adapter = AyatsAdapter(it, this@AyatsFragment)
                recyclerview.layoutManager = linearLayoutManager
                recyclerview.adapter = adapter
                if (isLastRead != null && isLastRead == true && lastAyaNumber != null) {
                    linearLayoutManager.scrollToPositionWithOffset(lastAyaNumber!!, 20)
                }
            }

        }
    }

    override fun onDestroyView() {
        MyPreference.with(requireContext()).save(LAST_READ_AYA_NUMBER, currentAyaPosition)
        super.onDestroyView()
    }

    override fun onDestroy() {
        (activity as MainActivity).checkLastRead()
        super.onDestroy()
    }

    override fun currentPosition(position: Int) {
        currentAyaPosition = position
    }

}