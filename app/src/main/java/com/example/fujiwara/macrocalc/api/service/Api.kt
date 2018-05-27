package com.example.fujiwara.macrocalc.api.service

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.fujiwara.macrocalc.R
import com.example.fujiwara.macrocalc.api.model.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {

    private val TAG: String = javaClass.simpleName
    val client: HttpClient

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://young-spire-16181.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        client = retrofit.create(HttpClient::class.java)
    }

    class Request(var context: Context) {

        var user = User("", "", "")
        var inputFromApi: InputFromApi? = null
        var entries: Entries = Entries(listOf())

        fun showToast(message: String) {
            Toast.makeText(context, message,
                    Toast.LENGTH_SHORT).show()
        }

        /**
         * Attempts to log in an user to the server.
         * It returns an object with user details if successful or an error if not.
         *
         * @param credentials Login object used for authentication
         * @see user User info when login is successful.
         */
        fun login(credentials: Login) {

            val call = client.login(credentials)

            call.enqueue(object : retrofit2.Callback<User> {
                override fun onFailure(call: Call<User>?, t: Throwable?) {
                    Log.e(TAG, t.toString())
                    showToast(context.getString(R.string.internet_error))
                }

                override fun onResponse(call: Call<User>?,
                                        response: retrofit2.Response<User>?) {

                    if (response!!.isSuccessful) {

                        user = response.body()!!
                        user.token = response.headers().get("x-auth").toString()
                        Log.e(TAG, user.toString())

                    } else {
                        Log.e(TAG, response.toString())
                    }
                }
            })
        }

        fun signin(credentials: Login) {

            val call = client.signIn(credentials)

            call.enqueue(object : retrofit2.Callback<User> {
                override fun onFailure(call: Call<User>?, t: Throwable?) {
                    Log.e(TAG, t.toString())
                    showToast(context.getString(R.string.internet_error))
                }

                override fun onResponse(call: Call<User>?,
                                        response: retrofit2.Response<User>?) {

                    if (response!!.isSuccessful) {

                        user = response.body()!!
                        user.token = response.headers().get("x-auth").toString()
                        Log.e(TAG, user.toString())

                    } else {
                        Log.e(TAG, response.toString())
                    }

                }
            })
        }

        fun logout(token: String) {

            val call = client.logout(token)

            call.enqueue(object : retrofit2.Callback<Void> {
                override fun onFailure(call: Call<Void>?, t: Throwable?) {
                    Log.e(TAG, t.toString())
                    showToast(context.getString(R.string.internet_error))
                }

                override fun onResponse(call: Call<Void>?,
                                        response: retrofit2.Response<Void>?) {

                    if (response!!.isSuccessful) {
                        Log.e(TAG, "Logging out SUCESSFUL!")
                    } else {
                        Log.e(TAG, "Logging out FAILED!")
                    }
                }
            })
        }

        fun newEntry(input: InputFromUser, token: String) {

            val call = client.newEntry(token, input)

            call.enqueue(object : retrofit2.Callback<InputFromApi> {
                override fun onFailure(call: Call<InputFromApi>?, t: Throwable?) {
                    Log.e(TAG, t.toString())
                    showToast(context.getString(R.string.internet_error))
                }

                override fun onResponse(call: Call<InputFromApi>?,
                                        response: retrofit2.Response<InputFromApi>?) {

                    if (response!!.isSuccessful) {

                        inputFromApi = response.body()
                        Log.e(TAG, inputFromApi.toString())

                        showToast(context.getString(R.string.entry_added))

                    } else {
                        Log.e(TAG, response.toString())
                    }
                }
            })
        }

        fun listOfEntries(token: String) {

            val call = client.listOfEntries(token)

            call.enqueue(object : retrofit2.Callback<Entries> {
                override fun onFailure(call: Call<Entries>?, t: Throwable?) {
                    Log.e(TAG, t.toString())
                    showToast(context.getString(R.string.internet_error))
                }

                override fun onResponse(call: Call<Entries>?,
                                        response: retrofit2.Response<Entries>?) {

                    if (response!!.isSuccessful) {

                        entries = response.body()!!
                        Log.e(TAG, entries.listOfEntries.toString())


                    } else {
                        Log.e(TAG, response.toString())
                    }
                }
            })
        }

        fun deleteEntry(entryId: String, token: String) {

            val call = client.deleteEntry(token, entryId)

            call.enqueue(object : retrofit2.Callback<Void> {
                override fun onFailure(call: Call<Void>?, t: Throwable?) {
                    Log.e(TAG, t.toString())
                    showToast(context.getString(R.string.internet_error))
                }

                override fun onResponse(call: Call<Void>?,
                                        response: retrofit2.Response<Void>?) {

                    if (response!!.isSuccessful) {
                        showToast(context.getString(R.string.entry_deleted))
                    } else {
                        showToast(context.getString(R.string.something_went_wrong))
                    }
                }
            })
        }

        fun editEntry(entryId: String, token: String, input: UserInput) {

            val call = client.editEntry(token, entryId, input)

            call.enqueue(object : retrofit2.Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                    Log.e(TAG, t.toString())
                    showToast(context.getString(R.string.internet_error))
                }

                override fun onResponse(call: Call<JsonObject>?,
                                        response: retrofit2.Response<JsonObject>?) {

                    if (response!!.isSuccessful) {

                        // Populating InputFromApi with the contents of the "result" key
                        val correctKey = response.body()?.get("result")
                        inputFromApi = Gson().fromJson(correctKey, InputFromApi::class.java)

                        showToast(context.getString(R.string.entry_edited))
                        Log.e("editEntry()", inputFromApi.toString())
                    } else {
                        Log.e(TAG, response.toString())
                    }
                }
            })
        }
    }
}