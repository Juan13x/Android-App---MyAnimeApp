package com.example.myanimeapp.persistent

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myanimeapp.models.one_anime.AnimeData
import com.example.myanimeapp.models.persistent.PersistentEntityMyAnime

@Dao
interface MyAnimeDao {
   @Insert
   suspend fun insertNewAnime(anime: PersistentEntityMyAnime)

   @Delete
   suspend fun deleteSavedAnime(anime: PersistentEntityMyAnime)

   @Query("SELECT * FROM my_animes")
   suspend fun getAllFavoriteAnimes(): MutableList<PersistentEntityMyAnime>

   @Query("SELECT EXISTS(SELECT 1 FROM my_animes WHERE id = :id)")
   suspend fun isAnimeSaved(id: Int): Boolean

   @Query("DELETE FROM my_animes")
   suspend fun deleteAll()

   @Query("SELECT id FROM my_animes")
   suspend fun getAnimesID(): MutableList<Int>
}