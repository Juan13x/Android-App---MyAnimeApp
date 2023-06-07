package com.example.myanimeapp.ui.my_animes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myanimeapp.R
import com.example.myanimeapp.databinding.CardAnimesBinding
import com.example.myanimeapp.models.one_anime.AnimeData
import com.example.myanimeapp.models.persistent.PersistentEntityMyAnime
import com.example.myanimeapp.ui.e_Interfaces
import com.squareup.picasso.Picasso


class MyAnimesAdapter(
    private val myAnimes: ArrayList<PersistentEntityMyAnime>,
    private val navigator: NavController
): RecyclerView.Adapter<MyAnimesAdapter.MyAnimesViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAnimesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_animes, parent, false)
        return MyAnimesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyAnimesViewHolder, position: Int) {
        val animeSelected = myAnimes[position]
        holder.bind(animeSelected, navigator, position)
    }

    override fun getItemCount() : Int = myAnimes.size

    fun appendItems(newArray: ArrayList<PersistentEntityMyAnime>){
        myAnimes.clear()
        myAnimes.addAll(newArray)
        notifyDataSetChanged()
    }

    class MyAnimesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = CardAnimesBinding.bind(itemView)

        fun bind(
            currentAnime: PersistentEntityMyAnime,
            navigator: NavController,
            animePos: Int
        ){
            with(binding){
                cardAnimesDetailsButton.setOnClickListener{
                    navigator.navigate(MyAnimesFragmentDirections.actionNavigationMyAnimesToDetailsFragment(e_Interfaces.MY_ANIMES, animePos))
                }
                cardAnimesActionFloatingActionButton.setOnClickListener{
                }

                cardAnimesTitleTextView.text = currentAnime.title

                currentAnime.numEpisodes.let {
                    if(it > 0) cardAnimesEpisodesDescriptionTextView.text = currentAnime.numEpisodes.toString()
                    else cardAnimesEpisodesDescriptionTextView.setText(R.string.cardViews_unknownEpisodes)
                }
                cardAnimesRatingDescriptionTextView.text = currentAnime.mean.toString()
                cardAnimesStatusDescriptionTextView.text = currentAnime.status.replace("_", " ")
                cardAnimesPremieredDescriptionTextView.text = currentAnime.startSeasonAnimeData
                cardAnimesYearDescriptionTextView.text = currentAnime.year.toString()


                Picasso.get().load(currentAnime.mainPictureAnimeData)
                    .into(cardAnimesImageView)
            }
        }
    }
}