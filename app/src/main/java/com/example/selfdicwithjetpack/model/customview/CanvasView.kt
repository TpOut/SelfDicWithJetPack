package com.example.selfdicwithjetpack.model.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View

/**
 * Created by TpOut on 2020-04-28.<br>
 * Email address: 416756910@qq.com<br>
 */
class CanvasView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    var mName: String?
    var mPaint: Paint

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomViewApiName)
        mName = typedArray.getString(R.styleable.CustomViewApiName_cvan_name)
        typedArray.recycle()

        mPaint = Paint().apply {
            color = resources.getColor(R.color.colorAccent)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawName(canvas)

//        canvas.drawCircle()
//        canvas.drawPicture()
//        canvas.drawArc()
//        canvas.drawARGB()
//        canvas.drawBitmap()
//        canvas.drawBitmapMesh()
//        canvas.drawColor()
//        canvas.drawDoubleRoundRect()
//        canvas.drawLine()
//        canvas.drawLines()
//        canvas.drawOval()
//        canvas.drawPaint()
//        canvas.drawPath()
//        canvas.drawPoint()
//        canvas.drawPoints()
//        canvas.drawRect()
//        canvas.drawRenderNode()
//        canvas.drawRGB()
//        canvas.drawRoundRect()
//        canvas.drawText()
//        canvas.drawTextOnPath()
//        canvas.drawTextRun()
//        canvas.drawVertices()
//        canvas.drawPosText()
    }

    private fun drawName(canvas: Canvas) {

    }

}