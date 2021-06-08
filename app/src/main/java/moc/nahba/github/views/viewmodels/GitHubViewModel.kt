package moc.nahba.github.views.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import moc.nahba.github.models.usecases.GitHubUseCase
import moc.nahba.github.models.wrappers.GitHubWrapper

class GitHubViewModel(
    private val useCase: GitHubUseCase
): ViewModel() {
    private var _repositories = MutableLiveData<GitHubWrapper>()
    val repositories: LiveData<GitHubWrapper>
        get() = _repositories
    
    fun repositories(userName: String) {
        viewModelScope.launch {
            useCase.repositories(userName).collect { result ->
                _repositories.value = result
            }
        }
    }
}