package edu.com.reyphill_duarteap2_p2.Presentation.Repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.com.reyphill_duarteap2_p2.Data.Remote.Resource
import edu.com.reyphill_duarteap2_p2.Data.Repository.RepositoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val repositoryRepository: RepositoryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(RepositoryUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getUsuarios("Reyx38")
    }

    fun OnEvent(event: RepositoryUiEvent) {
        when (event) {
            RepositoryUiEvent.GetSuplidor -> TODO()
            is RepositoryUiEvent.NombreChange -> TODO()
            RepositoryUiEvent.Nuevo -> TODO()
            RepositoryUiEvent.PostSuplidor -> TODO()
            is RepositoryUiEvent.PrecioChange -> TODO()
            is RepositoryUiEvent.SuplidorIdChange -> TODO()
        }
    }

    fun getUsuarios(Usuario: String) {
        viewModelScope.launch {

            repositoryRepository.getUsuarios(Usuario).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val Usuario = resource.data
                        _uiState.update {
                            it.copy(
                                repository = Usuario ?: emptyList()
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                errorMessage = resource.message ?: "Error al cargar los usuarios",
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }

    }


}