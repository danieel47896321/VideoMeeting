package com.example.videomeeting.myClass

import android.app.ProgressDialog
import android.content.Context
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import com.example.videomeeting.R

class Loading {
    private var loadingBar: ProgressDialog? = null
    constructor(context: Context) {
        loadingBar = ProgressDialog(context, R.style.AppCompatAlertDialogStyle)
        val loading = SpannableString(context.resources.getString(R.string.Loading))
        loading.setSpan(RelativeSizeSpan(2f), 0, loading.length, 0)
        loadingBar!!.setCancelable(false)
        loadingBar!!.setMessage(loading)
        loadingBar!!.show()
    }
    fun stop() { loadingBar!!.cancel() }
}