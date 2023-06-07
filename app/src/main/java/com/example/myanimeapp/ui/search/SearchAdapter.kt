package com.example.myanimeapp.ui.search

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myanimeapp.R
import com.example.myanimeapp.databinding.CardAnimesBinding
import com.example.myanimeapp.models.search_fragment.SearchFragmentDataForAnimes
import com.example.myanimeapp.ui.e_Colors
import com.example.myanimeapp.ui.e_Interfaces
import com.squareup.picasso.Picasso

class SearchAdapter(
    private val searchList: ArrayList<SearchFragmentDataForAnimes>,
    private val colors: Map<e_Colors, Int>,
    private val navigator: NavController
):RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_animes, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int = searchList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val anime = searchList[position]
        holder.bind(anime, colors, navigator, position)
    }

    fun appendItems(animesList: ArrayList<SearchFragmentDataForAnimes>){
        searchList.clear()
        searchList.addAll(animesList)
        notifyDataSetChanged()
    }

    fun getAnimeList(): ArrayList<SearchFragmentDataForAnimes>{
        return ArrayList(searchList)
    }
    class SearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val binding = CardAnimesBinding.bind(itemView)

        fun bind(animeStruct: SearchFragmentDataForAnimes,
                 colors: Map<e_Colors, Int>,
                 navigator: NavController,
                 animePos: Int
        ){
            with(binding){
                val anime = animeStruct.anime

                cardAnimesActionFloatingActionButton.setOnClickListener {  }
                cardAnimesDetailsButton.setOnClickListener {
                    navigator.navigate(SearchFragmentDirections.actionNavigationSearchToDetailsFragment(e_Interfaces.SEARCH, animePos))
                }

                //cardAnimesActionFloatingActionButton.setRippleColor(ColorStateList.valueOf(colors[e_Colors.PRESSED_FLOATING_BOTTON]!!))

                if(animeStruct.stateFavorites) {
                    cardAnimesActionFloatingActionButton.setImageResource(R.drawable.baseline_delete_24)
                    cardAnimesActionFloatingActionButton.backgroundTintList = ColorStateList.valueOf(colors[e_Colors.DELETE]!!)
                }
                else {
                    cardAnimesActionFloatingActionButton.setImageResource(R.drawable.baseline_add_24)
                    cardAnimesActionFloatingActionButton.backgroundTintList = ColorStateList.valueOf(colors[e_Colors.ADD]!!)
                }
                cardAnimesTitleTextView.text = anime.title

                anime.numEpisodes.let{
                    if(it > 0) cardAnimesEpisodesDescriptionTextView.text = it.toString()
                    else cardAnimesEpisodesDescriptionTextView.setText(R.string.cardViews_unknownEpisodes)
                }
                cardAnimesRatingDescriptionTextView.text = anime.mean.toString()
                cardAnimesStatusDescriptionTextView.text = anime.status.replace("_", " ")
                cardAnimesPremieredDescriptionTextView.text = anime.startSeasonAnimeData
                cardAnimesYearDescriptionTextView.text = anime.year.toString()

                Picasso.get().load(anime.mainPictureAnimeData)
                    .into(cardAnimesImageView)
            }
        }
    }
}