package com.mis.route.newsapp.presentations.ui.home.fragments.news

import android.content.DialogInterface

data class DialogMessage(
    val title: String?,
    val message: String?,
    val posText: String?,
    val posAction: DialogInterface.OnClickListener?,
    val negText: String?,
    val negAction: DialogInterface.OnClickListener?,
    val cancelable: Boolean = true
)
