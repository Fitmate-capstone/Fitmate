package com.tegar.fitmate.ui.screens.profile

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.tegar.fitmate.R
import com.tegar.fitmate.ui.composables.SliderBanner
import com.tegar.fitmate.ui.screens.model.UserData
import com.tegar.fitmate.ui.theme.neutral10
import com.tegar.fitmate.ui.theme.neutral80
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.util.Locale

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProfileScreen(
    userData: UserData?,
    onSignIn: () -> Unit,
    onSignOut: () -> Unit,
    redirectToHome: () -> Unit,
) {
    val currentLocale = LocalContext.current.resources.configuration
    var selectedLanguage by remember { mutableStateOf(Locale.getDefault().language) }
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (userData == null) {
            Spacer(modifier = Modifier.height(20.dp))
            SliderBanner()


            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Login to fitmate " , style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp
                ))
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onSignIn,
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 2.dp
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(
                        vertical = 14.dp,
                        horizontal = 49.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = neutral80,
                        contentColor = neutral10
                    ),
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        modifier = Modifier.size(20.dp),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Text(
                        text = stringResource(id = R.string.continue_with_google),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                            .offset(x = -20.dp / 2) //default icon width = 24.dp
                    )
                }
            }


        } else {
            if (userData?.profilePictureUrl != null) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,

                    modifier = Modifier.fillMaxWidth()
                ) {
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



                Column(modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,) {
                    Text("Hello  ${userData.username}")

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            onSignOut()
                            redirectToHome()
                        },
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 2.dp
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(
                            vertical = 14.dp,
                            horizontal = 49.dp
                        ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = neutral80,
                            contentColor = neutral10
                        ),
                    ) {

                        Icon(
                            imageVector = Icons.Filled.Logout,
                            modifier = Modifier.size(20.dp),
                            contentDescription = null,
                            tint = Color.White
                        )
                        Text(
                            text = stringResource(id = R.string.logout),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .weight(1f)
                                .offset(x = -20.dp / 2) //default icon width = 24.dp
                        )
                    }

                }


            }

        }
    }
}

fun setLocale(context: Context, locale: Locale) {
    val configuration = Configuration(context.resources.configuration)
    configuration.setLocale(locale)
    context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
}
