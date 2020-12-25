package com.astar.osterrig.controllightmvvm.view.base

import android.widget.Toast
import androidx.fragment.app.Fragment

internal open class BaseFragment: Fragment() {

    protected fun showToastMessage(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}