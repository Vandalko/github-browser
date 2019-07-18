package me.vandalko.githubupstack.data.api

import me.vandalko.githubupstack.data.model.LoggedInUser
import me.vandalko.githubupstack.data.model.Repo
import okhttp3.OkHttpClient
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHub {

    companion object {
        const val API = "https://api.github.com"

        fun service(authToken: String) : GitHub {
            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    var request = chain.request()
                    val headers =
                        request.headers().newBuilder().add("Authorization", authToken).build()
                    request = request.newBuilder().headers(headers).build()
                    chain.proceed(request)
                }
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(GitHub::class.java)
        }
    }

    @GET("/user")
    suspend fun user(): LoggedInUser

    @GET("user/repos")
    suspend fun repositories(@Query("type") type: String = "owner"): List<Repo>
}
