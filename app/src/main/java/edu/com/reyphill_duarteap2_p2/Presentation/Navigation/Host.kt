package edu.com.reyphill_duarteap2_p2.Presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.com.reyphill_duarteap2_p2.Presentation.Repository.RepositoryListScreen

@Composable
fun Host(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.RepositoryList
    ) {
        composable<Screen.RepositoryList> {
            RepositoryListScreen(

            )
        }

    }
}