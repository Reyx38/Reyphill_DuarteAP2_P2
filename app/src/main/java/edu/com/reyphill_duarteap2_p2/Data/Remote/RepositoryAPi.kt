package edu.com.reyphill_duarteap2_p2.Data.Remote

import edu.com.reyphill_duarteap2_p2.Data.Remote.Dto.RepositoryDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {
    @GET("users/{username}/repos")
    fun listRepos(@Path("username") username: String): List<RepositoryDto>
}