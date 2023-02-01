package com.example.shimmer_sample

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var startOrEnd = R.color.black
        var mid = R.color.white
        val drawable = GradientDrawable().apply {
            colors = intArrayOf(startOrEnd,mid,startOrEnd)
            orientation = GradientDrawable.Orientation.LEFT_RIGHT
            gradientType = GradientDrawable.LINEAR_GRADIENT
        }
        val view = findViewById<View>(R.id.v_one)
        view.background = drawable
        val evaluator = ArgbEvaluator()
        val va = ValueAnimator.ofFloat(0.0f, 1.0f).apply {
            addUpdateListener { p0 ->
                val value = (p0.animatedValue as Float).roundToInt()
                var newStart = evaluator.evaluate(value/100f,startOrEnd,startOrEnd) as Int
                var newMid = evaluator.evaluate(value/100f,mid,startOrEnd) as Int
                var newEnd = evaluator.evaluate(value/100f,startOrEnd,mid) as Int
                drawable.colors = intArrayOf(newStart,newMid,newEnd)
                view.background = drawable
            }
            duration = 1000
            repeatCount = 20
            repeatMode = ValueAnimator.REVERSE
            start()
        }
        va.values.forEach { Log.d("taget",it.toString()) }
    }

}