package me.vandalko.githubupstack.ui.login

import me.vandalko.githubupstack.data.model.LoggedInUser

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: Pair<LoggedInUser, String>? = null,
    val error: Int? = null
)
