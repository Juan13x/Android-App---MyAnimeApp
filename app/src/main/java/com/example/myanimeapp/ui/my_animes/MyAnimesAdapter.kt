package com.example.myanimeapp.ui.my_animes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myanimeapp.R
import com.example.myanimeapp.databinding.CardMyAnimesBinding
import com.example.myanimeapp.models.one_anime.AnimeData

class MyAnimesAdapter(
    private val myAnimes: ArrayList<AnimeData>,
    private val onItemClicked: (AnimeData) -> Unit,
): RecyclerView.Adapter<MyAnimesAdapter.MyAnimesViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAnimesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_my_animes, parent, false)
        return MyAnimesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyAnimesViewHolder, position: Int) {
        val animeSelected = myAnimes[position]
        holder.bind(animeSelected)
    }

    override fun getItemCount() : Int = myAnimes.size

    fun appendItems(newArray: ArrayList<AnimeData>){
        myAnimes.clear()
        myAnimes.addAll(newArray)
        notifyDataSetChanged()
    }

    class MyAnimesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = CardMyAnimesBinding.bind(itemView)

        fun bind(currentAnime: AnimeData){
            with(binding){
                cardMyAnimesTitleTextView.text = currentAnime.title
                cardMyAnimesEpisodesDescriptionTextView.text = currentAnime.numEpisodes.toString()
                cardMyAnimesRatingDescriptionTextView.text = currentAnime.mean.toString()
                cardMyAnimesStatusDescriptionTextView.text = currentAnime.status
                cardMyAnimesPremieredDescriptionTextView.text = currentAnime.startSeason.season

                cardMyAnimesDetailsButton.setOnClickListener{
                    TODO()
                }
                cardMyAnimesDeleteFloatingActionButton.setOnClickListener{
                    TODO()
                }

                cardMyAnimesDetailsButton.setOnClickListener {  }
                cardMyAnimesDeleteFloatingActionButton.setOnClickListener {  }
            }
        }
    }
}