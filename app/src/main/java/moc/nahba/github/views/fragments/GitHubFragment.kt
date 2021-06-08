package moc.nahba.github.views.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import moc.nahba.github.R
import moc.nahba.github.databinding.FragmentGithubBinding
import moc.nahba.github.models.dto.GitHubRepo
import moc.nahba.github.models.wrappers.GitHubWrapper
import moc.nahba.github.views.activities.GitHubActivity
import moc.nahba.github.views.adapters.GitHubAdapter
import moc.nahba.github.views.extensions.gone
import moc.nahba.github.views.extensions.visible
import moc.nahba.github.views.utils.KeyboardUtil
import moc.nahba.github.views.viewmodels.GitHubViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GitHubFragment : Fragment() {
    private val viewModel: GitHubViewModel by viewModel()
    private lateinit var binding: FragmentGithubBinding
    private lateinit var adapter: GitHubAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = GitHubAdapter()
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGithubBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(getString(R.string.app_name))
        setUpRecyclerView()
        binding.titleView.setOnClickListener {
            println("Nahba: I\'m clicked...")
        }
        binding.etUser.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(textView: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (textView?.id == R.id.etUser) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        fetchRepositories()
                        return true
                    }
                }
                return false
            }
        })
        binding.btnFetch.setOnClickListener {
            fetchRepositories()
        }
        viewModel.repositories.observe(viewLifecycleOwner, observer)
    }
    
    private fun setTitle(title: String) = getBaseActivity()?.setTitle(title)
    
    private fun setUpRecyclerView() {
        context?.let {
            binding.recyclerView.setHasFixedSize(true)
            binding.recyclerView.layoutManager = LinearLayoutManager(it)
            binding.recyclerView.adapter = adapter
            val itemDecorator = DividerItemDecoration(it, DividerItemDecoration.VERTICAL)
            val drawable = ContextCompat.getDrawable(it, R.drawable.divider_verticle)
            drawable?.let { spacer ->
                itemDecorator.setDrawable(spacer)
            }
            binding.recyclerView.addItemDecoration(itemDecorator)
            binding.recyclerView.itemAnimator = null
        }
    }
    
    private fun fetchRepositories() {
        KeyboardUtil.hideKeyboard(binding.etUser)
        binding.etUser.text?.let { userName ->
            if (userName.toString().trim().isNotBlank()) {
                showLoader()
                viewModel.repositories(userName.toString())
            }
        }
    }
    
    @Suppress("UNCHECKED_CAST")
    private val observer = Observer<GitHubWrapper> { result ->
        when (result) {
            is GitHubWrapper.NetworkError -> {
                hideLoader()
                showSnackbar(getString(R.string.error_no_internet_connectivity))
            }
            is GitHubWrapper.Success<*> -> {
                val items = result.value as? List<GitHubRepo>
                if (items.isNullOrEmpty()) {
                    binding.recyclerView.gone()
                    binding.textView.visible()
                } else {
                    binding.recyclerView.visible()
                    binding.textView.gone()
                    adapter.setRepo(items)
                }
                hideLoader()
            }
            is GitHubWrapper.GenericError -> {
                hideLoader()
                if (result.error.isNullOrBlank()) {
                    showSnackbar(getString(R.string.error_oops_something_went_wrong))
                } else {
                    showSnackbar(result.error)
                }
            }
        }
    }
    
    private fun showLoader() = getBaseActivity()?.showLoader()
    private fun hideLoader() = getBaseActivity()?.hideLoader()
    private fun showSnackbar(message: String) = getBaseActivity()?.showSnackbar(message)
    private fun getBaseActivity() = activity as? GitHubActivity
    internal fun onBackPressed(): Boolean = false
}