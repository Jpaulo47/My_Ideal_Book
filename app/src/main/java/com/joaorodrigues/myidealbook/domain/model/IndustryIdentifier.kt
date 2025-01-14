package com.joaorodrigues.myidealbook.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IndustryIdentifier(
    val identifier: String,
    val type: String
): Parcelable