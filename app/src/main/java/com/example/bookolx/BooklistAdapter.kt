package com.example.bookolx

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BooklistAdapter(private val bookList : ArrayList<Book>) : RecyclerView.Adapter<BooklistAdapter.BooklistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooklistViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.booklist_list_element, parent, false)
        return BooklistViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BooklistViewHolder, position: Int) {
        val currentItem = bookList[position]
        holder.title.text = currentItem.title
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    class BooklistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title : TextView = itemView.findViewById(R.id.textView)
    }
}