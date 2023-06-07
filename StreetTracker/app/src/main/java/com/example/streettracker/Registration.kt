package com.example.streettracker

import android.media.Image
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import java.util.logging.Handler
import kotlin.math.log

@Composable
fun Registration(navController: NavHostController){
    var photoUri: Uri? by remember { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        //When the user has selected a photo, its URL is returned here
        photoUri = uri
    }
    val Context = LocalContext.current
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var confirmationVisible by rememberSaveable { mutableStateOf(false) }
    var username by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var email by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var password by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var confirmPassword by remember {
        mutableStateOf(TextFieldValue(""))
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Registration", modifier = Modifier.padding(5.dp), fontWeight = FontWeight.ExtraBold, color = Color(0xFF986DF2), fontSize = 30.sp)
        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 1.dp,
            color = Color.Black
        )
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
            value = email,
            onValueChange = {
                email = it
            },
            label = { Text(text = "Email") },
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
            modifier = Modifier.padding(10.dp),
            visualTransformation = if (confirmationVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (confirmationVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (confirmationVisible) "Hide password" else "Show password"

                IconButton(onClick = {confirmationVisible = !confirmationVisible}){
                    Icon(imageVector  = image, description)
                }
            }
        )
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmation") },
            singleLine = true,
            placeholder = { Text("Confirmation") },
            modifier = Modifier.padding(10.dp),
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
        Column {
            if (photoUri != null) {
                //Use Coil to display the selected image
                val painter = rememberAsyncImagePainter(
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(data = photoUri)
                        .build()
                )
                Image(painter = painter, contentDescription = "Image", modifier = Modifier.fillMaxSize(0.5f))
            }
            Button(
                onClick = {
                    //On button press, launch the photo picker
                    launcher.launch(PickVisualMediaRequest(
                        //Here we request only photos. Change this to .ImageAndVideo if
                        //you want videos too.
                        //Or use .VideoOnly if you only want videos.
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                    ))
                }
            ) {
                Text("Select Photo KTP")
            }

        }
        OutlinedButton(onClick = {
            if(UserRepo.isUsernameExist(username.text)){
                Toast.makeText(Context, "Register Failed", Toast.LENGTH_SHORT).show()
                Toast.makeText(Context, "Username is already exist", Toast.LENGTH_LONG).show()
            }else if (UserRepo.isEmailExist(email.text)){
                Toast.makeText(Context, "Register Failed", Toast.LENGTH_SHORT).show()
                Toast.makeText(Context, "Email is already exist", Toast.LENGTH_LONG).show()
            } else if(username.text.length <= 6 && password.text.length <= 6){
                Toast.makeText(Context, "Register Failed", Toast.LENGTH_SHORT).show()
                Toast.makeText(Context, "Username and password should be more than 6 character", Toast.LENGTH_LONG).show()
            } else if(username.text != "" && email.text != ""&& password.text != "" && password.text == confirmPassword.text && photoUri != null){
                val newUser = User(UserRepo.users.last().id + 1,username.text,email.text,password.text,false,photoUri,false,"User",0)
                UserRepo.users.add(newUser)
                Toast.makeText(Context, "Register Successfully", Toast.LENGTH_SHORT).show()
                navController?.navigate(Login.route)
            }else{
                Toast.makeText(Context, "Register Failed", Toast.LENGTH_SHORT).show()
                Toast.makeText(Context, "All form should be filled and password must be the same as confirmation", Toast.LENGTH_LONG).show()
            }
        },border = BorderStroke(1.dp, Color(0xFF986DF2)), shape = RoundedCornerShape(20), modifier = Modifier
            .fillMaxWidth(0.72f)
            .padding(horizontal = 0.dp, vertical = 5.dp)) {
            Text(text = "Register", color = Color(0xFF986DF2))
        }
    }
}

