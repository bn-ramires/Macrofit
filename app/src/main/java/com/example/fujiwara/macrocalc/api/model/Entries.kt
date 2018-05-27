package com.example.fujiwara.macrocalc.api.model

import com.google.gson.annotations.SerializedName
import java.util.*

class Entries(@SerializedName("entries")
                    var listOfEntries: List<InputFromApi>)