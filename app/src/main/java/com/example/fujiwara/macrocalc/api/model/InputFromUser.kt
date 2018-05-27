package com.example.fujiwara.macrocalc.api.model

data class InputFromUser(override var title: String,
                         override var gender: Int,
                         override var age: Int,
                         override var weight: Double,
                         override var height: Double,
                         override var activityMult: Double,
                         override var goalMult: Double,
                         override var isImperial: Boolean) : UserInput {

    override var id: String
        get() = "Not defined"
        set(value) {}
    override var userId: String
        get() = "Not defined"
        set(value) {}
    override var createdAt: String
        get() = "Not defined"
        set(value) {}
    override var v: String
        get() = "Not defined"
        set(value) {}

    init {

        when (activityMult) {
            0.0 -> activityMult = 1.2       // Sedentary
            1.0 -> activityMult = 1.375     // Lightly active
            2.0 -> activityMult = 1.55      // Moderately active
            3.0 -> activityMult = 1.725     // Very active
            4.0 -> activityMult = 1.9       // Extremely active
        }

        when (goalMult) {
            0.0 -> goalMult = 0.85          // 15% Calorie deficit
            1.0 -> goalMult = 1.15          // 15% Calorie surplus
            2.0 -> goalMult = 1.0           // Caloric maintenance

        }
    }
}
