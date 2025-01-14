package com.joaorodrigues.myidealbook.domain.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Ignore
import kotlinx.parcelize.Parcelize

@Parcelize
data class VolumeInfo(
    var allowAnonLogging: Boolean,
    var authors: List<String>,
    var canonicalVolumeLink: String,
    @Ignore val categories: List<String>,
    var contentVersion: String,
    var description: String,
    @Embedded var imageLinks: ImageLinks,
    var infoLink: String,
    var language: String,
    var maturityRating: String,
    var pageCount: Int,
    var previewLink: String,
    var printType: String,
    var publishedDate: String,
    var publisher: String,
    var subtitle: String,
    var title: String
) : Parcelable {

    constructor() : this(
        false, // allowAnonLogging
        emptyList(), // authors
        "", // canonicalVolumeLink
        emptyList(), // categories (ignorado pelo Room)
        "", // contentVersion
        "", // description
        ImageLinks("", ""), // imageLinks
        "", // infoLink
        "", // language
        "", // maturityRating
        0, // pageCount
        "", // previewLink
        "", // printType
        "", // publishedDate
        "", // publisher
        "", // subtitle
        "" // title
    )
}