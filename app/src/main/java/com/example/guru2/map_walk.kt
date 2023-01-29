package com.example.guru2

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.io.InputStreamReader
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class Place(
    val name: String,
    val latLng: LatLng,
    val address: String,
    val rating: Float
) : ClusterItem {
    override fun getPosition(): LatLng =
        latLng

    override fun getTitle(): String =
        name

    override fun getSnippet(): String =
        address
}

class map_walk(private val context: Context) {

    // GSON object responsible for converting from JSON to a Place object
    private val gson = Gson()

    // InputStream representing places.json
    private val inputStream: InputStream
        get() = context.resources.openRawResource(R.raw.map_walk)

    /**
     * Reads the list of place JSON objects in the file places.json
     * and returns a list of Place objects
     */
//    fun read(): List<Place> {
//        val itemType = object : TypeToken<List<PlaceResponse>>() {}.type
//        val reader = InputStreamReader(inputStream)
//        return gson.fromJson<List<PlaceResponse>>(reader, itemType).map {
//            it.toPlace()
//        }
//    }
}

