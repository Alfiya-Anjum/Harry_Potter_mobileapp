package com.harry_potter_app

import java.io.Serializable



data class Character(
    val name: String,
    val imageResourceId: Int,
    val description: String,
    val url: String
) : Serializable




