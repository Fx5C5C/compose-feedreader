@file:Suppress("DEPRECATION")

package de.critequal.mobile.composefeedreader.facade

import de.critequal.mobile.composefeedreader.dto.RSS
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiFacade {
    @GET
    fun getChannel(@Url url: String) : Call<RSS>

    companion object {

        var BASE_URL = "https://google.com"

        fun create() : ApiFacade {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiFacade::class.java)

        }
    }
}