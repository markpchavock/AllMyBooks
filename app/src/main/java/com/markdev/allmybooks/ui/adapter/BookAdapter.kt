package com.markdev.allmybooks.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.markdev.allmybooks.databinding.ItemBookBinding
import com.markdev.allmybooks.entity.BookEntity
import com.markdev.allmybooks.ui.listener.BookListener
import com.markdev.allmybooks.ui.viewholder.BookViewHolder

//RecyleView - 3° Passo - Criação do Adapter (conecta a lista de livros ao layout)
//OBS: Precisa criar também o viewHolder (4° passo)

//RecyleView - 7° Passo - Atribuir o Adapter à classe e atribuindo os membros (onCreate,getItem e OnBind..)


//RecyleView - 9° Passo - O adapter precisa receber a lista de livros, então foi criada uma lista do tipo BookEntity.
private var bookList: List<BookEntity> = listOf()
private lateinit var bookListener: BookListener


class BookAdapter : RecyclerView.Adapter<BookViewHolder>() {
    //Esse elemento é responsável por criar o elemento de layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        //RecyleView - 12° Passo - Vamos precisar criar uma view com código parecido com a criação de uma fragment:
        // OBS parâmetros do .inflate:
        //LayoutInflater - Precisa de um contexto, nesse caso vamos pegar o contexto do parent que vem da ViewGroup
        //parent - Onde o item será criado
        //attachToParent - False - Tem de ser falso pq nao quero me intrometer no ciclo de vida do
        //android,ele irá adicionara view quando necessário.
        val view = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return BookViewHolder(view, bookListener)
        // Preciso retornar a view instanciando a BookViewHolder pq essa view será manipulada.
    }

    //Esse elemento é responsável por retornar quantos itens tem na itemView.. 5,10,200??
    override fun getItemCount(): Int {
        //RecyleView - 10° Passo - Passamos o tamanho da lista para resolver o getItemCount
        return bookList.size
    }

    //Esse elemento é responsável por atribuir os valores dos atributos para o layout
    // É como se o ViewHolder fossem quem detem a view
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        //RecyleView - 10° Passo - Vamos chamar o método que será criado na viewHolder:
        //Eu passo a position para o holder, que irá utilizar o método criado binda para atribuir os valores na posição passada.
        holder.bind(bookList[position])
    }

    //RecyleView - 13° Passo - Vamos criar uma função que preencha a bookList:
    fun updateBooks(list: List<BookEntity>){
        bookList = list
        notifyDataSetChanged() // Força uma atualização quando a lista mudar
    }

    fun attachListener(listener: BookListener){
        bookListener = listener
    }
}