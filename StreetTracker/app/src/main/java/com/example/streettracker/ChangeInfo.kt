package com.example.streettracker

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun ChangeInfo(navController: NavHostController){
    val Context = LocalContext.current
    var username by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var email by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var password by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var oldPassword by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var confirmation by remember {
        mutableStateOf(TextFieldValue(""))
    }
    Column(modifier = Modifier.fillMaxSize(0.97f)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Ganti Informasi User", modifier = Modifier.padding(5.dp), fontWeight = FontWeight.ExtraBold, color = Color(0xFF00B0FF), fontSize = 30.sp)
        }
        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = Color.Black
        )
        LazyColumn{
            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)) {
                    IconButton(onClick = { navController?.navigate(Rumah.route) }) {
                        Image(painter = painterResource(id = R.drawable.back), contentDescription = "Back Icon", modifier = Modifier.fillMaxSize(0.1f))
                    }
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Note: Isi yang perlu diubah saja", fontSize = 20.sp, modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 23.dp))
                }
                Text(text = "Username", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 23.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        value = username,
                        onValueChange = {
                            username = it
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .padding(10.dp, vertical = 15.dp),
                    )
                }
                Text(text = "Email", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 23.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        value = email,
                        onValueChange = {
                            email = it
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .padding(10.dp, vertical = 15.dp),
                    )
                }
                Text(text = "Old Password", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 23.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        value = oldPassword,
                        onValueChange = {
                            oldPassword = it
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .padding(horizontal = 10.dp, vertical = 15.dp),
                    )
                }
                Text(text = "Password", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 23.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .padding(horizontal = 10.dp, vertical = 15.dp),
                    )
                }
                Text(text = "Confirmation", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 23.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        value = confirmation,
                        onValueChange = {
                            confirmation = it
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .padding(10.dp, vertical = 15.dp),
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(onClick = {
                        if(username.text != "") {
                            Repo.updateReporter(UserRepo.usernameCookies,username.text)
                            UserRepo.updateUsername(username.text)
                            Toast.makeText(Context, "Username berhasil diubah", Toast.LENGTH_LONG).show()
                        }
                        if(email.text != ""){
                            UserRepo.updateEmail(email.text)
                            Toast.makeText(Context, "Email berhasil diubah", Toast.LENGTH_LONG).show()
                        }
                        if(password.text != "" && password.text == confirmation.text) {
                            if(oldPassword.text != UserRepo.getUserbyUsername(UserRepo.usernameCookies)?.password) {
                                UserRepo.updatePassword(password.text)
                                Toast.makeText(Context, "Password berhasil diubah", Toast.LENGTH_LONG).show()
                            }
                        }
                    },border = BorderStroke(1.dp, Color(0xFF00B0FF)), shape = RoundedCornerShape(20), modifier = Modifier
                        .fillMaxWidth(0.92f)
                        .padding(horizontal = 0.dp, vertical = 5.dp)) {
                        Text(text = "Save", color = Color(0xFF00B0FF), fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        }

    }
}