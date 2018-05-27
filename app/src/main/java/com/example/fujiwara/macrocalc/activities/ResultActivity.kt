package com.example.fujiwara.macrocalc.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.fujiwara.macrocalc.R
import com.example.fujiwara.macrocalc.api.model.InputFromApi
import com.example.fujiwara.macrocalc.api.model.InputFromUser
import com.example.fujiwara.macrocalc.api.model.UserInput
import com.example.fujiwara.macrocalc.api.service.Api
import com.example.fujiwara.macrocalc.data.UserEntry
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.toast


class ResultActivity : AppCompatActivity() {

    val TAG = javaClass.simpleName
    var userInput: UserInput? = null
    val api = Api.Request(this)

    val testToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1YWQ3MTYzOTQ2OWF" +
            "hZjAwMTQyMmRmMDYiLCJhY2Nlc3MiOiJhdXRoIiwiaWF0IjoxNTI0MDUwMzE5fQ.d-hk1NPzp" +
            "c6c6a-UkDRtGed6ObHkRg3t8HtA_jDS4tA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Grabbing input from the previous screen
        val isNewEntry = intent.getStringExtra("isNewEntry")
        val entryTitle = intent.getStringExtra("entryTitle")
        val userGender = intent.getIntExtra("userGender", -1)
        val userAge = intent.getIntExtra("userAge", -1)
        val userWeight = intent.getDoubleExtra("userWeight", -1.00)
        val userHeight = intent.getDoubleExtra("userHeight", -1.00)
        val userActivity = intent.getDoubleExtra("userActivity", -1.00)
        val userGoal = intent.getDoubleExtra("userGoal", -1.00)
        val isImperial = intent.getBooleanExtra("isImperial", false)

        if (isNewEntry.equals("no")) {
            val id = intent.getStringExtra("id")
            val userId = intent.getStringExtra("userId")
            val createdAt = intent.getStringExtra("createdAt")
            val v = intent.getStringExtra("v")

            save_update_btn.text = getString(R.string.update_button)

            userInput = InputFromApi(
                    entryTitle,
                    userGender,
                    userAge,
                    userWeight,
                    userHeight,
                    userActivity,
                    userGoal,
                    isImperial,
                    id,
                    userId,
                    createdAt,
                    v)
        } else {
            userInput = InputFromUser(
                    entryTitle,
                    userGender,
                    userAge,
                    userWeight,
                    userHeight,
                    userActivity,
                    userGoal,
                    isImperial)
        }

        val userEntry = UserEntry(userInput!!)

        Log.e(TAG, userInput.toString())

        // Displaying calculation results to the screen
        fun displayResults() {
            title_tv.text = userEntry.title
            dailycal_results.text = "${userEntry.dailyCal}kcal"
            protein_results.text = "${userEntry.protein}g"
            carb_results.text = "${userEntry.carb}g"
            fat_results.text = "${userEntry.fat}g"
            tdee_results.setText("${userEntry.TDEE}")
            bmr_results.setText("${userEntry.BMR}")
        }

        displayResults()

        save_update_btn.setOnClickListener { _ ->

            // When entry is already in the cloud
            if (isNewEntry.equals("no")) {

                api.editEntry(userEntry.input.id, testToken, userInput!!)

                // When adding a new entry
            } else {
                api.newEntry(userInput as InputFromUser, testToken)
            }
        }

        delete_button.setOnClickListener { _ ->

            if (isNewEntry.equals("no")) {
                api.deleteEntry(userEntry.input.id, testToken)
            }

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        // Manually updating TDEE value
        tdee_tv.setOnClickListener { _ ->

            val newTdeeInput = tdee_results.text.toString().toInt()
            userEntry.updateTdee(newTdeeInput)
            displayResults()
        }

        // Manually updating BMR value
        bmr_tv.setOnClickListener { _ ->

            val newBmrInput = bmr_results.text.toString().toInt()
            userEntry.updateBmr(newBmrInput)

            displayResults()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        toast("Back button pressed.")
    }
}
