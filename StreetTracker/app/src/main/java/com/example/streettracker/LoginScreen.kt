package com.example.streettracker

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(navController: NavHostController){
    val Context = LocalContext.current
    var username by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var password by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.loginmobile), contentDescription = "Login Image")
            TextField(
                value = username,
                onValueChange = {
                    username = it
                },
                label = { Text(text = "Username") },
                modifier = Modifier.padding(10.dp),
//                colors = TextFieldDefaults.textFieldColors(
//                    backgroundColor = Color(0xFF00B0FF)
//                )
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                placeholder = { Text("Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    // Please provide localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = {passwordVisible = !passwordVisible}){
                        Icon(imageVector  = image, description)
                    }
                }
            )
            OutlinedButton(onClick = {
                var found = false
                for(user in UserRepo.users){
                    if(username.text == user.username && password.text == user.password){
                        UserRepo.usernameCookies = user.username
                        navController?.navigate(Rumah.route)
                        Toast.makeText(Context, "Login Success", Toast.LENGTH_LONG).show()
                        found = true
                    }
                }
                if(!found){
                    Toast.makeText(Context, "Credential Invalid", Toast.LENGTH_LONG).show()
                }
            },border = BorderStroke(1.dp, Color(0xFF00B0FF)), shape = RoundedCornerShape(20), modifier = Modifier
                .fillMaxWidth(0.72f)
                .padding(horizontal = 0.dp, vertical = 5.dp)) {
                Text(text = "Login", color = Color(0xFF00B0FF))
            }
            Text(text = "Doesn't have any account yet?")
            OutlinedButton(onClick = { navController?.navigate(Register.route) },border = BorderStroke(1.dp, Color.Red), shape = RoundedCornerShape(20)) {
                Text(text = "Register", color = Color.Red)
            }
        }

    }
}