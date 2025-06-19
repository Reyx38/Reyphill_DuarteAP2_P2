package edu.com.reyphill_duarteap2_p2.Data.Repository

import edu.com.reyphill_duarteap2_p2.Data.Remote.Dto.RepositoryDto
import edu.com.reyphill_duarteap2_p2.Data.Remote.RemoteDataSource
import edu.com.reyphill_duarteap2_p2.Data.Remote.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {

     fun getUsuarios(username: String): Flow<Resource<List<RepositoryDto>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val repository = remoteDataSource.getSuplidores(username)
                if (repository.isNotEmpty()) {
                    emit(Resource.Success(repository))
                } else {
                    emit(Resource.Error("No se encontraron repositorios"))
                }
            } catch (e: Exception) {

                emit(Resource.Error("Error: ${e.localizedMessage ?: "Error desconocido"}"))
            }
        }
    }

}