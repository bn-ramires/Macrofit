package com.example.fujiwara.macrocalc.adapters

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.fujiwara.macrocalc.R
import com.example.fujiwara.macrocalc.activities.ResultActivity
import com.example.fujiwara.macrocalc.api.model.Entries
import com.example.fujiwara.macrocalc.api.model.InputFromApi


class EntriesAdapter(list: Entries, context: Context) :
        RecyclerView.Adapter<EntriesAdapter.ViewHolder>() {

    var listOfEntries = listOf<InputFromApi>()
    var entry: InputFromApi? = null
    var currentContext = context

    init {
        listOfEntries = list.listOfEntries
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context)
                .inflate(R.layout.list_of_entries, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listOfEntries.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        val currentEntry: InputFromApi = listOfEntries[position]

        holder?.entryTitle?.text = currentEntry.title

    }

    fun showResults(context: Context) {

        val intent = Intent(context, ResultActivity::class.java)
        intent.putExtra("isNewEntry", "no")
        intent.putExtra("entryTitle", entry?.title)
        intent.putExtra("userGender", entry?.gender)
        intent.putExtra("userAge", entry?.age)
        intent.putExtra("userWeight", entry?.weight)
        intent.putExtra("userHeight", entry?.height)
        intent.putExtra("userActivity", entry?.activityMult)
        intent.putExtra("userGoal", entry?.goalMult)
        intent.putExtra("isImperial", entry?.isImperial)
        intent.putExtra("id", entry?.id)
        intent.putExtra("userId", entry?.userId)
        intent.putExtra("createdAt", entry?.createdAt)
        intent.putExtra("v", entry?.v)
        startActivity(context, intent, null)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val entryTitle: TextView = itemView.findViewById(R.id.entry_title)

        init {
            itemView.setOnClickListener { _ ->

                var position: Int = adapterPosition
                entry = listOfEntries[position]
                showResults(currentContext)
            }
        }
    }
}


