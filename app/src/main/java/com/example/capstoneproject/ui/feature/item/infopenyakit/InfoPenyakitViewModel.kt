package com.example.capstoneproject.ui.feature.item.infopenyakit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.capstoneproject.data.response.DataItem
import com.example.capstoneproject.data.response.DiseasesResponse
import com.example.capstoneproject.data.tools.UserRepository
import com.example.capstoneproject.data.response.UserModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class InfoPenyakitViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _diseases = MutableLiveData<Result<DiseasesResponse>>()
    val diseases: LiveData<Result<DiseasesResponse>> get() = _diseases

    private val listDiseases = MutableLiveData<ArrayList<DataItem>>()
    fun setDiseases(): LiveData<ArrayList<DataItem>> {
        return listDiseases
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getDiseases() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.getDiseases()
                _diseases.value = Result.success(result)
            } catch (e: Exception) {
                _diseases.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}