package com.markdev.allmybooks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.markdev.allmybooks.R
import com.markdev.allmybooks.databinding.FragmentHomeBinding
import com.markdev.allmybooks.helper.BookConstants
import com.markdev.allmybooks.ui.adapter.BookAdapter
import com.markdev.allmybooks.ui.listener.BookListener
import com.markdev.allmybooks.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    val viewModel: HomeViewModel by viewModels()

    //RecyleView - 8° Passo - Criar a adapter na fragment, declarar, instanciar e identificar:
    // 8.1 declarar e instanciar:
    val adapter: BookAdapter = BookAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        //RecyleView - 2° Passo - identificação do código e atribuição do layout(como a recView se comporta)
        //Ela irá se comportar como um linear Layout
        binding.recycleiewBooks.layoutManager = LinearLayoutManager(context)

        //8.2 identificar
        binding.recycleiewBooks.adapter = adapter

        attachListener()
        //RecyleView - 15° Passo - Chamar a função que busca todos os livros do repositório
        //viewModel.getAllBooks() - foi posteriomente colocada em OnResume devido ao motivo de:
        // sempre que atualizar a tela, a fragmente vai buscar a nova atualização da lista.

        //RecyleView - 16° Passo - Criar o código para observar a variável
        setObservers()

        return binding.root
    }

    override fun onResume() {
        // sempre que atualizar a tela, a fragmente vai buscar a nova atualização da lista.
        super.onResume()
        viewModel.getAllBooks()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setObservers() {
        viewModel.books.observe(viewLifecycleOwner) {
            adapter.updateBooks(it) //it é a lista
        }
    }

    private fun attachListener() {
        adapter.attachListener(object : BookListener {
            override fun onClick(id: Int) {

                val bundle = Bundle()
                bundle.putInt(BookConstants.KEY.BOOK_ID,id)
                findNavController().navigate(R.id.navigation_details,bundle)
            }

            override fun onFavoriteClick(id: Int) {
                viewModel.favorite(id) // Vai alterar o simbolo do favorito
                viewModel.getAllBooks()  // Vai listar novamente a lista atualizada
            }
        })

    }
}