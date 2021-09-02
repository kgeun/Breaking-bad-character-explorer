package com.kgeun.bbcharacterexplorer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kgeun.bbcharacterexplorer.analytics.CDGAnalytics

open class BBBaseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sendAnalytics()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun sendAnalytics() {
        CDGAnalytics.sendView(javaClass.simpleName)
    }
}