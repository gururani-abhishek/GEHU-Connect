package org.example.chatbox.daos

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.*
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.example.chatbox.models.Post
import org.example.chatbox.models.User

class PostDao {
    private val db = Firebase.firestore
    val postCollections = db.collection("posts")
    private val auth = Firebase.auth
    fun addPost(text: String) {
        val currentUserId = auth.currentUser!!.uid
        //here the requirement was to provide a non-null current user, so we either need to use safe call(?.)
        //bangbang(!!), we were sure that only a legit signed in user(non-null current user) should be allowed
        //post, so we're kinda sure that the currentUser is not null, if in case it's null : let the application
        //crash and burn.

        GlobalScope.launch {
            val userDao = UserDao()
            val user = userDao.getUserById(currentUserId).await().toObject(User::class.java)!!
            val currentTime = System.currentTimeMillis()
            val post = Post(text, user, currentTime)
            postCollections.document().set(post)
        }



    }
}