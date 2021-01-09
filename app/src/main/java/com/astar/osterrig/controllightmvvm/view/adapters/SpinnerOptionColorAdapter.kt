package com.astar.osterrig.controllightmvvm.view.adapters

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.astar.osterrig.controllightmvvm.R

class SpinnerOptionColorAdapter(val typeface: Typeface?) : BaseAdapter() {

    private val elementsColorPreview: MutableList<Pair<Int, String>> = mutableListOf()

    fun addElementsColor(element: List<Pair<Int, String>>) {
        elementsColorPreview.clear()
        elementsColorPreview.addAll(element)
        notifyDataSetChanged()
    }

    override fun getCount(): Int = elementsColorPreview.size

    override fun getItem(position: Int): Any = elementsColorPreview[position]

    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(parent?.context, R.layout.item_spinner_element, null)
        val tvElement: TextView = view.findViewById(R.id.tvElement)
        val colorPreview: View = view.findViewById(R.id.previewColor)
        colorPreview.visibility = View.VISIBLE
        typeface?.let { tvElement.typeface = it }
        tvElement.text = elementsColorPreview[position].second
        colorPreview.setBackgroundColor(elementsColorPreview[position].first)
        return view
    }
}