package edu.com.reyphill_duarteap2_p2.Data.Remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val suplidorApi: GitHubApi
) {

     suspend fun getSuplidores(Username: String) = suplidorApi.listRepos(Username)

}