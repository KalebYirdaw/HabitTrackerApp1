package com.example.habittrackerapp.services

import android.content.Context
import com.example.habittrackerapp.models.JournalEntry
import org.json.JSONArray
import org.json.JSONObject

class JournalStorageService(
    private val context: Context
) {

    companion object {
        private const val PREFS_NAME = "journal_storage"
        private const val KEY_ENTRIES = "journal_entries"
    }

    fun saveEntries(entries: List<JournalEntry>) {

        val jsonArray = JSONArray()

        entries.forEach { entry ->

            val jsonObject = JSONObject()

            jsonObject.put("id", entry.id)
            jsonObject.put("text", entry.text)
            jsonObject.put("date", entry.date)

            jsonArray.put(jsonObject)
        }

        context
            .getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )
            .edit()
            .putString(
                KEY_ENTRIES,
                jsonArray.toString()
            )
            .apply()
    }

    fun getEntries(): MutableList<JournalEntry> {

        val entries = mutableListOf<JournalEntry>()

        val preferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        val jsonString = preferences.getString(
            KEY_ENTRIES,
            null
        )

        if (jsonString.isNullOrBlank()) {
            return entries
        }

        try {

            val jsonArray = JSONArray(jsonString)

            for (i in 0 until jsonArray.length()) {

                val jsonObject = jsonArray.getJSONObject(i)

                val entry = JournalEntry(
                    id = jsonObject.getString("id"),
                    text = jsonObject.getString("text"),
                    date = jsonObject.getString("date")
                )

                entries.add(entry)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return entries
    }
}