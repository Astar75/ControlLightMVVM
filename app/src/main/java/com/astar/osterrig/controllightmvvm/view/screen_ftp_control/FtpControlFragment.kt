package com.astar.osterrig.controllightmvvm.view.screen_ftp_control

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.FragmentFtpControlBinding
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment

internal class FtpControlFragment : BaseFragment() {

    private lateinit var mBinding: FragmentFtpControlBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentFtpControlBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FtpControlFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}