package com.example.myanimeapp.remote_access.remote_repositories

import com.example.myanimeapp.models.User
import com.example.myanimeapp.remote_access.RemoteResult
import com.example.myanimeapp.remote_access.e_StatusResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseRepository: RemoteRepository {
    private val userDB = "users"
    private val auth = Firebase.auth
    private val db = Firebase.firestore

    override suspend fun isThereCurrentUser(): Boolean {
        return auth.currentUser != null
    }

    override suspend fun getCurrentUserId(): String? {
        return if(isThereCurrentUser())
            auth.currentUser?.email
        else
            null
    }

    override suspend fun getAnimesFromUser(email: String): RemoteResult<MutableList<Int>>{
        val resultFirestore = getUserFireStore(email)
        return if(resultFirestore.status == e_StatusResult.success){
            val animes =  resultFirestore.data!!.animes.toMutableList()
            RemoteResult.Success(animes)
        }else{
            val exception = resultFirestore.errorException!!
            RemoteResult.Error(
                exception = exception,
                errorMessage = exception.message
            )
        }
    }

    suspend fun getUserFireStore(email: String) : RemoteResult<User>{
        return try {
            val result = db.collection(userDB).document(email).get().await()
            if(result.exists()){
                val user = result.toObject<User>()!!
                RemoteResult.Success(user)
            }else{
                val exception = Exception("The Document doesn't exist")
                RemoteResult.Error(
                    exception = exception,
                    errorMessage = exception.message
                )
            }

        }catch (exception: Exception){
            RemoteResult.Error(
                exception = exception,
                errorMessage = exception.message
            )
        }
    }

    override suspend fun loginUser(email: String, password: String): RemoteResult<User> {
        return try{
            val resultAuth = auth.signInWithEmailAndPassword(email, password).await()
            val resultFirestore = getUserFireStore(email)
            if(resultFirestore.status == e_StatusResult.success){
                val user = User(uid = resultAuth.user!!.uid, email = email)
                user.animes =  resultFirestore.data!!.animes.toMutableList()
                RemoteResult.Success(user)
            }else{
                val exception = resultFirestore.errorException!!
                RemoteResult.Error(
                    exception = exception,
                    errorMessage = exception.message
                )
            }

        }catch(exception: Exception){
            RemoteResult.Error(
                exception = exception,
                errorMessage = exception.message
            )
        }
    }

    private suspend fun createUserFireStore(user: User) : RemoteResult<Boolean>{
        return try {
            val resultFireStore = db.collection(userDB).document(user.email!!).set(user).await()
            RemoteResult.Success(true)
        } catch (exception: Exception) {
            RemoteResult.Error(
                exception = exception,
                errorMessage = exception.message
            )
        }
    }

    override suspend fun signUpUser(email: String, password: String): RemoteResult<User> {
        try {
            val resultAuth = auth.createUserWithEmailAndPassword(email, password).await()
            val user = User(uid = resultAuth.user!!.uid, email = email)
            val resultFireStore = createUserFireStore(user)
            return if (resultFireStore.status == e_StatusResult.success) {
                RemoteResult.Success(user)
            } else {
                RemoteResult.Error(
                    exception = resultFireStore.errorException,
                    errorMessage = resultFireStore.errorMessage
                )
            }
        }catch (exception: Exception) {
            return RemoteResult.Error(
                exception = exception,
                errorMessage = exception.message
            )
        }
    }

    override suspend fun updateAnimeList(Array: Array<Int>): RemoteResult<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun logout(){
        auth.signOut()
    }
}