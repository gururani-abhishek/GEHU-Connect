package org.example.chatbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.FileReader

private const val Tag : String = "document"
class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val desc = findViewById<TextView>(R.id.desc)
        val db = Firebase.firestore
        val auth = Firebase.auth
        val currentUserId = auth.currentUser!!.uid
        db.collection("profile").document(currentUserId).get()
            .addOnSuccessListener { document ->
                if(document != null) {
                    desc.text = document.getString("desc")
                }
            }
            .addOnFailureListener{ exception ->
                Log.d(Tag, "get failed with ", exception)
            }
    }
}