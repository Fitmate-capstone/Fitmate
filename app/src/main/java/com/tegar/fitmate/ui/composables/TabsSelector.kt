package com.tegar.fitmate.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tegar.fitmate.ui.model.OnboardPage
import com.tegar.fitmate.ui.theme.lightblue60

@Composable
fun TabSelector(onboardPages: List<OnboardPage>, currentPage: Int, onTabSelected: (Int) -> Unit) {
    onboardPages.forEachIndexed { index, _ ->

        Box(
            modifier = Modifier
                .size(8.dp)
                .clickable {
                    onTabSelected(index)
                }
                .background(
                    color = if (index == currentPage) lightblue60
                    else Color.LightGray, shape = RoundedCornerShape(4.dp)
                )
        )

    }
}