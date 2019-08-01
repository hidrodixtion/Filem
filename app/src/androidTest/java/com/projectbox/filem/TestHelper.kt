package com.projectbox.filem

import android.view.View
import android.widget.TextView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom

/**
 * Created by adinugroho
 */
object TestHelper {
    fun getText(viewInteraction: ViewInteraction): String? {
        val stringHolder = arrayOf<String?>(null)
        viewInteraction.perform(object : ViewAction {
            override fun getConstraints() = isAssignableFrom(TextView::class.java)

            override fun getDescription() = "Get text from View: ${stringHolder[0]}"

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView
                stringHolder[0] = tv.text.toString()
            }
        })
        return stringHolder[0]
    }

}