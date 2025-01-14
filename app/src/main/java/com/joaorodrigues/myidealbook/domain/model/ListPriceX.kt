package com.joaorodrigues.myidealbook.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListPriceX(
    val amountInMicros: Int,
    val currencyCode: String
): Parcelable