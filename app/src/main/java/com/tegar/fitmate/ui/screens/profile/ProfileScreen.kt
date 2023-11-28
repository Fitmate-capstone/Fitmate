package com.tegar.fitmate.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tegar.fitmate.ui.screens.model.UserData

@Composable
fun ProfileScreen(
    userData: UserData?,
    onSignIn: () -> Unit,
    onSignOut: () -> Unit,
    redirectToHome : () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userData == null) {
            // User is not logged in, show login button
            Button(onClick = onSignIn) {
                Text("Login")
            }
        } else {
            if (userData?.profilePictureUrl != null) {
                AsyncImage(
                    model = userData.profilePictureUrl,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(
                            CircleShape
                        )
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // User is logged in, show profile and logout button
            Text("Hello  ${userData.username}")
            // Display user data or other profile information here
            Button(onClick = {
                onSignOut()
                redirectToHome()
            }) {
                Text("Keluar")
            }
        }
    }
}
