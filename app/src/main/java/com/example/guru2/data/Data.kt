package com.example.guru2.data

data class Data(
    val count: Int, //개수
    val old_addr: String, //구주소
    val doro_name: String, //도로명
    val doro_2: Int,  //세부번지 (도로명)
    val new_old: String, //신형, 구형
    val year: String, //연번
    val location: String, //위치
    val dong: String //해당 동
)