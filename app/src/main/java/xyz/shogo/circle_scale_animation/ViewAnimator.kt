package xyz.shogo.circle_scale_animation

import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.TextView
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR

class ViewAnimator : BaseObservable() {

    @get:Bindable
    var baseAlpha: Float = 1.0f
        set(value) {
            field = value
            notifyPropertyChanged(BR.baseAlpha)
        }

    @get:Bindable
    var completionAlpha: Float = 0.0f
        set(value) {
            field = value
            notifyPropertyChanged(BR.completionAlpha)
        }

    @get:Bindable
    var resultViewAlpha: Float = 0.0f
        set(value) {
            field = value
            notifyPropertyChanged(BR.resultViewAlpha)
        }

    @get:Bindable
    var answeredTextViewAlpha: Float = 0.0f
        set(value) {
            field = value
            notifyPropertyChanged(BR.answeredTextViewAlpha)
        }

    @get:Bindable
    var completionText: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.completionText)
        }

    fun appearCompletionLayout() {
        completionAlpha = 1.0f
        ValueAnimator.ofFloat(1f, 0f).apply {
            duration = 500
            addUpdateListener {
                baseAlpha = it.animatedValue as Float
                resultViewAlpha = 1 - it.animatedValue as Float
            }
            start()
        }
    }

    fun moveCompletionParts(
        pushedButton: FrameLayout?,
        circleView: CircleView?,
        resultView: FrameLayout?
    ) {
        pushedButton ?: return
        circleView ?: return
        resultView ?: return

        val location = IntArray(2)
        pushedButton.getLocationInWindow(location)
        val x = location[0]
        val y = location[1]
        resultView.translationX = x.toFloat()
        resultView.translationY = y.toFloat()
        val lp: ViewGroup.LayoutParams = circleView.getLayoutParams()
        val mlp = lp as MarginLayoutParams
        mlp.setMargins(x, y, mlp.rightMargin, mlp.bottomMargin)
        circleView.layoutParams = mlp
    }

    fun animateCompletion(
        circleView: CircleView?,
        resultView: FrameLayout?,
        answerdTextView: TextView?,
        displayWidth: Int
    ) {
        var scaleAnimation = ScaleAnimation(
            1.0f, 50.0f, 1.0f, 50.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleAnimation.duration = 500
        scaleAnimation.repeatCount = 0
        scaleAnimation.fillAfter = true
        scaleAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                centeringResultView(resultView, displayWidth)
                centeringAnswerdTextView(answerdTextView)
            }
        })
        circleView?.startAnimation(scaleAnimation)
    }

    private fun centeringResultView(resultView: FrameLayout?, displayWidth: Int) {
        resultView ?: return
        val location = IntArray(2)
        resultView.getLocationInWindow(location)
        val moveX = (displayWidth / 2) - location[0] - (resultView.width / 2)
        val translateAnimation = TranslateAnimation(0f, moveX.toFloat(), 0f, 0f)
        translateAnimation.setDuration(800)
        translateAnimation.setRepeatCount(0)
        translateAnimation.setFillAfter(true)
        resultView.startAnimation(translateAnimation)
    }

    private fun centeringAnswerdTextView(textView: TextView?) {
        textView ?: return
        ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 800
            addUpdateListener {
                answeredTextViewAlpha = it.animatedValue as Float
            }
            start()
        }
        val translateAnimation = TranslateAnimation(0f, 0f, 0f, -50f)
        translateAnimation.setDuration(800)
        translateAnimation.setRepeatCount(0)
        translateAnimation.setFillAfter(true)
        textView.startAnimation(translateAnimation)
    }

    companion object {
        @JvmStatic
        fun setSize(view: View, size: Int) {
            val layoutParams: ViewGroup.LayoutParams = view.getLayoutParams()
            layoutParams.height = size
            layoutParams.width = size
            view.setLayoutParams(layoutParams)
        }
    }
}
