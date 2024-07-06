package com.folu.jejakkaki.model

import android.os.Parcelable
import com.folu.jejakkaki.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Place(
    val id: Int,
    val name: String,
    val image: Int,
) : Parcelable