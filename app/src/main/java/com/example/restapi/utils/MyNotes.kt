package com.example.restapi.utils

import java.io.Serializable

data class MyNotes(
    val id: Int,
    val bajarildi: Boolean,
    val batafsil: String,
    val oxirgi_muddat: String,
    val sana: String,
    val sarlavha: String,
    val zarurlik: String
): Serializable