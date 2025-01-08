package com.ev.dpadnavsystem.utils

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()


fun ViewPager2.showHorizontalPreview(offsetDpLeft: Int, offsetDpRight: Int, marginBtwItems: Int) {
    this.apply {
        clipToPadding = false   // allow full width shown with padding
        clipChildren = false    // allow left/right item is not clipped
        offscreenPageLimit = 2  // make sure left/right item is rendered
    }

    // increase this offset to show more of left/right
    val offsetPxLeft = offsetDpLeft.px
    val offsetPxRight = offsetDpRight.px
//    this.setPadding(offsetPxLeft, 0, offsetPxRight, 0)
    this.setPadding(0, offsetPxLeft, 0, offsetPxRight)

    // increase this offset to increase distance between 2 items
    val pageMarginPx = marginBtwItems.px
    val marginTransformer = MarginPageTransformer(pageMarginPx)
    this.setPageTransformer(marginTransformer)
}

fun <T> Fragment.collectFlowOnLifeCycle(flow: Flow<T>, collect: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect { value ->
                collect(value)
            }
        }
    }
}


fun <T> AppCompatActivity.collectFlowOnLifeCycle(flow: Flow<T>, collect: (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect { value ->
                collect(value)
            }
        }
    }
}