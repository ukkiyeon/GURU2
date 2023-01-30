package com.example.guru2

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.guru2.databinding.ActivityMapsBinding
import java.security.MessageDigest
import org.json.JSONArray

class MapsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapsBinding
//    val itemList = arrayListOf<WalkData>()
//    val walkAdapter = RecycleAdapter(itemList)

    lateinit var walks_num: TextView
    lateinit var walks_addr: TextView

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.walks.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        binding.walks.adapter = walkAdapter

        val walk = findViewById<View>(R.id.walk)
        walks_num = walk.findViewById(R.id.walks_num)
        walks_addr = walk.findViewById(R.id.walks_addr)

        val jsonString = assets.open("json_trash.json").reader().readText();
//        Log.d("JSON STR", jsonString)

        val jsonArray = JSONArray(jsonString)

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            Log.d("jsonObject", jsonObject.toString())

            var str1 = jsonObject.getString("연번")
            walks_num.text = str1

            var str2 = jsonObject.getString("도로명")
            var str3 = jsonObject.getString("세부번지(도로명)")
            var str4 = jsonObject.getString("해당 동")
            walks_addr.text = str4 + "동 " + str2 + " " + str3
        }

        try {
            val information =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
            val signatures = information.signingInfo.apkContentsSigners
            for (signature in signatures) {
                val md = MessageDigest.getInstance("SHA").apply {
                    update(signature.toByteArray())
                }
                val HASH_CODE = String(Base64.encode(md.digest(), 0))

                Log.d("해시", "HASH_CODE -> $HASH_CODE")
            }
        } catch (e: Exception) {
            Log.d("예외", "Exception -> $e")
        }

    }
}