package com.astar.osterrig.controllightmvvm.view.adapters

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.astar.osterrig.controllightmvvm.R

open class SpinnerOptionAdapter(val typeface: Typeface?): BaseAdapter() {

    private val elements: MutableList<String> = mutableListOf()

    fun addElements(elements: Array<String>) {
        this.elements.clear()
        this.elements.addAll(elements)
        notifyDataSetChanged()
    }

    override fun getCount() = elements.size

    override fun getItem(position: Int) = elements[position]

    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = View.inflate(parent?.context, R.layout.item_spinner_element, null)
        val textElement: TextView = view.findViewById(R.id.tvElement)
        textElement.text = elements[position]
        typeface?.let { textElement.typeface = it }
        return view
    }
}