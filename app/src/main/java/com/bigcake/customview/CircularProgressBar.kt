package com.bigcake.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import kotlin.math.min

class CircularProgressBar : View {
    companion object {
        private const val DEFAULT_PROGRESS = 50
        private const val DEFAULT_PROGRESS_COLOR = R.color.teal_700
    }

    private lateinit var paint: Paint
    private lateinit var rect: RectF
    var strokeWidth: Float = 50f
    var progress = DEFAULT_PROGRESS
        set(value) {
            value.takeIf { it in 0..100 }?.let {
                field = it
            } ?: throw RuntimeException("Invalid progress value: $value")
        }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.CircularProgressBar, defStyle, 0
        )

        progress = a.getInt(R.styleable.CircularProgressBar_progress, DEFAULT_PROGRESS)
        val strokeColor =
            a.getColor(
                R.styleable.CircularProgressBar_strokeColor,
                ContextCompat.getColor(context, DEFAULT_PROGRESS_COLOR)
            )

//        _exampleString = a.getString(
//            R.styleable.CircularProgressBar_exampleString
//        )
//        _exampleColor = a.getColor(
//            R.styleable.CircularProgressBar_exampleColor,
//            exampleColor
//        )
//        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
//        // values that should fall on pixel boundaries.
//        _exampleDimension = a.getDimension(
//            R.styleable.CircularProgressBar_exampleDimension,
//            exampleDimension
//        )
//
//        if (a.hasValue(R.styleable.CircularProgressBar_exampleDrawable)) {
//            exampleDrawable = a.getDrawable(
//                R.styleable.CircularProgressBar_exampleDrawable
//            )
//            exampleDrawable?.callback = this
//        }
//
        a.recycle()

        paint = Paint()
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = strokeColor

        rect = RectF()

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements()
    }

    private fun invalidateTextPaintAndMeasurements() {
//        textPaint.let {
//            it.textSize = exampleDimension
//            it.color = exampleColor
//            textWidth = it.measureText(exampleString)
//            textHeight = it.fontMetrics.bottom
//        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom
        val contentSize = min(contentWidth, contentHeight).toFloat()
        val radius = contentSize / 2 - strokeWidth / 2
        rect.set(
            paddingStart + strokeWidth / 2,
            paddingTop + strokeWidth / 2,
            contentSize - paddingRight - strokeWidth / 2,
            contentSize - paddingBottom - strokeWidth / 2
        )
        canvas.drawArc(rect, -90f, 360f * progress / 100f, false, paint)
    }
}