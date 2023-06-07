package com.example.myanimeapp.ui.shared

import androidx.lifecycle.ViewModel
import com.example.myanimeapp.models.one_anime.AnimeData
import com.example.myanimeapp.models.persistent.PersistentEntityMyAnime
import com.example.myanimeapp.models.search_fragment.SearchFragmentDataForAnimes

class SharedViewModel: ViewModel() {
    var animeListSearch = ArrayList<SearchFragmentDataForAnimes>()
    var airingAnimes = ArrayList<SearchFragmentDataForAnimes>()
    var stateButtonAiringAnimes: Boolean = false
    var animeListMyAnimes = ArrayList<PersistentEntityMyAnime>()
    var _query: String = ""

    fun removeAnimeFromFavorites(animeId: Int){
        val size = animeListMyAnimes.size
        for(i in 0 until size){
            if(animeListMyAnimes[i].id == animeId) {
                animeListMyAnimes.removeAt(i)
                break
            }
        }
    }
}