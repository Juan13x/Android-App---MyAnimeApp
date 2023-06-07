package com.example.myanimeapp.persistent

import com.example.myanimeapp.AppPersistentDatabase
import com.example.myanimeapp.models.one_anime.*
import com.example.myanimeapp.models.persistent.PersistentEntityMyAnime

class LocalRepository {
    suspend fun deleteAll(){
        val animeDao = AppPersistentDatabase.database.MyAnimeDao()
        animeDao.deleteAll()
    }

    suspend fun getAnimesId(): ArrayList<Int>{
        val animeDao = AppPersistentDatabase.database.MyAnimeDao()
        val animesId = animeDao.getAnimesID()
        return animesId as ArrayList<Int>
    }

    suspend fun getAllFavoriteAnimes(): ArrayList<PersistentEntityMyAnime>{
        val animeDao = AppPersistentDatabase.database.MyAnimeDao()
        return animeDao.getAllFavoriteAnimes() as ArrayList<PersistentEntityMyAnime>
    }
    
    suspend fun isAnimeSaved(id: Int): Boolean{
        val animeDao = AppPersistentDatabase.database.MyAnimeDao()
        return animeDao.isAnimeSaved(id)
    }
    
    suspend fun saveAnimes(animes: ArrayList<PersistentEntityMyAnime>){
        val animeDao = AppPersistentDatabase.database.MyAnimeDao()
        animes.forEach { anime ->
            animeDao.insertNewAnime(anime)
        }
    }

    suspend fun saveOneAnime(anime: PersistentEntityMyAnime){
        val animeDao = AppPersistentDatabase.database.MyAnimeDao()
        animeDao.insertNewAnime(anime)
    }

    private fun alternativeTitleFromRemoteToLocal(remoteAlternativeTitle: AlternativeTitles_AnimeData): ArrayList<String>{
        val localAlternativeTitle: ArrayList<String> = arrayListOf()
        localAlternativeTitle.add(remoteAlternativeTitle.en)
        localAlternativeTitle.add(remoteAlternativeTitle.ja)
        localAlternativeTitle.addAll(remoteAlternativeTitle.synonyms)
        return localAlternativeTitle
    }

    private fun genresFromRemoteToLocal(remoteGenre: List<Genre_AnimeData>): ArrayList<String>{
        val localGenre = arrayListOf<String>()
        remoteGenre.forEach { data ->
            localGenre.add(data.name)
        }
        return localGenre
    }

    private fun studioFromRemoteToLocal(remoteStudio: List<Studio_AnimeData>): ArrayList<String>{
        val localStudio = arrayListOf<String>()
        remoteStudio.forEach { data ->
            localStudio.add(data.name)
        }
        return localStudio
    }

    fun animeRemoteDataToLocalData(remoteAnime: AnimeData): PersistentEntityMyAnime {
        return PersistentEntityMyAnime(
            id = remoteAnime.id,
            alternativeTitlesAnimeData = alternativeTitleFromRemoteToLocal(remoteAnime.alternativeTitlesAnimeData),
            genreAnimeData = genresFromRemoteToLocal(remoteAnime.genreAnimeData),
            mainPictureAnimeData = remoteAnime.mainPictureAnimeData.medium,
            mean = remoteAnime.mean,
            numEpisodes = remoteAnime.numEpisodes,
            startSeasonAnimeData = remoteAnime.startSeasonAnimeData.season,
            status = remoteAnime.status,
            studioAnimeData = studioFromRemoteToLocal(remoteAnime.studioAnimeData),
            year = remoteAnime.startSeasonAnimeData.year,
            synopsis = remoteAnime.synopsis,
            title = remoteAnime.title
        )
    }

    suspend fun deleteAnime(anime: PersistentEntityMyAnime) {
        val animeDao = AppPersistentDatabase.database.MyAnimeDao()
        animeDao.deleteSavedAnime(anime)
    }
}