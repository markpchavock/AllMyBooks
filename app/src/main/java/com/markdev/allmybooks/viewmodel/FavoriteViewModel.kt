package com.markdev.allmybooks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.markdev.allmybooks.entity.BookEntity
import com.markdev.allmybooks.repository.BookRepository

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val _books = MutableLiveData<List<BookEntity>>()
    val books: LiveData<List<BookEntity>> = _books


    private val repository = BookRepository.getInstance(application.applicationContext)

    fun getFavoriteBooks() {
        _books.value = repository.getFavoriteBooks()
    }

    fun favorite(id: Int){
        repository.toggleFavoriteStatus(id)
    }
}