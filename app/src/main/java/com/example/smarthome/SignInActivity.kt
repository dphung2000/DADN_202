package com.example.smarthome

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smarthome.databinding.ActivityAuthenticateBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {

    private lateinit var dbcontext: DatabaseReference
    private lateinit var binding: ActivityAuthenticateBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var intentSignUp: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val contxt = this
        binding = ActivityAuthenticateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user_email: EditText = findViewById(R.id.editTextTextEmailAddress)
        val user_password: EditText = findViewById(R.id.editTextTextPassword)
        val buttonSignIn: Button = findViewById(R.id.button_sign_in)
        val textViewSignUp: TextView = findViewById(R.id.signUpTextView)
        buttonSignIn.setOnClickListener {
            val email_str = user_email.text.toString()
            val password_str = user_password.text.toString()
            Log.d("SignIn", "onClick: called")
            Log.d("SignIn", "Email: ${email_str}")
            Log.d("SignIn", "Password: ${password_str}")
            if (email_str.isNullOrEmpty() || password_str.isNullOrEmpty()) {
                Toast.makeText(baseContext, "Authentication failed: Missing field",
                    Toast.LENGTH_SHORT).show()
            } else {
                Log.d("SignIn", "About to call SignIn()")
                signIn(user_email.text.toString(), user_password.text.toString())
            }
        }
        textViewSignUp.setOnClickListener {
            Log.d("SignUp", "onClick: called")
            intentSignUp = Intent(contxt, SignUpActivity::class.java)
            startActivityForResult(intentSignUp, 200)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("SignIn", "This is not supposed to show up")
        // Ki???m tra requestCode c?? tr??ng v???i REQUEST_CODE v???a d??ng
        if (requestCode == 200) {

            // resultCode ???????c set b???i DetailActivity
            // RESULT_OK ch??? ra r???ng k???t qu??? n??y ???? th??nh c??ng
            if (resultCode == RESULT_OK) {
                // Nh???n d??? li???u t??? Intent tr??? v???
                val NEmail = data?.getStringExtra("NewUserEmail")
                val NPassword = data?.getStringExtra("NewUserPassword")
                Log.d("SignIn", "Return newly created email: $NEmail")
                Log.d("SignIn", "Return newly created password: $NPassword")
                if (NEmail != null && NPassword != null){
                    signIn(NEmail, NPassword)
                }
            } else {
                // DetailActivity kh??ng th??nh c??ng, kh??ng c?? data tr??? v???.
            }
        }
    }
    private fun signIn(email: String, password: String){
        Log.d("SignIn", "About to call signInWithEmail" )
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignIn", "signInWithEmail:success")
                    // Get name of the user
                        val intent = Intent()
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SignIn", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed :(",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}