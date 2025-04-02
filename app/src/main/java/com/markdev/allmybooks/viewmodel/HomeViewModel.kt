package com.markdev.allmybooks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.markdev.allmybooks.entity.BookEntity
import com.markdev.allmybooks.repository.BookRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    //14.2 - Utilizamos do antigo texto gerado automaticamente pelo AndroidStudio e foi alterado a variavel text para books
    // Ordem: A função getAllBooks pega a lista de livros do repositório e atribui à variável _books,
    // que por sua vez é atribuida à variavel books, que esta sendo observada.

    private val _books = MutableLiveData<List<BookEntity>>()
    val books: LiveData<List<BookEntity>> = _books



    //14.1
    private val repository = BookRepository.getInstance(application.applicationContext)

    //RecyleView - 14° Passo - Criar um método na viewModel que retorne todos os livros do repository
    fun getAllBooks() {
        _books.value = repository.getAllBooks()
    }

    fun favorite(id: Int){
        repository.toggleFavoriteStatus(id)
    }
}