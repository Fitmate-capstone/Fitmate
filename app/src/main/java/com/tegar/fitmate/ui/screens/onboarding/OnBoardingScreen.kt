package com.tegar.fitmate.ui.screens.onboarding

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tegar.fitmate.ui.composables.OnBoardDetails
import com.tegar.fitmate.ui.composables.OnBoardImageView
import com.tegar.fitmate.ui.composables.OnBoardNavButton
import com.tegar.fitmate.ui.composables.TabSelector
import com.tegar.fitmate.ui.util.OnBordingPrefence


@Composable
fun OnBoardingScreen(
    navigateToHome : () -> Unit,

    ) {
    val onboardPages = onboardPagesList

    val context = LocalContext.current

    val currentPage = remember { mutableStateOf(0) }

//    LaunchedEffect(key1 =OnBordingPrefence.isOnboardingCompleted(context)  ) {
//        navigateToHome()
//    }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Box(

            contentAlignment = Alignment.TopEnd,
            modifier = Modifier.fillMaxWidth().clickable {
                OnBordingPrefence.setOnboardingCompleted(context, true)
                navigateToHome()
            }
        ) {
            Text("Skip", style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(36.dp))

        OnBoardImageView(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()  ,
            imageRes = onboardPages[currentPage.value].imageRes
        )
        Spacer(modifier = Modifier.height(64.dp))

        OnBoardDetails(
            modifier = Modifier
                .weight(1f),

            currentPage = onboardPages[currentPage.value]
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,

            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 36.dp)
        ) {


            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),


                ) {

                TabSelector(
                    onboardPages = onboardPages,
                    currentPage = currentPage.value
                ) { index ->
                    currentPage.value = index
                }
            }

            OnBoardNavButton(
                currentPage = currentPage.value,
                noOfPages = onboardPages.size,
                onNextClicked = {
                    currentPage.value++

                },
                onEndClicked = {
                    OnBordingPrefence.setOnboardingCompleted(context, true)
                    navigateToHome()
                }

            )
        }
    }
}