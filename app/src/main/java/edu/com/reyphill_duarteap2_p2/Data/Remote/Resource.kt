package edu.com.reyphill_duarteap2_p2.Data.Remote

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String?, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)

    fun getErrorMessage(): String? = when (this) {
        is Error -> message
        else -> null
    }

}