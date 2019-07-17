package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_main.*


fun Activity.hideKeyboard(){
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(iv_send.windowToken, 0)
}

fun Activity.isKeyBoardOpen():Boolean {
    val rect = Rect()
    main_activity.getWindowVisibleDisplayFrame(rect)
    val screenHeight = main_activity.rootView.height
    val keypadHeight = screenHeight - rect.bottom
    return keypadHeight > screenHeight*0.15
}

fun Activity.isKeyBoardClose():Boolean {
    val rect = Rect()
    main_activity.getWindowVisibleDisplayFrame(rect)
    val screenHeight = main_activity.rootView.height
    val keypadHeight = screenHeight - rect.bottom
    return keypadHeight < screenHeight*0.15
}