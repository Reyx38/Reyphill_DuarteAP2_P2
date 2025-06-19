package edu.com.reyphill_duarteap2_p2.Presentation.Repository

import edu.com.reyphill_duarteap2_p2.Data.Remote.Dto.RepositoryDto

data class RepositoryUiState(
    val nombre: String = "",
    val errorNombre: String? = null,
    val descripcion: String? = null,
    val errorDescripcion: String? = null,
    val htmlUrl: String? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val repository: List<RepositoryDto> = emptyList(),
)