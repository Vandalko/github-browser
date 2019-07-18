package me.vandalko.githubupstack.ui.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.vandalko.githubupstack.data.api.GitHub
import me.vandalko.githubupstack.data.model.Repo
import java.lang.Exception

class RepoViewModel: ViewModel() {

    val repositories = MutableLiveData<List<Repo>>()

    fun refresh(user: String, token: String) {
        val service = GitHub.service(token)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val repos = try {
                    service.repositories(user)
                } catch (e: Exception) {
                    Log.e("RepoViewModel", "failed to list repos", e)
                    repositories.value
                }
                repositories.postValue(repos)
            }
        }
    }
}
