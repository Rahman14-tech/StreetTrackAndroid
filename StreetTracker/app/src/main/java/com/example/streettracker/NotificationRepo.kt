package com.example.streettracker


object NotificationRepo{

}
data class Notification(
    val id: Int,
    val content : String,
    val foreignPostId : Int,
    )