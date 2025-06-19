package edu.com.reyphill_duarteap2_p2.Presentation.Repository

sealed interface RepositoryUiEvent {
    data class SuplidorIdChange(val suplidorId: Int): RepositoryUiEvent
    data class NombreChange(val nombre: String): RepositoryUiEvent
    data class PrecioChange(val precio: Double) : RepositoryUiEvent

    data object PostSuplidor: RepositoryUiEvent
    data object GetSuplidor: RepositoryUiEvent
    data object Nuevo: RepositoryUiEvent
}