package com.aayush.telewise.ui.fragment.movies.details

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import coil.load
import com.aayush.telewise.R
import com.aayush.telewise.api.model.TmdbFailure
import com.aayush.telewise.databinding.FragmentMovieDetailsBinding
import com.aayush.telewise.model.UiModel
import com.aayush.telewise.util.android.AppPreferences
import com.aayush.telewise.util.android.DotItemDecoration
import com.aayush.telewise.util.android.adapter.CreditsAdapter
import com.aayush.telewise.util.android.adapter.GenreAdapter
import com.aayush.telewise.util.android.launchBrowser
import com.aayush.telewise.util.android.toastLong
import com.aayush.telewise.util.android.viewBinding
import com.aayush.telewise.util.common.FB_PREFIX
import com.aayush.telewise.util.common.IMAGE_URL_ORIGINAL
import com.aayush.telewise.util.common.IMAGE_URL_W500
import com.aayush.telewise.util.common.IMDB_MOVIE_PREFIX
import com.aayush.telewise.util.common.INSTA_PREFIX
import com.aayush.telewise.util.common.RETRY_COUNT
import com.aayush.telewise.util.common.TMDB_MOVIE_PREFIX
import com.aayush.telewise.util.common.TOOLBAR_COLLAPSE_HEIGHT_PX
import com.aayush.telewise.util.common.TWITTER_PREFIX
import com.aayush.telewise.util.common.VEILED_ITEM_COUNT
import com.aayush.telewise.util.common.onError
import com.aayush.telewise.util.common.onSuccess
import com.aayush.telewise.util.common.toFormattedDate
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerializationException
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
    private val binding by viewBinding(FragmentMovieDetailsBinding::bind)

    private val viewModel by viewModels<MovieDetailsViewModel>()

    private val movie by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments().getParcelable<UiModel.MovieCollectionModel>("movie")!!
    }

    @Inject lateinit var preferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.fade)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutToolbar.collapsingToolbar.setupWithNavController(
            binding.layoutToolbar.toolbar,
            findNavController()
        )

        // Instead of displaying movie title all the time, display only when toolbar is collapsed
        binding.layoutToolbar.appBarLayout.addOnOffsetChangedListener(
            object : AppBarLayout.OnOffsetChangedListener {
                private var shown = true

                override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                    if (verticalOffset + appBarLayout.totalScrollRange <= TOOLBAR_COLLAPSE_HEIGHT_PX) {
                        if (!shown) {
                            shown = true
                            binding.layoutToolbar.toolbar.title = movie.title
                        }
                    } else {
                        if (shown) {
                            shown = false
                            binding.layoutToolbar.toolbar.title = " "
                        }
                    }
                }
            }
        )

        val saveData = runBlocking {
            preferences.saveData.first()
        }
        val prefix = if (saveData) IMAGE_URL_W500 else IMAGE_URL_ORIGINAL
        binding.layoutToolbar.imgToolbar.load(prefix + movie.posterPath) {
            placeholder(R.drawable.ic_movies_64)
            fallback(R.drawable.ic_movies_64)
            error(R.drawable.ic_broken_image_64)
        }

        observeChanges()
    }

    private fun observeChanges() {
        lifecycleScope.launch {
            viewModel.getMovieDetails(movie.id)
                .retryWhen { cause, attempt ->
                    cause !is TmdbFailure && cause !is SerializationException && attempt < RETRY_COUNT
                }
                .collect { result ->
                    binding.veilDetails.veil()
                    result
                        .onSuccess {
                            binding.veilDetails.unVeil()
                            viewModel.saveMovie(value)
                            displayDetails(value)
                        }
                        .onError {
                            binding.veilDetails.unVeil()
                            toastLong(
                                requireContext(),
                                error.localizedMessage ?: error.toString()
                            )
                            findNavController().navigateUp()
                        }
                }

            viewModel.getMovieExternalIds(movie.id)
                .retryWhen { cause, attempt ->
                    cause !is TmdbFailure && cause !is SerializationException && attempt < RETRY_COUNT
                }
                .collect { result ->
                    binding.veilExternalIds.veil()
                    result
                        .onSuccess {
                            binding.veilExternalIds.unVeil()
                            viewModel.saveExternalIds(value)
                            displayExternalIds(value)
                        }
                        .onError {
                            binding.veilExternalIds.unVeil()
                            toastLong(
                                requireContext(),
                                error.localizedMessage ?: error.toString()
                            )
                            findNavController().navigateUp()
                        }
                }

            viewModel.getMovieCredits(movie.id)
                .retryWhen { cause, attempt ->
                    cause !is TmdbFailure && cause !is SerializationException && attempt < RETRY_COUNT
                }
                .collect { result ->
                    binding.veilCast.veil()
                    binding.veilCrew.veil()
                    result
                        .onSuccess {
                            binding.veilCast.unVeil()
                            binding.veilCrew.unVeil()
                            displayCredits(value)
                        }
                        .onError {
                            binding.veilCast.unVeil()
                            binding.veilCrew.unVeil()
                            toastLong(
                                requireContext(),
                                error.localizedMessage ?: error.toString()
                            )
                            findNavController().navigateUp()
                        }
                }
        }
    }

    private fun displayDetails(movie: UiModel.MovieModel) = with(binding) {
        textMovieOverview.text = movie.overview
        textMovieReleaseDate.text = getString(R.string.release_date, movie.releaseDate.toFormattedDate())
        textMovieRating.text = movie.rating.toString()
        imgMovieExplicit.isVisible = movie.adult

        val genreAdapter = GenreAdapter(movie.genres)
        recyclerGenres.isVisible = movie.genres.isNotEmpty()
        recyclerGenres.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        recyclerGenres.addItemDecoration(DotItemDecoration(requireContext()))
        recyclerGenres.adapter = genreAdapter
    }

    private fun displayExternalIds(ids: Map<String, String?>) = with(binding) {
        btnTmdb.setOnClickListener {
            val uri = (TMDB_MOVIE_PREFIX + ids.getOrElse("tmdb") { movie.id.toString() }).toUri()
            launchBrowser(requireContext(), uri)

        }

        btnImdb.isVisible = ids["imdb"] != null
        btnImdb.setOnClickListener {
            val uri = (IMDB_MOVIE_PREFIX + ids["imdb"]).toUri()
            launchBrowser(requireContext(), uri)
        }

        btnFb.isVisible = ids["facebook"] != null
        btnFb.setOnClickListener {
            val uri = (FB_PREFIX + ids["facebook"]).toUri()
            launchBrowser(requireContext(), uri)
        }

        btnInsta.isVisible = ids["instagram"] != null
        btnInsta.setOnClickListener {
            val uri = (INSTA_PREFIX + ids["instagram"]).toUri()
            launchBrowser(requireContext(), uri)
        }

        btnTwitter.isVisible = ids["twitter"] != null
        btnTwitter.setOnClickListener {
            val uri = (TWITTER_PREFIX + ids["twitter"]).toUri()
            launchBrowser(requireContext(), uri)
        }
    }

    private fun displayCredits(credits: Map<String, List<UiModel.Person>>) = with(binding) {
        val cast = credits["cast"]
        val crew = credits["crew"]
        if (cast == null || crew == null) {
            toastLong(
                requireContext(),
                "Something went wrong while fetching movie credits. Load the page again."
            )
            findNavController().navigateUp()
        }

        val saveData = runBlocking {
            preferences.saveData.first()
        }
        textLabelCast.isVisible = cast!!.isNotEmpty()
        veilCast.isVisible = cast.isNotEmpty()
        veilCast.addVeiledItems(VEILED_ITEM_COUNT)
        val castAdapter = CreditsAdapter(cast, saveData)
        veilCast.getRecyclerView().setHasFixedSize(true)
        veilCast.setAdapter(
            castAdapter,
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        )

        textLabelCrew.isVisible = crew!!.isNotEmpty()
        veilCrew.isVisible = crew.isNotEmpty()
        veilCrew.addVeiledItems(VEILED_ITEM_COUNT)
        val crewAdapter = CreditsAdapter(crew, saveData)
        veilCrew.getRecyclerView().setHasFixedSize(true)
        veilCrew.setAdapter(
            crewAdapter,
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        )
    }
}
