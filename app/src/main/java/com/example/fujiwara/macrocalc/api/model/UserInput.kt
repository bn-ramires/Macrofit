package com.example.fujiwara.macrocalc.api.model

interface UserInput{

    var title: String // Entry's title
    var gender: Int // 0 = Male, 1 = Female
    var age: Int
    var weight: Double
    var height: Double
    var activityMult: Double
    var goalMult: Double
    var isImperial: Boolean
    var id: String
    var userId: String
    var createdAt: String
    var v: String

}
