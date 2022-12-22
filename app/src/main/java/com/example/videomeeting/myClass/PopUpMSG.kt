package com.example.videomeeting.myClass

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.example.videomeeting.R

class PopUpMSG {
    private var builder: AlertDialog.Builder
    constructor(context: Context, Title: String?, Message: String?) {
        builder = AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle)
        builder.setTitle(Title)
        builder.setMessage(Message)
        builder.setPositiveButton(context.resources.getString(R.string.OK)){ _, _ -> }
        builder.setCancelable(false)
        builder.create().show()
    }
    constructor(context: Context, Title: String?, Message: String?, Destination: Class<*>?) {
        builder = AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle)
        builder.setTitle(Title)
        builder.setMessage(Message)
        builder.setPositiveButton(context.resources.getString(R.string.OK)) { _, _ ->
            context.startActivity(Intent(context, Destination))
            (context as Activity).finish()
        }
        builder.setCancelable(false)
        builder.create().show()
    }
}