package com.example.myanimeapp.models.search_fragment

import com.example.myanimeapp.models.one_anime.AnimeData
import com.example.myanimeapp.models.persistent.PersistentEntityMyAnime

data class SearchFragmentDataForAnimes(
    val anime: PersistentEntityMyAnime,
    var stateFavorites: Boolean = false
)
