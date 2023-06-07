package com.example.streettracker

import android.net.Uri
import androidx.annotation.DrawableRes

object Repo{
    var data = mutableListOf<Datum>(
        Datum(
            1,
            "Jalan berlubang di Suhat",
            "Jalan disuhat bolong ya tolong bisa sampai kepada pemerintah...",
            "Roy JS",
            "Jalan Suhat",
            "Malang",
            "Kecamatan Konoha",
            "Kelurahan Naruto",
            "20/12/2050",
            R.drawable.lubang,
            0,
            null,
            false
        ),
        Datum(
            2,
            "Jalan ihhh wibu lubang banget",
            "Jalan di ihhh wibu itu banyak bawangnya makanya harus dibenerin",
            "Kiritod",
            "Jalan ihhh wibu",
            "Otaku",
            "Kecamatan Wibu",
            "Kelurahan NiponPaint",
            "20/12/2069",
            R.drawable.lubang1,
            0,
            null,
            false
        ),
        Datum(
            3,
            "Jalan berlubang di oppa plastik lubang nya banyak bet",
            "Jalannya dari dulu pake plastik sih makanya sekarang banyak lubangnya",
            "Jongkok dilantai",
            "Jalan Plastik",
            "Plastik",
            "Kecamatan Oppa",
            "Kelurahan Hanguk",
            "20/12/2051",
            R.drawable.lubang2,
            0,
            null,
            false
        ),
        Datum(
            4,
            "Jalan berlubang di Suhat",
            "Jalan disuhat bolong ya tolong bisa sampai kepada pemerintah...",
            "Diana",
            "Jalan Suhat",
            "Shinobi",
            "Kecamatan Konoha",
            "Kelurahan Naruto",
            "20/12/2050",
            R.drawable.lubang,
            0,
            null,
            false
        ),
        Datum(
            5,
            "Jalan berlubang di Suhat",
            "Jalan disuhat bolong ya tolong bisa sampai kepada pemerintah...",
            "Diana",
            "Jalan Sulfat",
            "Malang",
            "Kecamatan Konoha",
            "Kelurahan Naruto",
            "20/12/2050",
            R.drawable.lubang,
            0,
            null,
            false
        ),
    )
    fun getPost(id: Int) = data.firstOrNull { it.id == id }
    fun disApprove(tempId :Int){
        for(datum in data){
            if(datum.id == tempId){
                datum.govApproval = false
            }
        }
    }
    fun deleteContent(tempId: Int){
        val tempPost = requireNotNull(getPost(tempId))
        data.remove(tempPost)
    }
    fun insertContent(tempDatum: Datum){
        data.add(tempDatum)
    }
    fun updateReporter(oldUsername : String, newUsername: String){
        for(datum in data){
            if(datum.reporter == oldUsername){
                datum.reporter = newUsername
            }
        }
    }

}
data class Datum(
    val id: Int,
    val title: String,
    val description: String,
    var reporter: String,
    val address: String,
    val kota: String,
    val kecamatan: String,
    val kelurahan : String,
    val datePublished :String,
    @DrawableRes val imageResource: Int,
    var FalseReportCount : Int,
    var lubangGambar : Uri?,
    var govApproval : Boolean
)