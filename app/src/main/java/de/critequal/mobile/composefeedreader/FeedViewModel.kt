package de.critequal.mobile.composefeedreader

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.critequal.mobile.composefeedreader.dto.RSS
import de.critequal.mobile.composefeedreader.facade.ApiFacade
import de.critequal.mobile.composefeedreader.persistence.FeedDatabase
import de.critequal.mobile.composefeedreader.persistence.FeedURL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedViewModel(private val database: FeedDatabase) : ViewModel() {

    var feeds = mutableStateListOf<RSS>()
    var urls = mutableStateListOf<FeedURL>()

    init {
        viewModelScope.launch {
            fetchFeeds()
            updateFeedURLs()
        }
    }



    var error: MutableLiveData<String> = MutableLiveData("")

    suspend fun removeURL(url: FeedURL) {
        withContext(Dispatchers.IO) {
            database.feedURLDao().delete(url)
            urls.remove(url)
        }
    }

    suspend fun addFeedURL(url: String) {
        withContext(Dispatchers.IO) {
            database.feedURLDao()
                .insertAll(FeedURL(url = url))
            updateFeedURLs()
        }
    }

    suspend fun updateFeedURLs() {
        withContext(Dispatchers.IO) {
            val urlDAOs = database.feedURLDao().getAll()
            urls.removeAll(urlDAOs)
            urls.addAll(urlDAOs)
        }
    }

    suspend fun fetchFeeds() {
        withContext(Dispatchers.IO) {
            database.feedURLDao().getAll().forEach { feedURL ->
                ApiFacade.create().getChannel(feedURL.url).enqueue(object : Callback<RSS> {
                    override fun onResponse(call: Call<RSS>, response: Response<RSS>) {
                        if (response.code() == 200) {
                            response.body()?.let { channel ->
                                feeds.remove(channel)
                                feeds.add(channel)
                            }
                        }
                    }

                    override fun onFailure(call: Call<RSS>, t: Throwable) {
                        error.postValue(t.message)
                    }
                })
            }
        }
    }

}