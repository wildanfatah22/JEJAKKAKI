package com.folu.jejakkaki.model

data class Taman(
    val id: Int,
    val namaTaman: String,
    val facebook: String?,
    val twitter: String?,
    val youtube: String?,
    val instagram: String?,
    val logo: Int,
    val logo2: Int?,
    val bgPic: Int,
    val info: Int?,
    val animals: List<Animal?>,
    val activities: List<Activity?>,
    val car1: Int? = null,
    val car2: Int? = null,
    val car3: Int? = null,
    val ar: Int? = null
)

data class Animal(
    val id: Int,
    val desc: Int,
    val pic: Int,
    val video: Int
)

data class Activity(
    val id: Int,
    val desc: Int,
    val pic: Int,
    val video: Int
)