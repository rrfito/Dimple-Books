package com.example.dimplebooks.model

data class RecycleViewBook (
    val nameBook : String ,
    val author : String ,
    val rating : Double ,
    val image : Int
)
data class RecycleViewBookHistory (
    val nameBook : String ,
    val author : String ,
    val publisher : String ,
    val genre : String ,
    val page : Int ,
    val image : Int
)