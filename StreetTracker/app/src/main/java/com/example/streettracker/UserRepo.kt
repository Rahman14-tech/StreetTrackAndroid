package com.example.streettracker

import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController

// Karena waktu yang tidak cukup maka akan terdapat pemerintah besar yang dimaksud pemerintah besar yaitu presiden dan gubernur
object UserRepo{
    var usernameCookies = ""
    var idCookies = 0
    val users = mutableListOf<User>(
        User(1,"Pakdhe Owi","owi@gmail.com","YTNKTS1!",true,null, true,"Solo",0),
        User(2,"Malang","jatim@gmail.com","YTNKTS2",true,null, true,"Malang",0),
        User(3,"Roy JS","roy@gmail.com","RJSGGBET17",false,null, false,"Admin",0),
    )
    fun getUser(id: Int) = users.firstOrNull { it.id == id }
    fun getUserbyUsername(tempUsername: String) = users.firstOrNull{ it.username == tempUsername }
    fun findbyEmail(tempEmail: String) = users.firstOrNull { it.email == tempEmail}
    fun isEmailExist(tempEmail:String):Boolean{
        if(findbyEmail(tempEmail) != null){
            return true
        }
        return false
    }
    fun isUsernameExist(tempUsername:String):Boolean{
        if(getUserbyUsername(tempUsername) != null){
            return true
        }
        return false
    }
    fun updateUsername(tempUsername:String){
        if(tempUsername != ""){
            getUserbyUsername(usernameCookies)?.username = tempUsername
            usernameCookies = tempUsername
        }
    }
    fun updateEmail(tempEmail:String){
        if(tempEmail != ""){
            getUserbyUsername(usernameCookies)?.email = tempEmail
        }
    }
    fun updatePassword(newPassword : String){
        getUserbyUsername(usernameCookies)?.password = newPassword
    }
}

data class User(
    val id: Int,
    var username: String,
    var email: String,
    var password: String,
    val pemerintah: Boolean,
    val KTP : Uri?,
    val KTPApproval : Boolean,
    val Kepemerintahan : String,
    val falseReportMax : Int,
)