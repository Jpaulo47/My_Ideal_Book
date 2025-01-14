package com.joaorodrigues.myidealbook.domain.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class BookModel(
    @Ignore val accessInfo: AccessInfo?,
    var etag: String,
    @PrimaryKey var id: String,
    var kind: String,
    @Ignore val saleInfo: SaleInfo?,
    @Ignore val searchInfo: SearchInfo?,
    @Ignore val selfLink: String,
    @Embedded var volumeInfo: VolumeInfo
) : Parcelable {

    constructor() : this(
        null,
        "",
        "",
        "",
        null,
        null,
        "",
        VolumeInfo()
    )
}
