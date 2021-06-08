package moc.nahba.github.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import moc.nahba.github.R
import moc.nahba.github.views.extensions.gone
import moc.nahba.github.views.extensions.visible
import moc.nahba.github.views.fragments.GitHubFragment
import moc.nahba.github.views.utils.SnackbarUtil

class GitHubActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private var container: Int = R.id.container
    private lateinit var progressBar: ContentLoadingProgressBar
    private val fragmentManager = supportFragmentManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github)
        toolbar = findViewById(R.id.toolbar)
        progressBar = findViewById(R.id.progressBar)
        initializeToolbar()
        replaceFragment(fragment = GitHubFragment(), tag = null, addToBackStack = false, backStackName = null)
    }
    
    private fun initializeToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(true)
            setToolbarImage()
            setToolbarTitleColor()
        }
        enableToolbarUpButton()
        showToolbar()
    }
    
    private fun showToolbar() = toolbar.visible()
    
    internal fun setTitle(title: String?) {
        supportActionBar?.title = title
    }
    
    private fun setToolbarImage() = supportActionBar?.setLogo(R.drawable.ic_arrow_back)
    
    private fun setToolbarTitleColor() = toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
    
    private fun enableToolbarUpButton() = supportActionBar?.setDisplayUseLogoEnabled(false)
    
    internal fun showLoader() = progressBar.visible()
    
    internal fun hideLoader() = progressBar.gone()
    
    @Suppress("SameParameterValue")
    private fun replaceFragment(fragment : Fragment, tag : String?, addToBackStack : Boolean, backStackName : String?) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(container, fragment, tag)
        if(addToBackStack) {
            fragmentTransaction.addToBackStack(backStackName)
        }
        fragmentTransaction.commit()
    }
    
    internal fun showSnackbar(message: String) = SnackbarUtil.showSnackbar(this, message)
    
    private fun getFragment() : Fragment? = fragmentManager.findFragmentById(container)
    
    private fun getBackStackCount() : Int = fragmentManager.backStackEntryCount
    
    override fun onBackPressed() {
        val fragment = getFragment()
        if(fragment != null) {
            if(!(fragment as GitHubFragment).onBackPressed()) {
                backPressedAction()
            }
        } else {
            backPressedAction()
        }
    }
    
    private fun backPressedAction() {
        val backStackCount = getBackStackCount()
        if(backStackCount <= 0) {
            finish()
        } else {
            if(1 == backStackCount) {
                enableToolbarUpButton()
            }
            fragmentManager.popBackStack()
        }
    }
    
    override fun finish() = finishAndRemoveTask()
}