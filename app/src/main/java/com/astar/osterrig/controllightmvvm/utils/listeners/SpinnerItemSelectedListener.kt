package com.astar.osterrig.controllightmvvm.utils.listeners

import android.view.View
import android.widget.AdapterView

abstract class SpinnerItemSelectedListener: AdapterView.OnItemSelectedListener {

    abstract override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
    override fun onNothingSelected(parent: AdapterView<*>?) {}
}