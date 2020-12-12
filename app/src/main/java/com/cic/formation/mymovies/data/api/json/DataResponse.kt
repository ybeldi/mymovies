package com.cic.formation.mymovies.data.api.json

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

data class JsonResponse(
    var page: String,
    var results: List<Movies>
)

@Parcelize
@Entity(tableName = "movies")
data class Movies(
    @PrimaryKey
    var id: Int,
    var poster_path: String?,
    var title: String? = null,
    var vote_average: Double,
    var release_date: String,
    var overview: String
) : Parcelable
