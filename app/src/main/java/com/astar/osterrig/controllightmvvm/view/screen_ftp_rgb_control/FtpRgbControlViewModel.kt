package com.astar.osterrig.controllightmvvm.view.screen_ftp_rgb_control

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astar.osterrig.controllightmvvm.utils.colorIntToHexString

class FtpRgbControlViewModel : ViewModel() {

    val color: MutableLiveData<Int> = MutableLiveData(Color.BLACK)
    val textColor: MutableLiveData<String> = MutableLiveData()

    val red: MutableLiveData<Int> = MutableLiveData(0)
    val green: MutableLiveData<Int> = MutableLiveData(0)
    val blue: MutableLiveData<Int> = MutableLiveData(0)

    fun setRed(value: Int) {
        red.value = value
        val color = this.color.value!!
        val green = Color.green(color)
        val blue = Color.blue(color)
        this.color.value = Color.rgb(value, green, blue)
        updateTextColor()
    }

    fun setGreen(value: Int) {
        green.value = value
        val color = this.color.value!!
        val red = Color.red(color)
        val blue = Color.blue(color)
        this.color.value = Color.rgb(red, value, blue)
        updateTextColor()
    }

    fun setBlue(value: Int) {
        blue.value = value
        val color = this.color.value!!
        val red = Color.red(color)
        val green = Color.green(color)
        this.color.value = Color.rgb(red, green, value)
        updateTextColor()
    }

    private fun updateTextColor() {
        textColor.value = colorIntToHexString(color.value!!)
    }
}