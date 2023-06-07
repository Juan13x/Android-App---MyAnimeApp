package com.example.myanimeapp.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myanimeapp.R
import com.example.myanimeapp.databinding.FragmentDetailsBinding
import com.example.myanimeapp.models.errors.e_Error
import com.example.myanimeapp.models.persistent.PersistentEntityMyAnime
import com.example.myanimeapp.ui.e_Interfaces
import com.example.myanimeapp.ui.shared.SharedViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {

    private lateinit var model: DetailsViewModel
    private lateinit var sharedModel: SharedViewModel
    private lateinit var binding: FragmentDetailsBinding

    private val argsNav: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        model = ViewModelProvider(this)[DetailsViewModel::class.java]
        sharedModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        val animePos = argsNav.animePos
        val anime: PersistentEntityMyAnime =
            if(argsNav.stateInterface == e_Interfaces.SEARCH){
                sharedModel.animeListSearch[animePos].anime
            }
            else { //e_Interfaces.MY_ANIMES
                sharedModel.animeListMyAnimes[animePos]
            }

        with(binding){
            with(anime) {
                detailsBackButton.setOnClickListener {
                    findNavController().popBackStack()
                }

                model.operationFinishedLiveData.observe(this@DetailsFragment){
                        isFavorite ->
                    if(isFavorite != null){
                        if(isFavorite){
                            setDeleteFunctionality(anime, animePos)
                        }else{
                            setSaveFunctionality(anime, animePos)
                        }
                    }
                }

                model.errorLiveData.observe(this@DetailsFragment) { errorData ->
                    if(errorData != null) {
                        when (errorData.errorEvent) {
                            e_Error.GenericInt -> {
                                Snackbar.make(detailsCoordinatorLayout, errorData.errorMessageInt!!, Toast.LENGTH_SHORT)
                                    .setBackgroundTint(resources.getColor(R.color.Red))
                                    .setTextColor(resources.getColor(R.color.White))
                                    .show()
                            }
                            else -> {
                                Snackbar.make(detailsCoordinatorLayout, errorData.errorMessageStr!!, Toast.LENGTH_SHORT)
                                    .setBackgroundTint(resources.getColor(R.color.Red))
                                    .setTextColor(resources.getColor(R.color.White))
                                    .show()
                            }
                        }
                    }
                }

                if(argsNav.stateInterface == e_Interfaces.SEARCH){
                    if(sharedModel.animeListSearch[animePos].stateFavorites) {
                        setDeleteFunctionality(anime, animePos)
                    }
                    else {
                        setSaveFunctionality(anime, animePos)
                    }
                } else {
                    setDeleteFunctionality(anime, animePos)
                }


                detailsTitleTextView.text = title
                detailsNumEpisodesDescriptionTextView.text = numEpisodes.toString()
                detailsYearDescriptionTextView.text = year.toString()
                detailsRatingDescriptionTextView.text = mean.toString()
                detailsSynopsisDescriptionTextView.text = synopsis
                detailsPremieredDescriptionTextView.text = startSeasonAnimeData
                detailsStatusDescriptionTextView.text = status

                bindAlternativeTitle(binding, alternativeTitlesAnimeData)
                bindStudios(binding, studioAnimeData)
                bindGenre(binding, genreAnimeData)

                Picasso.get().load(mainPictureAnimeData).into(detailsMainImageView)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private fun setDeleteFunctionality(anime: PersistentEntityMyAnime, animePos: Int) {
        binding.detailsFavoriteImageView.setImageDrawable(resources.getDrawable(R.drawable.saved_24))
        binding.detailsFavoriteImageView.setOnClickListener{
            model.deleteAnime(
                anime,
                animePos,
                argsNav.stateInterface,
                sharedModel
            )
        }
    }

    private fun setSaveFunctionality(anime: PersistentEntityMyAnime, animePos: Int) {
        binding.detailsFavoriteImageView.setImageDrawable(resources.getDrawable(R.drawable.unsaved_24))
        binding.detailsFavoriteImageView.setOnClickListener {
            model.saveAnime(
                anime,
                animePos,
                argsNav.stateInterface,
                sharedModel
            )
        }
    }

    private fun bindAlternativeTitle(binding: FragmentDetailsBinding, alternativeTitlesAnimeData: ArrayList<String>) {
        var altTitleStr: String = ""
        for(i in 0 until (alternativeTitlesAnimeData.size-1)) {
            val altTitle = alternativeTitlesAnimeData[i]
            altTitleStr += "$altTitle, "
        }
        altTitleStr += alternativeTitlesAnimeData.last()
        binding.detailsAlternativeTitlesDescriptionTextView.text = altTitleStr
    }

    private fun bindStudios(binding: FragmentDetailsBinding, studiosAnimeData: ArrayList<String>) {
        var studiosStr: String = ""
        for(i in 0 until (studiosAnimeData.size-1)) {
            val studio = studiosAnimeData[i]
            studiosStr += "$studio, "
        }
        studiosStr += studiosAnimeData.last()
        binding.detailsStudiosDescriptionTextView.text = studiosStr
    }

    private fun bindGenre(binding: FragmentDetailsBinding, genresAnimeData: ArrayList<String>) {
        var genresStr: String = ""
        for(i in 0 until (genresAnimeData.size-1)) {
            val genres = genresAnimeData[i]
            genresStr += "$genres, "
        }
        genresStr += genresAnimeData.last()
        binding.detailsGenreDescriptionTextView.text = genresStr
    }

    override fun onDestroy() {
        super.onDestroy()
        model.resetData()
    }
}