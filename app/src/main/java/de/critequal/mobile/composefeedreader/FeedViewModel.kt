package de.critequal.mobile.composefeedreader

import androidx.lifecycle.LiveData
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
import kotlin.collections.ArrayList

class FeedViewModel(private val database: FeedDatabase) : ViewModel() {

    private val cachedFeeds = ArrayList<RSS>()
    private val feeds: MutableLiveData<List<RSS>> by lazy {
        MutableLiveData<List<RSS>>().also {
            viewModelScope.launch {
                fetchFeeds()
            }
        }
    }
    private val urls: MutableLiveData<List<FeedURL>> by lazy {
        MutableLiveData<List<FeedURL>>().also {
            viewModelScope.launch {
                getFeedURLs()
            }
        }
    }

    var error: MutableLiveData<String> = MutableLiveData("")

    fun getFeeds(): LiveData<List<RSS>> {
        return feeds
    }

    fun getURLs(): LiveData<List<FeedURL>> {
        return urls
    }

    suspend fun addFeedURL(url: String) {
        withContext(Dispatchers.IO) {
            database.feedURLDao()
                .insertAll(FeedURL(url = url))
        }
    }

    private suspend fun getFeedURLs() {
        withContext(Dispatchers.IO) {
            urls.postValue(database.feedURLDao().getAll())
        }
    }

    private suspend fun fetchFeeds() {
        withContext(Dispatchers.IO) {
            database.feedURLDao().getAll().forEach { feedURL ->
                ApiFacade.create().getChannel(feedURL.url).enqueue(object : Callback<RSS> {
                    override fun onResponse(call: Call<RSS>, response: Response<RSS>) {
                        if (response.code() == 200) {
                            response.body()?.let { channel ->
                                cachedFeeds.add(channel)
                            }
                        }
                    }

                    override fun onFailure(call: Call<RSS>, t: Throwable) {
                        error.postValue(t.message)
                    }
                })
                feeds.postValue(cachedFeeds)
            }
        }
    }

}