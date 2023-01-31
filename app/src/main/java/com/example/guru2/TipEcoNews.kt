package com.example.guru2

import android.content.ClipData
import android.content.ClipData.Item
import android.content.Intent
import android.media.Image
import android.net.Uri
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

    // 뉴스 제목
    lateinit var tip_news01_title: TextView
    lateinit var tip_news02_title: TextView
    lateinit var tip_news03_title: TextView
    lateinit var tip_news04_title: TextView
    lateinit var tip_news05_title: TextView
    lateinit var tip_news06_title: TextView
    lateinit var tip_news07_title: TextView
    lateinit var tip_news08_title: TextView
    lateinit var tip_news09_title: TextView
    lateinit var tip_news10_title: TextView

    // 뉴스 송고 시간
    lateinit var tip_news01_content: TextView
    lateinit var tip_news02_content: TextView
    lateinit var tip_news03_content: TextView
    lateinit var tip_news04_content: TextView
    lateinit var tip_news05_content: TextView
    lateinit var tip_news06_content: TextView
    lateinit var tip_news07_content: TextView
    lateinit var tip_news08_content: TextView
    lateinit var tip_news09_content: TextView
    lateinit var tip_news10_content: TextView

    // 링크 버튼
    lateinit var tip_news01_btn: Button
    lateinit var tip_news02_btn: Button
    lateinit var tip_news03_btn: Button
    lateinit var tip_news04_btn: Button
    lateinit var tip_news05_btn: Button
    lateinit var tip_news06_btn: Button
    lateinit var tip_news07_btn: Button
    lateinit var tip_news08_btn: Button
    lateinit var tip_news09_btn: Button
    lateinit var tip_news10_btn: Button

    // job 전역변수
    private val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tip_eco_news)

        // 위젯 변수 연결
        backBtn = findViewById(R.id.back) // 뒤로가기 버튼
        tip_news01_title = findViewById(R.id.tip_news01_title) // 제목
        tip_news02_title = findViewById(R.id.tip_news02_title)
        tip_news03_title = findViewById(R.id.tip_news03_title)
        tip_news04_title = findViewById(R.id.tip_news04_title)
        tip_news05_title = findViewById(R.id.tip_news05_title)
        tip_news06_title = findViewById(R.id.tip_news06_title)
        tip_news07_title = findViewById(R.id.tip_news07_title)
        tip_news08_title = findViewById(R.id.tip_news08_title)
        tip_news09_title = findViewById(R.id.tip_news09_title)
        tip_news10_title = findViewById(R.id.tip_news10_title)

        tip_news01_btn = findViewById(R.id.tip_news01_btn) // 링크 버튼
        tip_news02_btn = findViewById(R.id.tip_news02_btn)
        tip_news03_btn = findViewById(R.id.tip_news03_btn)
        tip_news04_btn = findViewById(R.id.tip_news04_btn)
        tip_news05_btn = findViewById(R.id.tip_news05_btn)
        tip_news06_btn = findViewById(R.id.tip_news06_btn)
        tip_news07_btn = findViewById(R.id.tip_news07_btn)
        tip_news08_btn = findViewById(R.id.tip_news08_btn)
        tip_news09_btn = findViewById(R.id.tip_news09_btn)
        tip_news10_btn = findViewById(R.id.tip_news10_btn)

        tip_news01_content = findViewById(R.id.tip_news01_content) // 송고 시간
        tip_news02_content = findViewById(R.id.tip_news02_content)
        tip_news03_content = findViewById(R.id.tip_news03_content)
        tip_news04_content = findViewById(R.id.tip_news04_content)
        tip_news05_content = findViewById(R.id.tip_news05_content)
        tip_news06_content = findViewById(R.id.tip_news06_content)
        tip_news07_content = findViewById(R.id.tip_news07_content)
        tip_news08_content = findViewById(R.id.tip_news08_content)
        tip_news09_content = findViewById(R.id.tip_news09_content)
        tip_news10_content = findViewById(R.id.tip_news10_content)


        /* 방법 1 크롤링 코루틴 (job 생성)
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
        }*/

        // 방법 2 크롤링 코루틴 (job 생성)
        CoroutineScope(Dispatchers.IO + job).launch {

            val returnList = async { crawlEcoNews() } // crawlEcoNews()의 반환값을 넘김 (items)
            val titleList = returnList.await() // 뉴스 리스트 반환

            // 잠시 메인 스레드로 전환해서 제목 출력
            withContext(Dispatchers.Main) {
                tip_news01_title.text = titleList[0].title
                tip_news02_title.text = titleList[1].title
                tip_news03_title.text = titleList[2].title
                tip_news04_title.text = titleList[3].title
                tip_news05_title.text = titleList[4].title
                tip_news06_title.text = titleList[5].title
                tip_news07_title.text = titleList[6].title
                tip_news08_title.text = titleList[7].title
                tip_news09_title.text = titleList[8].title
                tip_news10_title.text = titleList[9].title

                tip_news01_content.text = titleList[0].time
                tip_news02_content.text = titleList[1].time
                tip_news03_content.text = titleList[2].time
                tip_news04_content.text = titleList[3].time
                tip_news05_content.text = titleList[4].time
                tip_news06_content.text = titleList[5].time
                tip_news07_content.text = titleList[6].time
                tip_news08_content.text = titleList[7].time
                tip_news09_content.text = titleList[8].time
                tip_news10_content.text = titleList[9].time

                tip_news01_btn.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(titleList[0].link))
                    startActivity(intent)
                }
                tip_news02_btn.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(titleList[1].link))
                    startActivity(intent)
                }
                tip_news03_btn.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(titleList[2].link))
                    startActivity(intent)
                }
                tip_news04_btn.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(titleList[3].link))
                    startActivity(intent)
                }
                tip_news05_btn.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(titleList[4].link))
                    startActivity(intent)
                }
                tip_news06_btn.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(titleList[5].link))
                    startActivity(intent)
                }
                tip_news07_btn.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(titleList[6].link))
                    startActivity(intent)
                }
                tip_news08_btn.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(titleList[7].link))
                    startActivity(intent)
                }
                tip_news09_btn.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(titleList[8].link))
                    startActivity(intent)
                }
                tip_news10_btn.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(titleList[9].link))
                    startActivity(intent)
                }

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

    // 방법 2 크롤링 부분, 코루틴에서 사용하는 함수 (suspend 사용)
    private suspend fun crawlEcoNews(): ArrayList<Item> {
        var items : ArrayList<Item> = arrayListOf() // 리턴할 뉴스 제목 리스트

        try{
            val url = "https://www.yna.co.kr/society/environment"
            val doc = Jsoup.connect(url).get() // Jsoup을 통해 접속하고, url에 있는 html 코드를 다 가져오기
            val baseUrl = "https:"

            val today = doc.select("ul.list div.item-box01") // html 코드 중에서도 필요한 부분만

            today.forEach { item ->
                val itemTitle = item.select("strong.tit-news").text() // 제목
                val itemTime = item.select("span.txt-time").text() // 송고시간
                val itemLink = baseUrl + item.select("a").attr("href") // 링크

                items.add(Item(itemTitle, itemTime, itemLink)) // arrayList 리스트에 추가
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return items // 제목 리스트 반환
    }

    // Item 클래스
    data class Item(val title: String, val time: String, val link: String)


    /* 방법 1 크롤링 부분, 코루틴에서 사용하는 함수 (suspend 사용)
    private suspend fun crawlEcoNews(): ArrayList<String> {
        var titleList = ArrayList<String>(); // 리턴할 뉴스 제목 리스트

        try{
            val url = "https://www.yna.co.kr/society/environment"
            val doc = Jsoup.connect(url).get() // Jsoup을 통해 접속하고, url에 있는 html 코드를 다 가져오기


            val classTag = doc.getElementsByClass("news-con") // <div class="news-con">

            for (i in 0..4) {
                titleList.add(classTag[i].getElementsByTag("strong").text()) // 제목 리스트에 추가
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return titleList // 제목 리스트 반환
    }*/

}