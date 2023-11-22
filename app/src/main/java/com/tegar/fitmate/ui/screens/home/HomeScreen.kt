package com.tegar.fitmate.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tegar.fitmate.ui.theme.satoshiFontFamily

@Composable
fun HomeScreen(
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,

    ) {

   Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
       Text("Trending Workout Wonders!", fontFamily =  satoshiFontFamily , fontWeight = FontWeight.Bold , fontSize = 16.sp)
   }
}