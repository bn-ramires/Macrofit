package com.example.fujiwara.macrocalc.data

import com.example.fujiwara.macrocalc.api.model.UserInput

class UserEntry(var input: UserInput) {

    var title = input.title

    var protein = -1
    var carb = -1
    var fat = -1
    var BMR = -1
    var TDEE = -1
    var dailyCal = -1

    var proteinCal = -1
    var carbCal = -1
    var fatCal = -1

    var weightMetric = -1.00
    var heightMetric = -1.00

    // Default values
    var proteinMultiplier = 1.8
    var fatMultiplier = 0.25

    // Performs all necessary calculations for a new entry
    init {

        unitConvert(input.isImperial)
        calcBmr(input.gender, weightMetric, heightMetric, input.age)
        calcTdee()
        calcDailyCal()
        calcMacros()

    }

    fun unitConvert(isImperial: Boolean) {
        if (isImperial) {
            weightMetric = input.weight * 0.453592
            heightMetric = input.height * 2.54
        } else {
            weightMetric = input.weight
            heightMetric = input.height
        }
    }

    // Calculate macros. Calling order: Protein, Fat, Carb
    fun calcMacros() {
        calcProtein(proteinMultiplier, weightMetric)
        calcFat(fatMultiplier)
        calcCarb()
    }

    // Calculates protein intake in grams and calories
    fun calcProtein(proteinMult: Double, userWeight: Double) {

        protein = (userWeight * proteinMult).toInt()
        proteinCal = protein * 4
    }

    // Calculates fat intake in grams and calories
    fun calcFat(fatMult: Double) {
        fatCal = (dailyCal * fatMult).toInt()
        fat = fatCal / 9

    }

    // Calculates carbs intake in grams and calories
    fun calcCarb() {
        carbCal = dailyCal - (proteinCal + fatCal)
        carb = (carbCal / 4)
    }

    // Calculates input's BMR
    fun calcBmr(userGender: Int, userWeight: Double, userHeight: Double, userAge: Int) {

        // Calculations for males
        if (userGender == 0) {
            BMR = ((10 * userWeight) +
                    (6.25 * userHeight) - (5 * userAge) + 5).toInt()
        }

        // Calculations for females
        if (userGender == 1) {
            BMR = ((10 * userWeight) +
                    (6.25 * userHeight) - (5 * userAge) - 161).toInt()
        }
    }

    // Calculates input's TDEE
    fun calcTdee() {
        TDEE = (BMR * input.activityMult).toInt()
    }

    // Calculates input's total daily calories
    fun calcDailyCal() {
        dailyCal = (TDEE * input.goalMult).toInt()

    }

    // Updates input's BMR manually and recalculates TDEE and Macros
    fun updateBmr(newBmr: Int) {
        BMR = newBmr
        calcTdee()
        calcDailyCal()
        calcMacros()
    }

    // Updates input's TDEE manually and recalculates Macros
    fun updateTdee(newTdee: Int) {
        TDEE = newTdee
        calcDailyCal()
        calcMacros()
    }
}




