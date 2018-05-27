package com.example.fujiwara.macrocalc.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.example.fujiwara.macrocalc.R
import kotlinx.android.synthetic.main.activity_add_user.*

class AddUserActivity : AppCompatActivity() {

    val TAG = javaClass.simpleName

    // Lists with entries for all spinners
    var list_of_genders = arrayOf("Male", "Female")
    var list_of_activity_mult = arrayOf(
            "Sedentary", "Light", "Moderate", "High", "Extreme")
    val list_of_goals = arrayOf("Cut 15%", "Bulk 15%", "Maintain 0%")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        // Adapters for all spinners
        val genderAdapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, list_of_genders)
        gender_spinner.adapter = genderAdapter
        val activityAdapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, list_of_activity_mult)
        activity_spinner.adapter = activityAdapter
        val goalAdapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, list_of_goals)
        goal_spinner.adapter = goalAdapter

//        // Auto-populate for testing purposes
//        title_input.setText("Summer Shredz")
//        weight_input.setText("77")
//        height_input.setText("169")
//        age_input.setText("28")
//        activity_spinner.setSelection(1)
//        goal_spinner.setSelection(0)

        confirm_button.setOnClickListener { _ ->

            // Grouping all input input to be sent to the next activity
            val entryTitle = title_input.text.toString()
            val userAge = age_input.text.toString().toInt()
            val userWeight = weight_input.text.toString().toDouble()
            val userHeight = height_input.text.toString().toDouble()
            val userGender = gender_spinner.selectedItemPosition
            val userActivity = activity_spinner.selectedItemPosition.toDouble()
            val userGoal = goal_spinner.selectedItemPosition.toDouble()
            val isImperial = unit_conversion_switch.isChecked

            var i = Intent(this, ResultActivity::class.java)
            i.putExtra("isNewEntry", "yes")
            i.putExtra("entryTitle", entryTitle)
            i.putExtra("userGender", userGender)
            i.putExtra("userAge", userAge)
            i.putExtra("userWeight", userWeight)
            i.putExtra("userHeight", userHeight)
            i.putExtra("userActivity", userActivity)
            i.putExtra("userGoal", userGoal)
            i.putExtra("isImperial", isImperial)
            startActivity(i)

        }
    }
}
