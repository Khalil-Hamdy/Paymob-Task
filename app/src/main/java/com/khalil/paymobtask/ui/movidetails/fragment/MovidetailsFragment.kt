package com.khalil.paymobtask.ui.movidetails.fragment

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.khalil.paymobtask.R
import com.khalil.paymobtask.databinding.FragmentMovidetailsBinding
import com.khalil.paymobtask.ui.home.viewmodel.MovieViewModel
import com.khalil.paymobtask.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovidetailsFragment : Fragment() {
    private var _binding: FragmentMovidetailsBinding? = null
    private val binding get() = _binding!!

    private val movieViewModel: MovieViewModel by viewModels()
    private val args: MovidetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovidetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = args.movie
        binding.textTitle.text = movie.title
        binding.textRate.text = String.format("%.1f", movie.vote_average)
        binding.textDate.text = movie.release_date
        binding.textOverView.text = movie.overview

        val heartColor = if (movie.isFavorite) R.color.red else R.color.gray
        binding.iconHeart.setColorFilter(
            ContextCompat.getColor(requireContext(), heartColor),
            PorterDuff.Mode.SRC_IN
        )

        Glide.with(requireContext())
            .load(Constants.IMG_URL + movie.poster_path)
            .into(binding.imageMovie)

        binding.iconHeart.setOnClickListener {
            movieViewModel.toggleFavorite(movie)
            collectFavoriteState()
        }

        binding.iconBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun collectFavoriteState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieViewModel.isFavorite.collect { isFavorite ->
                    val heartColor = if (isFavorite) R.color.red else R.color.gray
                    binding.iconHeart.setColorFilter(
                        ContextCompat.getColor(requireContext(), heartColor),
                        PorterDuff.Mode.SRC_IN
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}