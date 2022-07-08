package id.nphew.binar.challenge6.app

import android.app.Application
import com.facebook.stetho.Stetho
import id.nphew.binar.challenge5.service.ApiClient
import id.nphew.binar.challenge6.datastore.DataStoreManager
import id.nphew.binar.challenge6.repo.AccountRepo
import id.nphew.binar.challenge6.repo.MovieRepo
import id.nphew.binar.challenge6.viewmodel.LoginViewModel
import id.nphew.binar.challenge6.viewmodel.MovieViewModel
import id.nphew.binar.challenge6.viewmodel.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App: Application(){
    override fun onCreate() {
        super.onCreate()
        Stetho.initialize(Stetho.newInitializerBuilder(this)
            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
            .build()
        )
        initKoin()
    }

    private val appModule = module {
        single { DataStoreManager(androidContext()) }
        single { ApiClient.instance }
        single { MovieRepo(get()) }
        single { AccountRepo(androidContext()) }
    }

    private val viewModelModule = module {
        single { UserViewModel(get())}
        single { LoginViewModel(get())}
        single { MovieViewModel(get())}
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, viewModelModule))
        }
    }
}