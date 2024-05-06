package com.harry_potter_app

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button


// Use the BookMovie class for the items
class ItemsAdapter(private val items: MutableList<BookMovie>) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItemName: TextView = itemView.findViewById(R.id.tvItemName)
        val checkBoxFinished: CheckBox = itemView.findViewById(R.id.checkBoxFinished)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)

        init {
            checkBoxFinished.setOnCheckedChangeListener { _, isChecked ->
                // position is valid within the adapter's bounds
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val bookMovie = items[adapterPosition]
                    bookMovie.isFinished = isChecked
                    tvItemName.paintFlags = if (isChecked) {
                        tvItemName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    } else {
                        tvItemName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    }

                }
            }

            btnDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    items.removeAt(position)
                    notifyItemRemoved(position)

                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_book_movie, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvItemName.text = item.title
        holder.checkBoxFinished.isChecked = item.isFinished
        holder.tvItemName.paintFlags = if (item.isFinished) {
            holder.tvItemName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.tvItemName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun getItemCount(): Int = items.size


    // Method to add a BookMovie item
    fun addItem(item: BookMovie) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun clearItems() {
        items.clear() // Clear the list
        notifyDataSetChanged() // Notify adapter to refresh the RecyclerView
    }

}
