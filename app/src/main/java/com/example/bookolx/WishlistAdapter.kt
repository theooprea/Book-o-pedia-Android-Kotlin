package com.example.bookolx

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WishlistAdapter(private val wishlist : ArrayList<Book>, private val token: String, private val username: String, private val fragment: Fragment) : RecyclerView.Adapter<WishlistAdapter.WishlistViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WishlistAdapter.WishlistViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.wishlist_list_element, parent, false)
        return WishlistViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WishlistViewHolder, position: Int) {
        val currentItem = wishlist[position]
        holder.title.text = currentItem.title

        holder.removeButton.setOnClickListener {
            deleteinAPI(wishlist.get(position).title, position)
            notifyDataSetChanged()
        }
    }

    fun deleteinAPI(title: String, position: Int) {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = "Bearer " + token

        CoroutineScope(Dispatchers.IO).launch {
            val response = BookApi.retrofitService.deleteUserWishlistBook(username, title, headerMap)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    wishlist.removeAt(position)
                    notifyDataSetChanged()
                    Log.i("WishlistAdapter", "A mers")
                }
                else {
                    Log.i("WishlistAdapter", "Nu a mers")
                }
            }
        }

        Log.i("WishlistAdapter", title + " " + token + " " + username)
    }

    override fun getItemCount(): Int {
        return wishlist.size
    }

    class WishlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title : TextView = itemView.findViewById(R.id.textView4)
        val buyButton = itemView.findViewById<Button>(R.id.wishlistBuyButton)
        val removeButton = itemView.findViewById<Button>(R.id.wishlistRemoveButton)
    }
}