package com.example.fujiwara.macrocalc.api.service

import com.example.fujiwara.macrocalc.api.model.*
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface HttpClient {

    @POST("/users/login")
    fun login(@Body login: Login): Call<User>

    @POST("/users")
    fun signIn(@Body login: Login): Call<User>

    @DELETE("/users/me/token")
    fun logout(@Header("x-auth") token: String): Call<Void>

    @POST("/entries")
    fun newEntry(@Header("x-auth") token: String,
                 @Body entry: InputFromUser): Call<InputFromApi>

    @GET("/entries")
    fun listOfEntries(@Header("x-auth") token: String): Call<Entries>

    @DELETE("/entries/{id}")
    fun deleteEntry(@Header("x-auth") token: String,
                    @Path("id") entryId: String): Call<Void>

    @PATCH("/entries/{id}")
    fun editEntry(@Header("x-auth") token: String,
                  @Path("id") entryId: String,
                  @Body entry: UserInput): Call<JsonObject>

}
