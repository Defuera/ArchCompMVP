package ru.justd.abnamro.utils

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager


fun AppCompatActivity.hideKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}