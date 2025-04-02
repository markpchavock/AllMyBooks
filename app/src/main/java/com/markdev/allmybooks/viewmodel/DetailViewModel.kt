package com.markdev.allmybooks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.markdev.allmybooks.entity.BookEntity
import com.markdev.allmybooks.repository.BookRepository

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: BookRepository = BookRepository.getInstance(application.applicationContext)

    private val _book = MutableLiveData<BookEntity>()
    val books: LiveData<BookEntity> = _book

    private val _bookRemoval = MutableLiveData<Boolean>()
    val bookRemoval: LiveData<Boolean> = _bookRemoval

    fun getBookById (id: Int){
       _book.value = repository.getBookById(id)
    }

    fun deleteBook (id:Int){
       _bookRemoval.value =  repository.deleteBook(id)
    }

    fun favorite(id: Int){
        repository.toggleFavoriteStatus(id)
    }
}