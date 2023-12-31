package com.tegar.fitmate.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tegar.fitmate.R
import com.tegar.fitmate.ui.theme.neutral10
import com.tegar.fitmate.ui.theme.neutral30
import com.tegar.fitmate.ui.theme.neutral80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navigatToEquimentSearch: () -> Unit,
    navigateToExplore: (String) -> Unit,

    modifier: Modifier = Modifier
) {

    var query by remember { mutableStateOf(TextFieldValue()) }

    TopAppBar(
        title = {
            Box(
                Modifier.padding(
                    end = 16.dp

                )
            ) {
                CustomTextField(
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Search,
                            null,
                            tint = neutral30
                        )
                    },
                    trailingIcon = null,
                    modifier = Modifier
                        .background(
                            neutral80,
                            RoundedCornerShape(percent = 50)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .height(40.dp),
                    fontSize = 14.sp,
                    placeholderText = "Search",
                    navigateToExplore = { value ->
                        navigateToExplore(value)
                    }

                )
            }
        },
        actions = {
            Surface(
                color = neutral80,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.clickable {
                    navigatToEquimentSearch()
                }
            ) {
                Box(Modifier.padding(8.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Flip,
                        tint = neutral10,
                        contentDescription = stringResource(id = R.string.search_equiment)
                    )
                }

            }

        },


        )
}