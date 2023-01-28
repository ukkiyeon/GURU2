package com.example.guru2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.guru2.CommunityWriting.Companion.adapter
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

class Walk (addr:String) {

    var addr: String

    init{
        this.addr = addr
    }

    var lats = ArrayList<String>()
    var lngs = ArrayList<String>()
    var name = ArrayList<String>()
    val storesURL = "https://api.odcloud.kr/api/15037992/v1/uddi:b4ea380d-b6b1-4afb-be00-9c7305cc62e3?page=1&perPage=10&serviceKey=data-portal-test-key"

    private operator fun get(apiUrl: String): String {
        var responseBody: String = ""
        try {
            val url = URL(apiUrl)
            val `in` = url.openStream()
            responseBody = readBody(`in`)

        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return responseBody
    }

    private fun readBody(body: InputStream): String {
        val streamReader = InputStreamReader(body)

        try {
            BufferedReader(streamReader).use({ lineReader ->
                val responseBody = StringBuilder()

                var line: String? = lineReader.readLine()
                while ( line != null) {
                    responseBody.append(line)
                    line = lineReader.readLine()
                }
                return responseBody.toString()
            })
        } catch (e: IOException) {
            throw RuntimeException("API 응답을 읽는데 실패했습니다.", e)
        }

    }

    private fun parseData(responseBody: String) {
        var storeName: String
        var remain_stat: String
        var lat: String
        var lng: String
        var jsonObject = JSONObject()
        try {
            jsonObject = JSONObject(responseBody)
            val jsonArray = jsonObject.getJSONArray("stores")

            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                storeName = item.getString("name")
                remain_stat = item.getString("remain_stat")
                lat = item.getString("lat")
                lng = item.getString("lng")
                name.add(storeName)
                lats.add(lat)
                lngs.add(lng)
                adapter.addItem(storeName, remain_stat)

            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}