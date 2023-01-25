package com.example.guru2

import android.content.ClipData
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
    private val job = Job() // job 전역변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tip_eco_news)

        // 위젯 변수 연결
        backBtn = findViewById(R.id.back) // 뒤로가기 버튼
        tip_news01_title = findViewById(R.id.tip_news01_title) // 크롤링 제목

        // 크롤링 부분 (코루틴) - job이 만들어짐.
        CoroutineScope(Dispatchers.IO + job).launch {
            val newstitle = async { crawlEcoNews() } // crawlEcoNews()의 반환값을 넘김
            val text = "${newstitle.await()}" // 뉴스 제목 출력

            // 잠시 메인 스레드로 전환해서 제목 출력
            withContext(Dispatchers.Main) {
                tip_news01_title.text = text
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

    // 코루틴에서 사용하는 함수 (suspend) 크롤링 부분 !!
    private suspend fun crawlEcoNews(): String {
        var econewstitle = String()

        try{
            val url = "https://www.yna.co.kr/society/environment"
            val doc = Jsoup.connect(url).get() // Jsoup을 통해 접속하고, url에 있는 html 코드를 다 가져오기

            //val title = doc.select("bnusNo").text() // 파싱
            //EcoNewsTitle = title

            val classTag = doc.getElementsByClass("news-con")
            val title = classTag[0].getElementsByTag("strong").text()
            econewstitle = title

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return econewstitle
    }

}