
package com.example.habittrackerapp.ui.habits

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrackerapp.R
import com.example.habittrackerapp.models.JournalEntry
import com.example.habittrackerapp.services.JournalStorageService
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class JournalFragment : Fragment() {

    private lateinit var journalStorageService: JournalStorageService

    private lateinit var journalEntryEditText: EditText
    private lateinit var saveJournalButton: Button
    private lateinit var journalRecyclerView: RecyclerView
    private lateinit var journalAdapter: JournalAdapter

    private val journalEntries = mutableListOf<JournalEntry>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_journal,
            container,
            false
        )

        // ---------------------------------------------------------
        // STORAGE
        // ---------------------------------------------------------

        journalStorageService =
            JournalStorageService(requireContext())

        // ---------------------------------------------------------
        // FIND VIEWS
        // ---------------------------------------------------------

        journalEntryEditText =
            view.findViewById(R.id.etJournalEntry)

        saveJournalButton =
            view.findViewById(R.id.btnSaveJournal)

        journalRecyclerView =
            view.findViewById(R.id.rvJournalEntries)

        // ---------------------------------------------------------
        // LOAD SAVED ENTRIES
        // ---------------------------------------------------------

        journalEntries.clear()

        journalEntries.addAll(
            journalStorageService.getEntries()
        )

        // ---------------------------------------------------------
        // SET UP RECYCLERVIEW
        // ---------------------------------------------------------

        journalAdapter =
            JournalAdapter(
                journalEntries,

                // Tap entry to edit
                { entry ->
                    showEditDialog(entry)
                },

                // Tap delete button
                { entry ->
                    showDeleteConfirmation(entry)
                }
            )

        journalRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        journalRecyclerView.adapter =
            journalAdapter

        // ---------------------------------------------------------
        // SAVE BUTTON
        // ---------------------------------------------------------

        saveJournalButton.setOnClickListener {
            saveJournalEntry()
        }

        return view
    }

    // =============================================================
    // SAVE JOURNAL ENTRY
    // =============================================================

    private fun saveJournalEntry() {

        val text =
            journalEntryEditText.text
                .toString()
                .trim()

        if (text.isEmpty()) {

            Toast.makeText(
                requireContext(),
                "Please write something first.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val date =
            SimpleDateFormat(
                "dd MMMM yyyy",
                Locale.getDefault()
            ).format(Date())

        val entry = JournalEntry(
            id = System.currentTimeMillis().toString(),
            text = text,
            date = date
        )

        journalEntries.add(0, entry)

        journalStorageService.saveEntries(
            journalEntries
        )

        journalAdapter.notifyItemInserted(0)

        journalRecyclerView.scrollToPosition(0)

        journalEntryEditText.text.clear()

        Toast.makeText(
            requireContext(),
            "Journal entry saved!",
            Toast.LENGTH_SHORT
        ).show()
    }

    // =============================================================
    // EDIT JOURNAL ENTRY
    // =============================================================

    private fun showEditDialog(entry: JournalEntry) {

        val editText = EditText(requireContext())

        editText.setText(entry.text)

        editText.setSelection(
            editText.text.length
        )

        editText.minLines = 4

        editText.gravity =
            android.view.Gravity.TOP or
                    android.view.Gravity.START

        val dialog =
            AlertDialog.Builder(requireContext())
                .setTitle("Edit Journal Entry")
                .setView(editText)
                .setNegativeButton(
                    "Cancel",
                    null
                )
                .setPositiveButton(
                    "Save"
                ) { _, _ ->

                    val updatedText =
                        editText.text
                            .toString()
                            .trim()

                    if (updatedText.isEmpty()) {

                        Toast.makeText(
                            requireContext(),
                            "Journal entry cannot be empty.",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@setPositiveButton
                    }

                    val index =
                        journalEntries.indexOfFirst {
                            it.id == entry.id
                        }

                    if (index != -1) {

                        journalEntries[index] =
                            JournalEntry(
                                id = entry.id,
                                text = updatedText,
                                date = entry.date
                            )

                        journalStorageService.saveEntries(
                            journalEntries
                        )

                        journalAdapter.notifyItemChanged(
                            index
                        )

                        Toast.makeText(
                            requireContext(),
                            "Journal entry updated!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .create()

        dialog.show()
    }

    // =============================================================
    // DELETE CONFIRMATION
    // =============================================================

    private fun showDeleteConfirmation(
        entry: JournalEntry
    ) {

        AlertDialog.Builder(requireContext())
            .setTitle("Delete Journal Entry")
            .setMessage(
                "Are you sure you want to delete this journal entry?"
            )
            .setNegativeButton(
                "Cancel",
                null
            )
            .setPositiveButton(
                "Delete"
            ) { _, _ ->

                deleteJournalEntry(entry)
            }
            .show()
    }

    // =============================================================
    // DELETE JOURNAL ENTRY
    // =============================================================

    private fun deleteJournalEntry(
        entry: JournalEntry
    ) {

        val index =
            journalEntries.indexOfFirst {
                it.id == entry.id
            }

        if (index == -1) {
            return
        }

        journalEntries.removeAt(index)

        journalStorageService.saveEntries(
            journalEntries
        )

        journalAdapter.notifyItemRemoved(index)

        Toast.makeText(
            requireContext(),
            "Journal entry deleted.",
            Toast.LENGTH_SHORT
        ).show()
    }
}
