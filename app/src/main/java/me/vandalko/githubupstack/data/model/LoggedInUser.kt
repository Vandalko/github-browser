package me.vandalko.githubupstack.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    @SerializedName("login")
    val login: String,
    @SerializedName("url")
    val url: String
)
