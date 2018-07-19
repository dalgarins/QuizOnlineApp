package co.anbora.onlinequizapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import co.anbora.onlinequizapp.databinding.ActivityMainBinding
import co.anbora.onlinequizapp.domain.model.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.sign_up_layout.view.*

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    lateinit var database: FirebaseDatabase
    lateinit var users: DatabaseReference

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

    }

    private fun showSignUpDialog() {

        var alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialog.setTitle("Sign Up")
        alertDialog.setMessage("Please full fill info")

        var inflater: LayoutInflater = this.layoutInflater
        var signUpLayout: View = inflater.inflate(R.layout.sign_up_layout, null)

        alertDialog.setView(signUpLayout)
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp)

        alertDialog.setNegativeButton("NO", {
            dialogInterface, i ->  dialogInterface.dismiss()
        })

        alertDialog.setPositiveButton("YES", DialogInterface.OnClickListener {
            dialogInterface, i ->

            val user: User = User(mainBinding.infoLog.edtNewUserName.text.toString(),
                    mainBinding.infoLog.edtNewPassword.text.toString(),
                    mainBinding.infoLog.edtNewEmail.text.toString())

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
        })

        alertDialog.show()
    }
}
