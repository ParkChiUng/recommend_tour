package com.recommend_tour.data

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("response")
    var response: Response<T>? = null
)

data class Response<T>(
    @SerializedName("header")
    var header: Header? = null,

    @SerializedName("body")
    var body: Body<T>? = null
)

data class Header(
    @SerializedName("resultCode")
    var resultCode: String? = null,

    @SerializedName("resultMsg")
    var resultMsg: String? = null
)

data class Body<T>(
    @SerializedName("items")
    var items: T? = null,

    @SerializedName("numOfRows")
    var numOfRows: Int,

    @SerializedName("pageNo")
    var pageNo: Int,

    @SerializedName("totalCount")
    var totalCount: Int
)

data class TourItems(
    @SerializedName("item")
    var item: List<TourItem>? = null
)

data class AreaCodeItems(
    @SerializedName("item")
    var item: List<AreaItem>? = null
)

data class TourDetailItems(
    @SerializedName("item")
    var item: List<IntroductionItem>? = null
)
