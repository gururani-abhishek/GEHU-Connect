package org.example.chatbox

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

private const val Tag : String = "document"
class MiddleActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_middle)
        val name = findViewById<TextView>(R.id.name)
        val regNo = findViewById<TextView>(R.id.registrationNumber)
        val email = findViewById<TextView>(R.id.email)
        val mobileNo = findViewById<TextView>(R.id.phoneNumber)
        val imageView = findViewById<ImageView>(R.id.profilePic)
        val db = Firebase.firestore
        val auth = Firebase.auth
        val currentUserId = auth.currentUser!!.uid
        db.collection("profile").document(currentUserId).get()
            .addOnSuccessListener{ document ->
                if(document !=  null)  {
                    name.text = document.getString("name")
                    regNo.text = document.getString("regNo")
                    mobileNo.text = document.getString("mobileNo")
                    email.text = document.getString("email")
                    Glide.with(this)
                        .load(document.getString("imageUrl"))
                        .circleCrop()
                        .into(imageView)
                } else {
                    Log.d(Tag, "no such document!")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(Tag, "get failed with ", exception)
            }




        val circular = findViewById<ImageView>(R.id.circular)
        circular.setOnClickListener(this)
        val profile = findViewById<ImageView>(R.id.profile)
        profile.setOnClickListener(this)
        val academics = findViewById<ImageView>(R.id.academics)
        academics.setOnClickListener(this)
        val chatBox = findViewById<ImageView>(R.id.chatBox)
        chatBox.setOnClickListener(this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.logoutButton -> {
                Firebase.auth.signOut()
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(view : View) {
        when(view.id) {
            R.id.profile -> {
                intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }
//
//            R.id.academics -> {
//                intent = Intent(this, Academics::class.java)
//                startActivity(intent)
//            }

            R.id.circular -> {
                intent = Intent(this, CircularActivity::class.java)
                startActivity(intent)
            }

            R.id.chatBox -> {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}