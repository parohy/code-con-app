package sk.parohy.codecon

import android.app.Application
import androidx.preference.PreferenceManager
import sk.parohy.codecon.api.Api

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Api.startup(PreferenceManager.getDefaultSharedPreferences(this))
    }
}