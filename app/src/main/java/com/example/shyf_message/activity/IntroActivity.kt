package com.example.shyf_message.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.shyf_message.R
import com.example.shyf_message.databinding.ActivityIntroBinding
import com.example.shyf_message.utils.Constants
import com.example.shyf_message.utils.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding
    private lateinit var database: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var prefManager: PreferenceManager
    private lateinit var storageRef: StorageReference
    lateinit var rootView: ConstraintLayout // تعريف المتغير هنا
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        prefManager = PreferenceManager(applicationContext)
        rootView = binding.rootConst
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() {
        val lbtn = binding.btnLogin
        lbtn.setOnClickListener {
            val bottomSheet: BottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
            bottomSheet.setContentView(R.layout.login_dialog)
            bottomSheet.show()
            val btn = bottomSheet.findViewById<AppCompatButton>(R.id.btnLogin)
            btn?.setOnClickListener {
                val email: String =
                    bottomSheet.findViewById<TextInputLayout>(R.id.emailSignIn)?.editText!!.text.toString().trim()
                val password: String =
                    bottomSheet.findViewById<TextInputLayout>(R.id.passwordSignIn)?.editText!!.text.toString().trim()
                if (isValidSignInDetails(email, password)) {
                    login(email, password)
                }
                bottomSheet.dismiss()
            }
        }
    }

    private fun login(email: String, password: String) {
        auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                prefManager.putBoolean(Constants.KEY_IS_SIGNED_IN, true)
                prefManager.putString(Constants.KEY_USER_ID, it.result.user?.uid!!)
                val intent: Intent = Intent(this@IntroActivity, MainActivity::class.java)
                Log.d("YZ",it.result.user!!.uid)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            } else {
                showCenteredMessage("Unable to Sign In")
            }
        }
    }

    private fun isValidSignInDetails(email: String, password: String): Boolean {
        return if (email.trim().isEmpty()) {
            showCenteredMessage("Enter Email")
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showCenteredMessage("Enter Valid Email")
            false
        } else if (password.trim().isEmpty()) {
            showCenteredMessage("Enter Password")
            false
        } else if (password.length > 8) {
            showCenteredMessage("Password can't >8!")
            false
        } else {
            true
        }
    }

    private fun showCenteredMessage(message: String) {
        val snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view
        // تعديل حجم الـ snackbarView ليكون حسب حجم الكلمة الموجودة فيه
        snackbarView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
        snackbarView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        // تعديل تخطيط الرسالة لتوسيطها في وسط الشاشة
        val params = snackbarView.layoutParams as CoordinatorLayout.LayoutParams
        params.gravity = Gravity.CENTER
        snackbarView.layoutParams = params
        // تعيين مدة ظهور الـ Snackbar إلى ثانيتين
//        snackbar.duration = 2000 // بالمللي ثانية
        snackbar.show()
    }

}