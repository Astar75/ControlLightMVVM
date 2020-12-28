package com.astar.osterrig.controllightmvvm.view.screen_fnc_control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.astar.osterrig.controllightmvvm.databinding.FragmentFncControlBinding
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

internal class FncControlFragment : BaseFragment() {

    override val mModel: FncControlViewModel by viewModel()
    private lateinit var binding: FragmentFncControlBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFncControlBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FncControlFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}