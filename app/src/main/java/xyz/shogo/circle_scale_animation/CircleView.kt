package xyz.shogo.circle_scale_animation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

class CircleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private val rect = RectF()
    private var angle = 360F
    private var x: Int = 0
    private var y: Int = 0
    private var isInit = true

    companion object {
        private const val angleTarget = 0F
    }

    init {
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = ContextCompat.getColor(context, R.color.colorAccent)
    }

    public override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isInit) {
            x = canvas.width / 2
            y = canvas.height / 2
            isInit = false
        }
        canvas.drawColor(ContextCompat.getColor(context, R.color.transparent))
        var radius = 40
        val left = x - radius
        val top = y - radius
        val right = x + radius
        val bottom = y + radius
        rect.set(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
        canvas.drawArc(rect, angleTarget, angle, false, paint)
    }

    fun changeColor(@ColorRes colorId: Int) {
        paint.color = ContextCompat.getColor(context, colorId)
    }
}
