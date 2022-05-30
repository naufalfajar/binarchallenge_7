package id.nphew.binar.challenge6.app

import android.app.Application
import com.facebook.stetho.Stetho

class App: Application(){
    override fun onCreate() {
        super.onCreate()
        Stetho.initialize(Stetho.newInitializerBuilder(this)
            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
            .build()
        )
    }
}