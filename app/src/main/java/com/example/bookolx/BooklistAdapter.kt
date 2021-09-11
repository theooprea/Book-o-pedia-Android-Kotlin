package com.example.bookolx

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.bookolx.booklist.BooklistFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BooklistAdapter(private val bookList : ArrayList<Book>, private val token: String, private val username: String, private val fragment: Fragment) : RecyclerView.Adapter<BooklistAdapter.BooklistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooklistViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.booklist_list_element, parent, false)
        return BooklistViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BooklistViewHolder, position: Int) {
        val currentItem = bookList[position]
        holder.title.text = currentItem.title

        holder.buttonRemove.setOnClickListener {
            deleteinAPI(bookList.get(position).title, position)
            notifyDataSetChanged()
        }

        holder.buttonEdit.setOnClickListener {
            val action = BooklistFragmentDirections.actionBooklistFragmentToEditbookFragment(token, username,
                currentItem.title, currentItem.author, currentItem.genre, currentItem.pages, currentItem.price.toFloat(),
                currentItem.quantity)
            NavHostFragment.findNavController(fragment).navigate(action)
        }
    }

    fun deleteinAPI(title: String, position: Int) {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = "Bearer " + token

        CoroutineScope(Dispatchers.IO).launch {
            val response = BookApi.retrofitService.deleteUserBook(username, title, headerMap)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    bookList.removeAt(position)
                    notifyDataSetChanged()
                    Log.i("BooklistAdapter", "A mers")
                }
                else {
                    Log.i("BooklistAdapter", "Nu a mers")
                }
            }
        }

        Log.i("BooklistAdapter", title + " " + token + " " + username)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    class BooklistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val title : TextView = itemView.findViewById(R.id.textView)
        val buttonRemove = itemView.findViewById<Button>(R.id.booklistRemoveButtom)
        val buttonEdit = itemView.findViewById<Button>(R.id.booklistEditButton)

        override fun onClick(v: View?) {
            Log.i("BooklistAdapter", "s-a apasat elementul")
        }

    }
}