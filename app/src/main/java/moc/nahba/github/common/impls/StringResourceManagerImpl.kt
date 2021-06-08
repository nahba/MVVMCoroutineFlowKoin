package moc.nahba.github.common.impls

import android.content.res.Resources
import moc.nahba.github.R
import moc.nahba.github.common.StringResourceManager

class StringResourceManagerImpl(private val resources: Resources) : StringResourceManager {
    override fun appName(): String {
        return resources.getString(R.string.app_name)
    }
    
    override fun noInternetConnectivity(): String {
        return resources.getString(R.string.error_no_internet_connectivity)
    }
    
    override fun oopsSomethingWentWrong(): String {
        return resources.getString(R.string.error_oops_something_went_wrong)
    }
}