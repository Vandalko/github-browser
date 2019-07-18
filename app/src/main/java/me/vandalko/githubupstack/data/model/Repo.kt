package me.vandalko.githubupstack.data.model

import com.google.gson.annotations.SerializedName

class Repo(@SerializedName("name") val name: String,
    @SerializedName("html_url") val url: String)