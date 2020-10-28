package dev.weiqi.zoo.component

import android.view.View

class SingleClickListener(
    private val milliSecond: Long = 350L,
    private val action: (View) -> Unit
) : View.OnClickListener {

    private var lastTimeClicked = 0L

    override fun onClick(view: View) {
        if (System.currentTimeMillis() - lastTimeClicked >= milliSecond) {
            lastTimeClicked = System.currentTimeMillis()
            action(view)
        }
    }
}

fun View.singleClick(action: ((View) -> Unit)) {
    setOnClickListener(SingleClickListener(action = action))
}