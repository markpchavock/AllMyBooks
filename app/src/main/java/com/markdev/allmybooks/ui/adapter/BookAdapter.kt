package com.markdev.allmybooks.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.markdev.allmybooks.databinding.ItemBookBinding
import com.markdev.allmybooks.entity.BookEntity
import com.markdev.allmybooks.ui.listener.BookListener
import com.markdev.allmybooks.ui.viewholder.BookViewHolder


private var bookList: List<BookEntity> = listOf()
private lateinit var bookListener: BookListener


class BookAdapter : RecyclerView.Adapter<BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {

        val view = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return BookViewHolder(view, bookListener)

    }


    override fun getItemCount(): Int {

        return bookList.size
    }


    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {

        holder.bind(bookList[position])
    }


    fun updateBooks(list: List<BookEntity>){
        bookList = list
        notifyDataSetChanged()
    }

    fun attachListener(listener: BookListener){
        bookListener = listener
    }
}