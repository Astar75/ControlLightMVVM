package com.astar.osterrig.controllightmvvm.view.widgets

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt

class GradientView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defAttrsStyle: Int = 0
) : View(context, attrs, defAttrsStyle) {

    private var _isSmooth = false
    val isSmooth: Boolean
        get() = _isSmooth

    private var _colors: MutableList<Int> = mutableListOf()
    val colors: List<Int>
        get() = _colors

    private var _borderColor = DEFAULT_BORDER_COLOR
    val borderColor: Int
        get() = _borderColor

    private var widthItemColor = 0f  //  ширина одной ячейки цвета
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        setColors(listOf(
            Color.BLUE, Color.CYAN, Color.MAGENTA, Color.YELLOW
        ))

        setSmooth(false)
    }


    fun setSmooth(modeSmooth: Boolean) {
        _isSmooth = modeSmooth
        calculateWidthItemColor()
        invalidate()
    }

    fun setBorderColor(@ColorInt color: Int) {
        _borderColor = color
        invalidate()
    }

    fun setColors(@ColorInt colors: List<Int>) {
        _colors.clear()
        _colors.addAll(colors)
        calculateWidthItemColor()
        invalidate()
    }

    fun addColor(@ColorInt color: Int) {
        _colors.add(color)
        calculateWidthItemColor()
        invalidate()
    }

    fun removeAllColor() {
        _colors.clear()
        calculateWidthItemColor()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        clearCanvas(canvas)
        if (_colors.isEmpty()) {
            paint.style = Paint.Style.FILL
            paint.color = DEFAULT_BACKGROUND
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        } else if (_colors.size == 1) {
            paint.color = _colors[0]
            paint.style = Paint.Style.FILL
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        } else if (_colors.size > 1) {
            if (_isSmooth) {
                drawGradientColors(canvas)
            } else {
                drawSimpleColors(canvas)
            }
        }
        paint.color = _borderColor
        paint.style = Paint.Style.STROKE
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)   // border
    }

    private fun clearCanvas(canvas: Canvas) {
        paint.style =  Paint.Style.FILL
        paint.color = Color.BLACK
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    }

    private fun drawGradientColors(canvas: Canvas) {
        if (_colors.size > 1) {
            val gradient = LinearGradient(
                0f,
                0f,
                width.toFloat(),
                height.toFloat(),
                _colors.toIntArray(),
                null,
                Shader.TileMode.CLAMP
            )
            val matrix = Matrix()
            matrix.setRotate(-14f)
            gradient.setLocalMatrix(matrix)
            paint.shader = gradient
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        }
    }

    private fun drawSimpleColors(canvas: Canvas) {
        paint.shader = null
        paint.style = Paint.Style.FILL
        var xStart = 0f
        var xEnd = widthItemColor
        for (color in _colors) {
            paint.color = color
            canvas.drawRect(xStart, 0f, xEnd, height.toFloat(), paint)
            xStart += widthItemColor
            xEnd += widthItemColor * 2
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateWidthItemColor()
        invalidate()
    }

    private fun calculateWidthItemColor() {
        widthItemColor = if (_colors.size > 1)
            (width / _colors.size).toFloat()
        else
            width.toFloat()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = resolveDefaultSize(widthMeasureSpec)
        val height = resolveDefaultSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    private fun dpToPx(dp: Int): Float {
        return dp.toFloat() * context.resources.displayMetrics.density
    }

    private fun resolveDefaultSize(spec: Int): Int {
        return when (MeasureSpec.getMode(spec)) {
            MeasureSpec.UNSPECIFIED -> dpToPx(DEFAULT_SIZE).toInt()
            MeasureSpec.AT_MOST -> MeasureSpec.getSize(spec)
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(spec)
            else -> MeasureSpec.getSize(spec)
        }
    }

    companion object {
        const val TAG = "GradientView"

        const val DEFAULT_SIZE = 100
        const val DEFAULT_BACKGROUND = Color.BLACK
        const val DEFAULT_BORDER_COLOR = Color.GRAY
    }
}