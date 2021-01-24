package com.astar.osterrig.controllightmvvm.view.screen_tc_fnc_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.astar.osterrig.controllightmvvm.databinding.FragmentTcFncBinding
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

internal class TcFncControlFragment : BaseFragment<TcFncControlViewModel>() {

    private lateinit var binding: FragmentTcFncBinding

    override val mModel: TcFncControlViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTcFncBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {

    }

    override fun addObserversControl() {

    }

    override fun removeObserversControl() {

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            TcFncControlFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}