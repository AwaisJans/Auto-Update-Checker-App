package com.jans.app.dialog

import android.content.DialogInterface

interface DialogCloseListener {
    fun handleDialogClose(dialog: DialogInterface?)
}