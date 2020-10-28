package dev.weiqi.zoo

import android.app.Application
import android.content.Context
import dev.weiqi.zoo.di.koinModuleList
import org.koin.core.context.startKoin

class App : Application() {

    companion object {
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            modules(koinModuleList)
        }
    }
}
