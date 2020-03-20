package xyz.shogo.circle_scale_animation

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import xyz.shogo.circle_scale_animation.databinding.ActivityMainBinding

class MainActivity : Activity() {

    private var binding: ActivityMainBinding? = null
    private val animator = ViewAnimator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.owner = this
        binding?.animator = animator
    }

    fun onClickButtonA() {
        disableEnableControls(false, binding?.baseLayout)
        animateCompletion(binding?.buttonA, R.string.patternA, R.color.patternA)
        resetView()
    }

    fun onClickButtonB() {
        disableEnableControls(false, binding?.baseLayout)
        animateCompletion(binding?.buttonB, R.string.patternB, R.color.patternB)
        resetView()
    }

    fun onClickButtonC() {
        disableEnableControls(false, binding?.baseLayout)
        animateCompletion(binding?.buttonC, R.string.patternC, R.color.patternC)
        resetView()
    }

    fun onClickButtonD() {
        disableEnableControls(false, binding?.baseLayout)
        animateCompletion(binding?.buttonD, R.string.patternD, R.color.patternD)
        resetView()
    }

    private fun disableEnableControls(enable: Boolean, viewGroup: ViewGroup?) {
        viewGroup ?: return
        for (i in 0 until viewGroup.childCount) {
            val child = viewGroup.getChildAt(i)
            child.isEnabled = enable
            if (child is ViewGroup) {
                disableEnableControls(enable, child)
            }
        }
    }

    private fun animateCompletion(
        button: FrameLayout?,
        @StringRes textId: Int,
        @ColorRes colorId: Int
    ) {
        binding?.circleView?.changeColor(colorId)
        animator?.completionText = getString(textId)
        animator?.moveCompletionParts(
            button,
            binding?.circleView,
            binding?.resultView
        )
        animator?.appearCompletionLayout()
        binding?.completionLayout?.width?.let {
            animator?.animateCompletion(
                binding?.circleView,
                binding?.resultView,
                binding?.answeredTextView,
                it
            )
        }
    }

    private fun resetView() {
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            animator.disappearCompletionLayout()
            animator?.resetResultView(binding?.resultView)
            animator.resetAnswerdTextView(binding?.answeredTextView)
            disableEnableControls(true, binding?.baseLayout)
        }, 3000)
    }
}
