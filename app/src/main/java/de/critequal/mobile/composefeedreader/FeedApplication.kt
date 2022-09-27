package de.critequal.mobile.composefeedreader

import android.app.Application
import androidx.room.Room
import de.critequal.mobile.composefeedreader.persistence.FeedDatabase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class FeedApplication: Application() {

    val persistenceModule = module {
        single {
            Room.databaseBuilder(
                applicationContext,
                FeedDatabase::class.java, "feedreader-database"
            ).build()
        }
        viewModel { FeedViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(persistenceModule)
        }
    }
}