package com.astar.osterrig.controllightmvvm.view.screen_cct_control

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.astar.osterrig.controllightmvvm.databinding.FragmentCctControlBinding
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment

internal class CctControlFragment : BaseFragment() {

    private lateinit var mBinding: FragmentCctControlBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCctControlBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CctControlFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}