package com.example.asus.json.utils

import android.content.DialogInterface
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.view.Gravity
import android.view.View
import android.view.ViewManager
import android.widget.LinearLayout
import android.widget.TextView
import com.example.asus.json.R
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

private const val ACTIVITY_PADDING = 16
class FeedbackDialog(ui: AnkoContext<View>) {

    lateinit var dialog: DialogInterface

    init {
        with(ui) {
            dialog = indeterminateProgressDialog("loading")
        }
    }
}

