package com.example.streettracker

import dalvik.annotation.TestTarget
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {

    //Testing Repo
    @Test
    fun insertRepo() {
        Repo.insertContent(Datum(6, "t", "t", "t", "t","t", "t","t","t",R.drawable.lubang1,0,null, false))
        assertEquals(6, Repo.data.size)
    }

    @Test
    fun deleteRepo() {
        Repo.deleteContent(4)
        assertEquals(5, Repo.data.size)
    }

    @Test
    fun updateRepo() {
        UserRepo.usernameCookies = "Roy JS"
        UserRepo.updateUsername("Roy Josan")
        assertEquals(true, UserRepo.isUsernameExist("Roy Josan"))
    }

    @Test
    fun getRepo() {
        val RJS = Repo.getPost(2)
        assertEquals(Datum(
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
        ), RJS)
    }
    @Test
    fun ApproveRepo() {
        Repo.Approve(4)
        assertEquals(true, Repo.getPost(4)?.govApproval)
    }

    @Test
    fun disAproveRepo() {
        Repo.disApprove(4)
        assertEquals(false, Repo.getPost(4)?.govApproval)
    }

    //Testing UserRepo
    @Test
    fun getUser() {
        val tesUser = UserRepo.getUserbyUsername("Pakdhe Owi")
        assertEquals(User(1,"Pakdhe Owi","owi@gmail.com","YTNKTS1!",true,null, true,"Solo",0), tesUser)
    }
    @Test
    fun findEmail() {
        val tesEmail = UserRepo.findbyEmail("jatim@gmail.com")
        assertEquals(User(2,"Malang","jatim@gmail.com","YTNKTS2",true,null, true,"Malang",0), tesEmail)
    }

    @Test
    fun EmailExist() {
        val testEmail = UserRepo.isEmailExist("owi@gmail.com")
        assertEquals(true, testEmail)
    }
    @Test
    fun UsernameExist() {
        val testUsername = UserRepo.isUsernameExist("Malang")
        assertEquals(true, testUsername)
    }

    @Test
    fun UpUsername() {
        UserRepo.usernameCookies = "Roy JS"
        UserRepo.updateUsername("Roy Josan")
        assertEquals(true, UserRepo.isUsernameExist("Roy Josan"))
    }

    @Test
    fun UpEmail() {
        UserRepo.usernameCookies = "Roy Josan"
        UserRepo.updateEmail("roysantoso@gmail.com")
        assertEquals(true, UserRepo.isEmailExist("roysantoso@gmail.com"))
    }

    @Test
    fun UpPassword() {
        UserRepo.usernameCookies = "Roy Josan"
        UserRepo.updatePassword("RJSGGBET")
        assertEquals("RJSGGBET", UserRepo.getUserbyUsername("Roy Josan")?.password)
    }
}