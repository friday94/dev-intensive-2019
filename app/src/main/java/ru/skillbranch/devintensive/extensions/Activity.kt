package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_main.*


fun Activity.hideKeyboard(){
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(iv_send.windowToken, 0)
}


