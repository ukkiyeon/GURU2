package com.example.guru2

import android.content.ClipData
import android.content.ClipData.Item
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import java.lang.Runnable

class TipEcoNews : AppCompatActivity() {
    lateinit var backBtn: ImageButton // 뒤로가기 버튼

    // 크롤링
    lateinit var tip_news01_title: TextView // 첫번째 뉴스 제목
    lateinit var tip_news02_title: TextView // 첫번째 뉴스 제목
    lateinit var tip_news03_title: TextView // 첫번째 뉴스 제목
    lateinit var tip_news04_title: TextView // 첫번째 뉴스 제목
    lateinit var tip_news05_title: TextView // 첫번째 뉴스 제목
    lateinit var tip_news06_title: TextView // 첫번째 뉴스 제목
    private val job = Job() // job 전역변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tip_eco_news)

        // 위젯 변수 연결
        backBtn = findViewById(R.id.back) // 뒤로가기 버튼
        tip_news01_title = findViewById(R.id.tip_news01_title) // 크롤링 제목
        tip_news02_title = findViewById(R.id.tip_news02_title) // 크롤링 제목
        tip_news03_title = findViewById(R.id.tip_news03_title) // 크롤링 제목
        tip_news04_title = findViewById(R.id.tip_news04_title) // 크롤링 제목
        tip_news05_title = findViewById(R.id.tip_news05_title) // 크롤링 제목
        tip_news06_title = findViewById(R.id.tip_news06_title) // 크롤링 제목


        // 크롤링 코루틴 (job 생성)
        CoroutineScope(Dispatchers.IO + job).launch {

            val returnList = async { crawlEcoNews() } // crawlEcoNews()의 반환값을 넘김
            val titleList = returnList.await() // 뉴스 제목 리스트 반환

            // 잠시 메인 스레드로 전환해서 제목 출력
            withContext(Dispatchers.Main) {
                tip_news01_title.text = titleList.get(0)
                tip_news02_title.text = titleList.get(1)
                tip_news03_title.text = titleList.get(2)
                tip_news04_title.text = titleList.get(3)
                tip_news04_title.text = titleList.get(4)


            }
        }

        // 뒤로 가기
        backBtn.setOnClickListener {
            var intent = Intent(this, Tip::class.java)
            startActivity(intent)
        }
    }

    // job 전역변수에 대해 cancel
    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    // 크롤링 부분, 코루틴에서 사용하는 함수 (suspend 사용)
    private suspend fun crawlEcoNews(): ArrayList<String> {
        var titleList = ArrayList<String>(); // 리턴할 뉴스 제목 리스트

        try{
            val url = "https://www.yna.co.kr/society/environment"
            val doc = Jsoup.connect(url).get() // Jsoup을 통해 접속하고, url에 있는 html 코드를 다 가져오기

            /* 방법 2
            val base_url = "https:"
            val today = doc.select("ul.list div.item-box01") // html 코드 중에서도 필요한 부분만

            today.forEach { item ->
                val item_title = item.select("strong.tit-news").text() // 일단 제목만 ..

                items.add(Item(item_title)) // arrayList 리스트에 추가

            }
            */

            val classTag = doc.getElementsByClass("news-con") // <div class="news-con">

            for (i in 0..4) {
                titleList.add(classTag[i].getElementsByTag("strong").text()) // 제목 리스트에 추가
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return titleList // 제목 리스트 반환
    }
}