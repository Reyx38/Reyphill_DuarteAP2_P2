package edu.com.reyphill_duarteap2_p2.Presentation.Navigation

import kotlinx.serialization.Serializable

sealed class Screen{

    @Serializable
    data object RepositoryList : Screen()
}