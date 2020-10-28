package dev.weiqi.zoo.ui.main

import android.os.Bundle
import dev.weiqi.zoo.R
import dev.weiqi.zoo.ui.base.BaseActivity
import dev.weiqi.zoo.ui.areainfo.AreaInfoFragment


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar(title = getString(R.string.zoo))

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AreaInfoFragment.newInstance())
            .commit()
    }
}