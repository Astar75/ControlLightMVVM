package com.astar.osterrig.controllightmvvm.view.screen_rgb_control

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.astar.osterrig.controllightmvvm.databinding.FragmentRgbControlBinding
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment

internal class RgbControlFragment : BaseFragment() {

    private lateinit var mBinding: FragmentRgbControlBinding
    private var mCurrentColor = 0
    private var mLightness = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mCurrentColor = it.getInt(COLOR_ARG, Color.WHITE)
            mLightness = it.getInt(LIGHTNESS_ARG, 255)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentRgbControlBinding.inflate(layoutInflater)

        return mBinding.root
    }

    companion object {

        private const val COLOR_ARG = "color"
        private const val LIGHTNESS_ARG = "lightness"

        @JvmStatic
        fun newInstance(color: Int, lightness: Int) =
            RgbControlFragment().apply {
                arguments = Bundle().apply {
                    putInt(COLOR_ARG, color)
                    putInt(LIGHTNESS_ARG, lightness)
                }
            }
    }
}