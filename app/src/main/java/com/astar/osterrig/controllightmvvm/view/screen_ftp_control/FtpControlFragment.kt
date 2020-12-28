package com.astar.osterrig.controllightmvvm.view.screen_ftp_control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import com.astar.osterrig.controllightmvvm.databinding.FragmentFtpControlBinding
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

internal class FtpControlFragment : BaseFragment() {

    private lateinit var mBinding: FragmentFtpControlBinding

    override val mModel: ViewModel by viewModel<FtpControlViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentFtpControlBinding.inflate(layoutInflater, container, false)
        initView()
        return mBinding.root
    }

    private fun initView() {

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FtpControlFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}