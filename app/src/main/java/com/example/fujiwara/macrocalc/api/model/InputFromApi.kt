package com.example.fujiwara.macrocalc.api.model

import com.google.gson.annotations.SerializedName

data class InputFromApi(override var title: String,
                        override var gender: Int,
                        override var age: Int,
                        override var weight: Double,
                        override var height: Double,
                        override var activityMult: Double,
                        override var goalMult: Double,
                        override var isImperial: Boolean,
                        @SerializedName("_id")
                        override var id: String,
                        @SerializedName("_userId")
                        override var userId: String,
                        override var createdAt: String,
                        @SerializedName("__v")
                        override var v: String) : UserInput {
}