package de.critequal.mobile.composefeedreader

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.critequal.mobile.composefeedreader.dto.RSS
import de.critequal.mobile.composefeedreader.facade.ApiFacade
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedViewModel : ViewModel() {

    private val feeds: MutableLiveData<List<RSS>> by lazy {
        MutableLiveData<List<RSS>>().also {
            fetchFeeds()
        }
    }
    var error: MutableLiveData<String> = MutableLiveData("")

    fun getFeeds(): LiveData<List<RSS>> {
        return feeds
    }

    private fun fetchFeeds() {
        ApiFacade.create().getChannel().enqueue(object: Callback<RSS> {
            override fun onResponse(call: Call<RSS>, response: Response<RSS>) {
                response.body()?.let { channel ->
                    val list = ArrayList<RSS>()
                    list.add(channel)
                    feeds.postValue(list)
                }
            }

            override fun onFailure(call: Call<RSS>, t: Throwable) {
                error.postValue(t.message)
            }

        })
    }

}