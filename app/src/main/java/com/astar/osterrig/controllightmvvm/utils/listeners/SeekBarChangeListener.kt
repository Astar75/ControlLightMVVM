package com.astar.osterrig.controllightmvvm.utils.listeners

import android.widget.SeekBar

abstract class SeekBarChangeListener: SeekBar.OnSeekBarChangeListener {

    abstract override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean)
    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}