package com.example.guru2

// 컨텐츠 데이터 모델
data class ContentDTO(var explain : String? = null, // 컨텐츠 설명
                      var imageUrl : String? = null, // 이미지 주소 관리
                      var uid : String? = null, // 어느 유저가 올렸는지 uid
                      var userId : String? = null, // 유저의 이미지 관리?
                      var timestamp : Long? = null, // 컨텐츠 업로드 시각
                      var favoriteCount : Int = 0, // 좋아요 개수 관리
                      var favorites : Map<String,Boolean> = HashMap()) { // 중복 좋아요 방지?
    data class Comment(var uid : String? = null, // 댓글 관리하는 Comment라는 클래스. uid 관리
                       val userId: String? = null, // 이메일 관리
                       var comment: String? = null, // 댓글 관리
                       var timestamp: Long? = null) // 댓글 업로드 관리
}