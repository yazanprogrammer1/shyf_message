package com.example.shyf_message.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.shyf_message.databinding.ActivitySplashBinding
import com.example.shyf_message.utils.Constants
import com.example.shyf_message.utils.PreferenceManager
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var preferenceManager: PreferenceManager
    var uid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //..... code
        preferenceManager = PreferenceManager(applicationContext)


        getUserIdFb()
        Log.d("YZ", uid!!)
        Handler(Looper.getMainLooper()).postDelayed({
            var intent = Intent(this@SplashActivity,IntroActivity::class.java)
            if(preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
                intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
//            val pair1 = Pair<View, String>(binding?.splashLayout,binding?.splashLayout?.transitionName)
//            val pair2 = Pair<View, String>(binding?.splashLottieIcon,binding?.splashLottieIcon?.transitionName)
//            val pair3 = Pair<View, String>(binding?.splashTvTitle,binding?.splashTvTitle?.transitionName)
//            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@SplashActivity, pair1, pair2, pair3)
//            startActivity(intent,options.toBundle())
            startActivity(intent)
            finish()
        }, 2000)
    }
    private fun getUserIdFb(): String {
        val currentUser = FirebaseAuth.getInstance().uid
        if (currentUser != null) {
            // استخراج معرف المستخدم (UID)
            uid = currentUser
            // يمكنك استخدام مُعرف المستخدم (uid) هنا في نشاطك
            // على سبيل المثال، يمكنك تخزينه أو استخدامه كمعرف فريد للمستخدم
        } else {
            // المستخدم ليس مسجل الدخول، يمكنك تنفيذ الإجراءات اللازمة في هذا الحالة
            uid = "null"
        }
        return uid!!
    }
}