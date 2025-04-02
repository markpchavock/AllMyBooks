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
import com.markdev.allmybooks.databinding.FragmentFavoriteBinding
import com.markdev.allmybooks.helper.BookConstants
import com.markdev.allmybooks.ui.adapter.BookAdapter
import com.markdev.allmybooks.ui.listener.BookListener
import com.markdev.allmybooks.viewmodel.FavoriteViewModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null

    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModels() // declaração da viewModel delegando a inicalização para lib androidX
    val adapter: BookAdapter = BookAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        binding.recycleiewBooksFavorite.layoutManager = LinearLayoutManager(context)

        binding.recycleiewBooksFavorite.adapter = adapter

        attachListener()
        setObservers()

        return binding.root
    }

    override fun onResume() {
        // sempre que atualizar a tela, a fragmente vai buscar a nova atualização da lista.
        super.onResume()
        viewModel.getFavoriteBooks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
                viewModel.getFavoriteBooks()  // Vai listar novamente a lista atualizada
            }
        })

    }

    private fun setObservers() {
        viewModel.books.observe(viewLifecycleOwner) {
            if(it.isEmpty()){ // Logica para alternar entre lista vazia e imagem nos favoritos
                binding.recycleiewBooksFavorite.visibility = View.GONE
                binding.imageviewNoBooks.visibility = View.VISIBLE
                binding.textviewNoBooks.visibility = View.VISIBLE
            } else {
                binding.recycleiewBooksFavorite.visibility = View.VISIBLE
                binding.imageviewNoBooks.visibility = View.GONE
                binding.textviewNoBooks.visibility = View.GONE
                adapter.updateBooks(it) //it é a lista
            }

        }
    }
}