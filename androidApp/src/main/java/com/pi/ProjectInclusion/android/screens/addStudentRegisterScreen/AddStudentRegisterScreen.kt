@file:OptIn(ExperimentalMaterial3Api::class)

package com.pi.ProjectInclusion.android.screens.dashboardScreen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.material3.Card

import androidx.compose.material3.ColorScheme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth

import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size

import com.example.kmptemplate.logger.AppLoggerImpl
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.Transparent

import com.pi.ProjectInclusion.constants.CustomDialog
import com.pi.ProjectInclusion.data.model.GetLanguageListResponse


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun AddStudentRegisterScreen(navHostController: NavHostController) {
    var isDialogVisible by remember { mutableStateOf(false) }
    val logger = AppLoggerImpl()
    val query by rememberSaveable {
        mutableStateOf("")
    }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    val scrollState = rememberLazyGridState()
    val context = LocalContext.current
    val languageData = remember { mutableStateListOf<GetLanguageListResponse.Data.Result>() }
    CustomDialog(
        isVisible = isDialogVisible,
        onDismiss = { isDialogVisible = false },
        message = "Loading your data..."
    )
    val navController = NavHostController(context)
    val selectedLanguage = remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    var hasCameraPermission by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasCameraPermission = isGranted
    }
    if (hasCameraPermission) {

    } else {
        try {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        } catch (exc: Exception) {
            // Handle exception
        }
    }




    Surface(
        modifier = Modifier.wrapContentSize()
            .background(
                color = Transparent
            )
            .padding(0.dp),


        ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(
                    color = Transparent
                )
                .padding(10.dp),

        )
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally // Center rows inside the column
            )
            {
                ItemAddStudentScreeningRegisterScreen(context)

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemAddStudentScreeningRegisterScreen(context: Context){

    var text by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Student Name",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text("eg: Ramesh") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFD0D5DD),
                unfocusedBorderColor = Color(0xFFD0D5DD),
                cursorColor = Color.Black,
                containerColor = Color(0xFFFFFFFF)

            ),

            textStyle = TextStyle(fontSize = 16.sp, color = Black)

        )



    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Gender",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )






    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "DOB",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text("MM DD YY") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFD0D5DD),
                unfocusedBorderColor = Color(0xFFD0D5DD),
                cursorColor = Color.Black,
                containerColor = Color(0xFFFFFFFF)

            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange, // or any icon you like
                    contentDescription = "Search Icon"
                )
            },
            textStyle = TextStyle(fontSize = 16.sp, color = Black)

        )




    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Father's Name",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text("eg: Singh") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFD0D5DD),
                unfocusedBorderColor = Color(0xFFD0D5DD),
                cursorColor = Color.Black,
                containerColor = Color(0xFFFFFFFF)

            ),

            textStyle = TextStyle(fontSize = 16.sp, color = Black)

        )




    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Class",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text("Select an option") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFD0D5DD),
                unfocusedBorderColor = Color(0xFFD0D5DD),
                cursorColor = Color.Black,
                containerColor = Color(0xFFFFFFFF)

            ),

            textStyle = TextStyle(fontSize = 16.sp, color = Black)

        )




    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF101942)
            ),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)

            // Do not set containerColor, let the Box inside handle the gradient
        ){
            Text ( modifier = Modifier.wrapContentWidth()
                .padding(start = 26.dp, end = 26.dp, top = 8.dp, bottom = 8.dp)
                .clickable {  },
                text = "Next",
                color = White,
                fontSize = 16.sp// Make sure text is readable on gradient
            )
        }




    }




}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemAddStudentScreeningStep2RegisterScreen(context: Context){

    var text by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Father’s profession*",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text("Select an option") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFD0D5DD),
                unfocusedBorderColor = Color(0xFFD0D5DD),
                cursorColor = Color.Black,
                containerColor = Color(0xFFFFFFFF)

            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown, // or any icon you like
                    contentDescription = "Search Icon"
                )
            },
            textStyle = TextStyle(fontSize = 16.sp, color = Black)

        )



    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Mother's Name",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text("eg: Sita") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFD0D5DD),
                unfocusedBorderColor = Color(0xFFD0D5DD),
                cursorColor = Color.Black,
                containerColor = Color(0xFFFFFFFF)

            ),

            textStyle = TextStyle(fontSize = 16.sp, color = Black)

        )




    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Mother’s profession*",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text("Select an option") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFD0D5DD),
                unfocusedBorderColor = Color(0xFFD0D5DD),
                cursorColor = Color.Black,
                containerColor = Color(0xFFFFFFFF)

            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown, // or any icon you like
                    contentDescription = "Search Icon"
                )
            },
            textStyle = TextStyle(fontSize = 16.sp, color = Black)

        )



    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Mobile number of parents*",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text("Enter here") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFD0D5DD),
                unfocusedBorderColor = Color(0xFFD0D5DD),
                cursorColor = Color.Black,
                containerColor = Color(0xFFFFFFFF)

            ),

            textStyle = TextStyle(fontSize = 16.sp, color = Black)

        )




    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Education Status*",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text("Select an option") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFD0D5DD),
                unfocusedBorderColor = Color(0xFFD0D5DD),
                cursorColor = Color.Black,
                containerColor = Color(0xFFFFFFFF)

            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown, // or any icon you like
                    contentDescription = "Search Icon"
                )
            },
            textStyle = TextStyle(fontSize = 16.sp, color = Black)

        )



    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Board*",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text("Select an option") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFD0D5DD),
                unfocusedBorderColor = Color(0xFFD0D5DD),
                cursorColor = Color.Black,
                containerColor = Color(0xFFFFFFFF)

            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown, // or any icon you like
                    contentDescription = "Search Icon"
                )
            },
            textStyle = TextStyle(fontSize = 16.sp, color = Black)

        )



    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Type of school*",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text("Select an option") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFD0D5DD),
                unfocusedBorderColor = Color(0xFFD0D5DD),
                cursorColor = Color.Black,
                containerColor = Color(0xFFFFFFFF)

            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown, // or any icon you like
                    contentDescription = "Search Icon"
                )
            },
            textStyle = TextStyle(fontSize = 16.sp, color = Black)

        )



    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF101942)
            ),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)

            // Do not set containerColor, let the Box inside handle the gradient
        ){
            Text ( modifier = Modifier.wrapContentWidth()
                .padding(start = 26.dp, end = 26.dp, top = 8.dp, bottom = 8.dp)
                .clickable {  },
                text = "Add Student",
                color = White,
                fontSize = 16.sp// Make sure text is readable on gradient
            )
        }




    }




}




