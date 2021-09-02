package com.kgeun.bbcharacterexplorer.utils

import android.content.Context
import android.widget.Toast

object BBUtils {
    fun errorHandler(context: Context): (String?) -> Unit = {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }


}