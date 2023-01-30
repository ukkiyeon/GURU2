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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.post_detail_view_fragment.view.*
import kotlinx.android.synthetic.main.post_item.view.*

class PostDetailViewFragment : Fragment() {
    var firestore : FirebaseFirestore? = null
    var uid : String? = null // uid 전역변수

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.post_detail_view_fragment, container, false)
        firestore = FirebaseFirestore.getInstance() // 파이어스토어 객체 초기화
        uid = FirebaseAuth.getInstance().currentUser?.uid // uid 받아오기

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
            var view = LayoutInflater.from(p0.context).inflate(R.layout.post_item,p0,false) // post_item으로 뷰 생성
            return CustomViewHolder(view)
        }

        // post_item 뷰로 CustomViewHolder 생성
        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)

        // 메소드 2. 리사이클러뷰의 아이템 총 개수 반환
        override fun getItemCount(): Int {
            return contentDTOs.size
        }

        // 메소드 3. 뷰홀더가 재활용될 때 실행됨(onCreateViewHolder에서 만든 view와 실제 데이터를 연결)
        override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
            var viewholder = (p0 as CustomViewHolder).itemView // CustomViewHolder로 캐스팅

            //UserId
            viewholder.detailviewitem_profile_textview.text = contentDTOs!![p1].userId

            //Image
            Glide.with(p0.itemView.context).load(contentDTOs!![p1].imageUrl).into(viewholder.detailviewitem_imageview_content)

            // Explain
            viewholder.detailviewitem_explain_textview.text = contentDTOs!![p1].explain

            // likes
            viewholder.detailviewitem_favoritecounter_textview.text = "Likes " + contentDTOs!![p1].favoriteCount

            //profile image
            // Glide.with(p0.itemView.context).load(contentDTOs!![p1].imageUrl).into(viewholder.detailviewitem_profile_image)

            // 좋아요 버튼 클릭
            viewholder.detailviewitem_favorite_imageview.setOnClickListener {
                favoriteEvent(p1) // 포지션 값 전달
            }
            if(contentDTOs!![p1].favorites.containsKey(uid)) { // 채워진
                viewholder.detailviewitem_favorite_imageview.setImageResource(R.drawable.like_filled)
            } else { // 빈
                viewholder.detailviewitem_favorite_imageview.setImageResource(R.drawable.like_empty)
            }
        }



        // 좋아요 이벤트 메소드 (버튼 눌리면 발생)
        fun favoriteEvent(position : Int) { // 포지션 파라미터

            var tsDoc = firestore?.collection("images")?.document(contentUidList[position]) // 선택한 이미지 UID 받아오기

            // 데이터 입력을 위해 트랜잭션 불러오기
            firestore?.runTransaction { transaction ->
                var contentDTO = transaction.get(tsDoc!!).toObject(ContentDTO::class.java) // 트랜잭션 데이터를 contentDTO로 캐스팅

                if(contentDTO!!.favorites.containsKey(uid)) { // 이미 좋아요 되어있을 때 -> -1
                    contentDTO?.favoriteCount = contentDTO?.favoriteCount!! - 1
                    contentDTO?.favorites?.remove(uid)
                } else { // 좋아요 되어 있지 않을 때 -> +1
                    contentDTO?.favoriteCount = contentDTO?.favoriteCount!! + 1
                    contentDTO?.favorites?.set(uid!!, true)
                }

                // 트랜잭션을 다시 서버로 돌림
                transaction.set(tsDoc,contentDTO)
            }
        }

    }
}

