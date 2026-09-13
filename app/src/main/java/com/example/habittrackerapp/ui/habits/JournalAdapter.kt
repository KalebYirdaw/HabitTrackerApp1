
package com.example.habittrackerapp.ui.habits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrackerapp.R
import com.example.habittrackerapp.models.JournalEntry

class JournalAdapter(
    private val entries: List<JournalEntry>,
    private val onEntryClick: (JournalEntry) -> Unit,
    private val onDeleteClick: (JournalEntry) -> Unit
) : RecyclerView.Adapter<JournalAdapter.JournalViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JournalViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_journal_entry,
                parent,
                false
            )

        return JournalViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: JournalViewHolder,
        position: Int
    ) {

        val entry = entries[position]

        holder.bind(entry)

        // Tap the card to edit
        holder.itemView.setOnClickListener {
            onEntryClick(entry)
        }

        // Tap delete button to delete
        holder.deleteButton.setOnClickListener {
            onDeleteClick(entry)
        }
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    class JournalViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val dateTextView: TextView =
            itemView.findViewById(R.id.tvJournalDate)

        private val textTextView: TextView =
            itemView.findViewById(R.id.tvJournalText)

        val deleteButton: Button =
            itemView.findViewById(R.id.btnDeleteJournal)

        fun bind(entry: JournalEntry) {

            dateTextView.text = entry.date

            textTextView.text = entry.text
        }
    }
}

