package com.pi.ProjectInclusion.android.screens.addStudentRegisterScreen

import android.Manifest
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.material3.Card
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kmptemplate.logger.AppLoggerImpl
import com.pi.ProjectInclusion.Black
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.Transparent
import com.pi.ProjectInclusion.android.R.*
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
                ProfileItem(context)
                ItemAddStudentScreeningRegisterScreen(context)
            }
        }
    }
}

@Composable
fun ProfileItem(context: Context) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically // Aligns image & text vertically
    )
    {
        // Profile Image
        Image(
            painter = painterResource(id = R.drawable.upload_profile_ic), // your image
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(RectangleShape)
                .border(1.dp, Color.Gray, RectangleShape)
        )

        Spacer(modifier = Modifier.width(16.dp)) // Space between image and text

        // Text on the right
        Column {
            Text(
                text = "Upload a profile photo of the student.",
                fontSize = 16.sp,
                fontFamily = FontFamily(
                    Font(R.font.roboto_regular)
                )
            )
            Card(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFFFFF)
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color(0xFF2C3EA2)),
                elevation = CardDefaults.cardElevation(4.dp)

                // Do not set containerColor, let the Box inside handle the gradient
            ){
                Text ( modifier = Modifier.wrapContentWidth()
                    .padding(start = 26.dp, end = 26.dp, top = 8.dp, bottom = 8.dp)
                    .clickable {  },
                    text = stringResource(R.string.string_addphoto),
                    color = White,
                    fontSize = 16.sp// Make sure text is readable on gradient
                )
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
            text = stringResource(R.string.student_name),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(stringResource(R.string.student_name_ex)) },
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
            text = stringResource(R.string.student_gender),
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
            text = stringResource(R.string.student_dob),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(stringResource(R.string.dob_format)) },
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
                    contentDescription = stringResource(R.string.search_ic)
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
            text = stringResource(R.string.f_name),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(stringResource(R.string.f_name_ex)) },
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
            text = stringResource(R.string.student_class),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(stringResource(R.string.choose_option)) },
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
                text = stringResource(R.string.string_next),
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
            text = stringResource(R.string.f_occupation),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(stringResource(R.string.choose_option)) },
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
                    contentDescription = stringResource(R.string.search_ic)
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
            text = stringResource(R.string.m_name),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(stringResource(R.string.m_name_ex)) },
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
            text = stringResource(R.string.m_occupation),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(stringResource(R.string.choose_option)) },
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
                    contentDescription = stringResource(R.string.search_ic)
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
            text = stringResource(R.string.parent_mobile),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(stringResource(R.string.enter_here)) },
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
            text = stringResource(R.string.education_status),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(stringResource(R.string.choose_option)) },
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
                    contentDescription = stringResource(R.string.search_ic)
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
            text = stringResource(R.string.student_board),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(stringResource(R.string.choose_option)) },
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
                    contentDescription = stringResource(R.string.search_ic)
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
            text = stringResource(R.string.type_school),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(stringResource(R.string.choose_option)) },
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
                    contentDescription = stringResource(R.string.search_ic)
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
                text = stringResource(R.string.add_student),
                color = White,
                fontSize = 16.sp// Make sure text is readable on gradient
            )
        }




    }




}




