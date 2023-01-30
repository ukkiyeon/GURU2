package com.example.guru2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.post_detail_view_fragment.view.*
import kotlinx.android.synthetic.main.post_item.view.*

class PostDetailViewFragment : Fragment() {
    var firestore : FirebaseFirestore? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.post_detail_view_fragment, container, false)
        firestore = FirebaseFirestore.getInstance() // 파이어스토어 객체 초기화

        view.detailviewfragment_recyclerview.adapter = DetailViewRecyclerViewAdapter() // 아래 정의된 메소드
        view.detailviewfragment_recyclerview.layoutManager = LinearLayoutManager(activity) // 화면을 세로로 배치
        return view
    }

    //////////////////////////////////////////////////

    // 어댑터 (데이터를 아이템 레이아웃으로 만듦)
    inner class DetailViewRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        var contentDTOs : ArrayList<ContentDTO> = arrayListOf()
        var contentUidList : ArrayList<String> = arrayListOf()

        // 생성자 (DB 접근해서 데이터 받아오는 쿼리)
        init {
            firestore?.collection("images")?.orderBy("timestamp")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                contentDTOs.clear() // 초기화
                contentUidList.clear()

                for(snapshot in querySnapshot!!.documents) { // snapshot의 데이터를 for문으로 돌아가며 읽기
                    var item = snapshot.toObject(ContentDTO::class.java)

                    contentDTOs.add(item!!) // 배열에 추가
                    contentUidList.add(snapshot.id)
                }
                notifyDataSetChanged() // 값 새로고침
            }
        }

        // 메소드 1. 뷰 홀더 생성(레이아웃 생성, xml 파일을 inflate해서 ViewHolder를 생성)
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(p0.context).inflate(R.layout.post_item,p0,false)
            return CustomViewHolder(view)
        }

        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)

        // 리사이클러뷰의 아이템 총 개수 반환
        override fun getItemCount(): Int {
            return contentDTOs.size
        }

        // 뷰홀더가 재활용될 때 실행됨(onCreateViewHolder에서 만든 view와 실제 데이터를 연결)
        override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
            var viewholder = (p0 as CustomViewHolder).itemView // p0을 CustomViewHolder로 캐스팅

            //UserId
            viewholder.detailviewitem_profile_textview.text = contentDTOs!![p1].userId

            //Image
            Glide.with(p0.itemView.context).load(contentDTOs!![p1].imageUrl).into(viewholder.detailviewitem_imageview_content)

            // Explain
            viewholder.detailviewitem_explain_textview.text = contentDTOs!![p1].explain

            // likes
            viewholder.detailviewitem_favoritecounter_textview.text = "Likes " + contentDTOs!![p1].favoriteCount

            //profile image
            Glide.with(p0.itemView.context).load(contentDTOs!![p1].imageUrl).into(viewholder.detailviewitem_profile_image)
        }

    }
}
