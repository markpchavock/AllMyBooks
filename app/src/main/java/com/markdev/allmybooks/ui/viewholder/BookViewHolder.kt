package com.markdev.allmybooks.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.markdev.allmybooks.R
import com.markdev.allmybooks.databinding.ItemBookBinding
import com.markdev.allmybooks.entity.BookEntity
import com.markdev.allmybooks.ui.listener.BookListener

//RecyleView - 4° Passo - Criação do ViewHolder ()
//Antes de configurar o  viewHolder, é necessario criar um itemView, que
//é a linha que será criada para colocar os elementos da RecView.

//RecyleView - 6° Passo - Atribuir o itemBook na classe viewHolder()

class BookViewHolder(private val item: ItemBookBinding,private val listener: BookListener) : RecyclerView.ViewHolder(item.root) {

    //RecyleView - 11° Passo - Vamos criar o método na viewHolder que irá atribuir os elementos de layout(depois)

    fun bind(book:BookEntity){
        item.textviewTitle.text = book.title
        item.textviewGenre.text = book.genre
        item.textviewAuthor.text = book.author

        item.textviewTitle.setOnClickListener { listener.onClick(book.id) }

        item.imageviewFavorite.setOnClickListener { listener.onFavoriteClick(book.id) }

        setGenreBackground(book.genre)
        updateFavoriteIcon(book.favorite)
    }

    private fun setGenreBackground(genre: String){
        when (genre) {
            "Terror" -> {
                item.textviewGenre.setBackgroundResource(R.drawable.rounded_label_red)
            }
            "Fantasia" -> {
                item.textviewGenre.setBackgroundResource(R.drawable.rounded_label_fantasy)
            }
            else -> {
                item.textviewGenre.setBackgroundResource(R.drawable.rounded_label_teal)
            }
        }
    }

    private fun updateFavoriteIcon(favorite: Boolean){
        if(favorite){
            item.imageviewFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            item.imageviewFavorite.setImageResource(R.drawable.ic_favorite_empty)
        }
    }

}