package com.markdev.allmybooks.ui


import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.markdev.allmybooks.R
import com.markdev.allmybooks.databinding.FragmentDetailBinding
import com.markdev.allmybooks.helper.BookConstants
import com.markdev.allmybooks.viewmodel.DetailViewModel

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!


    private val viewModel: DetailViewModel by viewModels()

    private var bookId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, b: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        setListeners()
        setObservers()

        bookId = arguments?.getInt(BookConstants.KEY.BOOK_ID)
            ?: 0 // elvis operator - se vier nulo recebe 0
        viewModel.getBookById(bookId)


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setObservers() {
        viewModel.books.observe(viewLifecycleOwner) {
            binding.textviewDetailTitle.text = it.title
            binding.textviewDetailGenreValue.text = it.genre
            binding.textviewDetailAuthorValue.text = it.author
            binding.checkboxDetailFavorite.isChecked = it.favorite

            setGenreBackground(it.genre)
        }

        viewModel.bookRemoval.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.msg_removed_successfully),
                    Toast.LENGTH_SHORT
                ).show()

                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun setGenreBackground(genre: String) {
        when (genre) {
            "Terror" -> {
                binding.textviewDetailGenreValue.setBackgroundResource(R.drawable.rounded_label_red)
            }

            "Fantasia" -> {
                binding.textviewDetailGenreValue.setBackgroundResource(R.drawable.rounded_label_fantasy)
            }

            else -> {
                binding.textviewDetailGenreValue.setBackgroundResource(R.drawable.rounded_label_teal)
            }
        }
    }

    private fun setListeners() {
        binding.imageviewBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack() // Utilizado para retornar para activity anterior
        }

        binding.buttonDetailRemove.setOnClickListener {handleRemove()}

        binding.checkboxDetailFavorite.setOnClickListener{handleFavorite()}
    }

    private fun handleRemove(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(getString(R.string.dialog_message_delete_item))
            .setPositiveButton(
                getString(R.string.dialog_positive_button_yes)
            ) { dialog, which ->

                viewModel.deleteBook(bookId)

            }
            .setNegativeButton(getString(R.string.dialog_negative_button_no)) { dialog, witch ->

                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun handleFavorite(){
        viewModel.favorite(bookId)
    }
}