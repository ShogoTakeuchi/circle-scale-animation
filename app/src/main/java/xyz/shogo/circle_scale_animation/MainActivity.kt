package xyz.shogo.circle_scale_animation

import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
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
        binding?.viewAnimator = animator
    }

    fun onClickButtonA() {
        disableEnableControls(false, binding?.baseLayout)
        animateCompletion(R.string.patternA, binding?.buttonA, R.color.patternA)
    }

    fun onClickButtonB() {
        disableEnableControls(false, binding?.baseLayout)
        animateCompletion(R.string.patternB, binding?.buttonB, R.color.patternB)
    }

    fun onClickButtonC() {
        disableEnableControls(false, binding?.baseLayout)
        animateCompletion(R.string.patternC, binding?.buttonC, R.color.patternC)
    }

    fun onClickButtonD() {
        disableEnableControls(false, binding?.baseLayout)
        animateCompletion(R.string.patternD, binding?.buttonD, R.color.patternD)
    }

    private fun disableEnableControls(enable: Boolean, vg: ViewGroup?) {
        vg ?: return
        for (i in 0 until vg.childCount) {
            val child = vg.getChildAt(i)
            child.isEnabled = enable
            if (child is ViewGroup) {
                disableEnableControls(enable, child)
            }
        }
    }

    private fun animateCompletion(
        @StringRes textId: Int,
        button: FrameLayout?,
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
}
