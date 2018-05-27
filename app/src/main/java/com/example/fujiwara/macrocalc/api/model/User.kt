package com.example.fujiwara.macrocalc.api.model

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("_id")
                var id: String,
                var email: String,
                var token: String)
