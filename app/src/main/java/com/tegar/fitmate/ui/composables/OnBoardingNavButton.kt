package com.tegar.fitmate.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tegar.fitmate.ui.theme.neutral80
import com.tegar.fitmate.ui.theme.neutral90

@Composable
fun OnBoardNavButton(
    modifier: Modifier = Modifier, currentPage: Int, noOfPages: Int, onNextClicked: () -> Unit , onEndClicked : () -> Unit
) {
    Button(
        colors=  ButtonDefaults.buttonColors(
            containerColor = neutral80
        ),
                onClick = {
            if (currentPage < noOfPages - 1) {
                onNextClicked()
            }else{
                onEndClicked()
            }
        }, modifier = modifier
    ) {
        Text(text = if (currentPage < noOfPages - 1) "Next" else "Get Started" , style = MaterialTheme.typography.bodyMedium.copy(

        ))
    }
}