package dev.weiqi.zoo.ui.splash

import android.content.Intent
import android.os.Bundle
import dev.weiqi.zoo.R
import dev.weiqi.zoo.ui.base.BaseActivity
import dev.weiqi.zoo.ui.main.MainActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}