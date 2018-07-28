package co.anbora.onlinequizapp

import android.app.AlertDialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import co.anbora.onlinequizapp.databinding.ActivityMainBinding
import co.anbora.onlinequizapp.databinding.SignUpLayoutBinding
import co.anbora.onlinequizapp.domain.model.User
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    private lateinit var database: FirebaseDatabase
    private lateinit var users: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        database = FirebaseDatabase.getInstance()
        users = database.getReference("Users")

        mainBinding.btnSignUp.setOnClickListener {
            showSignUpDialog()
        }

        mainBinding.btnSignIn.setOnClickListener {
            signIn(mainBinding.edtUserName.text.toString(),
                    mainBinding.edtPassword.text.toString())
        }
    }

    private fun signIn(userName: String, password: String) {
        users.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.child(userName).exists()) {
                    if (!userName.isEmpty()) {
                        val login: User? = dataSnapshot.child(userName).getValue(User::class.java)
                        login!!.let {
                            if (password == (it.password)) {
                                val homeIntent: Intent =  Intent(this@MainActivity, HomeActivity::class.java)
                                startActivity(homeIntent)
                                finish()
                            } else {
                                Toast.makeText(this@MainActivity, "Wrong password!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Please enter the user name!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "User does not exist!", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun showSignUpDialog() {

        var alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Sign Up")
        alertDialog.setMessage("Please full fill info")

        val signUpLayout: SignUpLayoutBinding = SignUpLayoutBinding.inflate(layoutInflater)!!

        alertDialog.setView(signUpLayout.root)
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp)

        alertDialog.setNegativeButton("NO") {
            dialogInterface, _ ->  dialogInterface.dismiss()
        }

        alertDialog.setPositiveButton("YES") {
            dialogInterface, _ ->

            val user: User = User(signUpLayout.edtNewUserName.text.toString(),
                    signUpLayout.edtNewPassword.text.toString(),
                    signUpLayout.edtNewEmail.text.toString())

            val userListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.child(user.userName).exists()) {
                        Toast.makeText(this@MainActivity, "User already exists!", Toast.LENGTH_SHORT).show()
                    } else {
                        users.child(user.userName).setValue(user)
                        Toast.makeText(this@MainActivity, "User registration success!", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {

                }
            }

            users.addListenerForSingleValueEvent(userListener)

            dialogInterface.dismiss()
        }

        alertDialog.show()
    }
}
