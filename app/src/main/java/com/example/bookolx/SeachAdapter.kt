package com.example.bookolx

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SeachAdapter(private val booklist : ArrayList<Book>) : RecyclerView.Adapter<SeachAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.searchlist_list_element, parent, false)
        return SeachAdapter.SearchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentItem = booklist[position]

        holder.title.text = currentItem.title
        holder.price.text = currentItem.price.toString()
    }

    override fun getItemCount(): Int {
        return booklist.size
    }

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.searchBookTitle)
        val price = itemView.findViewById<TextView>(R.id.searchBookPrice)

        val buyButton = itemView.findViewById<Button>(R.id.searchBuyButton)
        val addToWishListButton = itemView.findViewById<Button>(R.id.searchWishlistButton)
    }
}