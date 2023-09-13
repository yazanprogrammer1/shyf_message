package com.example.shyf_message.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.shyf_message.databinding.ActivitySplashBinding
import com.example.shyf_message.utils.Constants
import com.example.shyf_message.utils.PreferenceManager

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(applicationContext)

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
}