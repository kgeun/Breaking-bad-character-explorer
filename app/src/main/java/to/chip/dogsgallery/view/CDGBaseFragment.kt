package to.chip.dogsgallery.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import to.chip.dogsgallery.analytics.CDGAnalytics

open class CDGBaseFragment : Fragment() {
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