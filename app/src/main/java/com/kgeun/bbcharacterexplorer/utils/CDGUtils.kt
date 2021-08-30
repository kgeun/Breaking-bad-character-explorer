package com.kgeun.bbcharacterexplorer.utils

import android.content.Context
import android.widget.Toast
import java.util.*

object CDGUtils {
    fun random17(): Int = Random().nextInt(17)

    fun errorHandler(context: Context): (String?) -> Unit = {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }
}