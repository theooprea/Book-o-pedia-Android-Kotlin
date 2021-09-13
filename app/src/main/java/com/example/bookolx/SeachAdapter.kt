package com.example.bookolx

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class SeachAdapter(private val booklist : ArrayList<Book>, private val token: String, private val username: String, private val fragment: Fragment) : RecyclerView.Adapter<SeachAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.searchlist_list_element, parent, false)
        return SeachAdapter.SearchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentItem = booklist[position]

        holder.title.text = currentItem.title
        holder.price.text = currentItem.price.toString()

        holder.addToWishListButton.setOnClickListener {
            addToWishList(currentItem.title, currentItem.author, currentItem.genre, currentItem.pages.toString(),
                currentItem.price.toString(), currentItem.quantity.toString(), currentItem.seller, )
        }

        holder.buyButton.setOnClickListener {
            buyBook(currentItem.title)
        }
    }

    private fun getShareIntent(title: String) : Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, "Hi! I want to buy " + title)
        return shareIntent
    }


    private fun buyBook(title: String) {
        fragment.startActivity(getShareIntent(title))
    }

    fun addToWishList(title: String, author: String, genre: String, pages: String, price: String, quantity: String, seller: String) {
        if (title == "" || author == "" || genre == "" ||
            pages == "" || price == "" || quantity == "" ) {
            Log.i("AddbookViewModel", "ceva e null")
            return
        }

        val jsonObject = JSONObject()
        jsonObject.put("title", title)
        jsonObject.put("author", author)
        jsonObject.put("genre", genre)
        jsonObject.put("pages", pages.toInt())
        jsonObject.put("price", price.toDouble())
        jsonObject.put("quantity", quantity.toInt())
        jsonObject.put("seller", seller)

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = "Bearer " + token

        val jsonString = jsonObject.toString()
        val requestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            val response = BookApi.retrofitService.addUserWishBook(username, headerMap, requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )

                    Log.i("SearchAdapter", "A mers")
                    Log.i("SearchAdapter", prettyJson)
                }
                else {
                    Log.i("SearchAdapter", "Nu a mers " + response.code() + " " + response.message() + "\n"
                            + jsonString)
                }
            }
        }
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