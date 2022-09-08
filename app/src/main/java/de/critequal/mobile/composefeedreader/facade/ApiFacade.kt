package de.critequal.mobile.composefeedreader.facade

import de.critequal.mobile.composefeedreader.dto.RSS
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET

interface ApiFacade {
    //@GET("HL-live.xml")
    @GET("heise-Rubrik-IT.rdf")
    fun getChannel() : Call<RSS>

    companion object {

        var BASE_URL = "https://www.hl-live.de/aktuell/rss/"
        var BASE_URL2 = "https://www.heise.de/rss/"

        fun create() : ApiFacade {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .baseUrl(BASE_URL2)
                .build()
            return retrofit.create(ApiFacade::class.java)

        }
    }
}