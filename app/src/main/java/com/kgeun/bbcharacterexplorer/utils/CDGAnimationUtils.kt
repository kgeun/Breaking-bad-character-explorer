package com.kgeun.bbcharacterexplorer.utils

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.kgeun.bbcharacterexplorer.constants.CDGConstants

object CDGAnimationUtils {

    val FIRST_SPLASH_DURATION = 600L
    val SECOND_SPLASH_DURATION = 300L
    val THIRD_SPLASH_DURATION = 400L
//
//    fun getFirstSplashListener(context: Context, binding: ActivitySplashBinding, afterFinish: () -> Unit) = object: Animator.AnimatorListener {
//        override fun onAnimationStart(anim: Animator?) {}
//        override fun onAnimationCancel(anim: Animator?) {}
//        override fun onAnimationRepeat(anim: Animator?) {}
//        override fun onAnimationEnd(anim: Animator?) {
//            val anim2 = getAnimCustomSpeed(SECOND_SPLASH_DURATION, R.anim.slide_to_left_splash, context)
//            anim2.setAnimationListener(getSecondSplashListener(binding, afterFinish))
//            anim2.fillAfter = true
//            binding.introAppLogoImg.startAnimation(anim2)
//        }
//    }
//
//    fun getSecondSplashListener(binding: ActivitySplashBinding, afterFinish: () -> Unit) = object: Animation.AnimationListener {
//        override fun onAnimationStart(anim: Animation?) {}
//        override fun onAnimationRepeat(anim: Animation?) {}
//        override fun onAnimationEnd(anim: Animation?) {
//            YoYo.with(Techniques.SlideInLeft)
//                .duration(THIRD_SPLASH_DURATION)
//                .withListener(getThirdSplashListener(binding, afterFinish))
//                .playOn(binding.introAppTypoImg)
//        }
//    }
//
//    fun getThirdSplashListener(binding: ActivitySplashBinding, afterFinish: () -> Unit) = object: Animator.AnimatorListener {
//        override fun onAnimationStart(anim: Animator?) {
//            binding.introAppTypoImg.visibility = View.VISIBLE
//        }
//        override fun onAnimationCancel(anim: Animator?) {}
//        override fun onAnimationRepeat(anim: Animator?) {}
//        override fun onAnimationEnd(anim: Animator?) {
//            afterFinish()
//        }
//    }

    fun getAnim400(animation: Int, context: Context): Animation {
        val ani400: Animation = AnimationUtils.loadAnimation(
            context,
            animation
        )
        ani400.duration = 400
        ani400.fillAfter = true

        return ani400
    }

    fun getAnim500(animation: Int, context: Context): Animation {
        val ani500: Animation = AnimationUtils.loadAnimation(
            context,
            animation
        )
        ani500.duration = 500
        ani500.fillAfter = true

        return ani500
    }

    fun getAnimCustomSpeed(duration: Long, animation: Int, context: Context): Animation {
        val aniC: Animation = AnimationUtils.loadAnimation(
            context,
            animation
        )
        aniC.duration = duration
        aniC.fillAfter = true

        return aniC
    }

    fun getAnimStandard(animation: Int, context: Context): Animation {
        val aniS: Animation = AnimationUtils.loadAnimation(
            context,
            animation
        )
        aniS.duration = CDGConstants.ANIM_DURATION
        aniS.isFillEnabled = true
        aniS.fillAfter = true

        return aniS
    }
}