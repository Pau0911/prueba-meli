package com.meli.productsearch.ui.screen

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.meli.productsearch.R
import com.meli.productsearch.ui.theme.Fonts
import com.meli.productsearch.ui.theme.button_primary
import com.meli.productsearch.ui.theme.color_background
import com.meli.productsearch.ui.theme.color_primary
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun SearchView(
    goToResultsView: (String) -> Unit, scrollableState: ScrollState = rememberScrollState()
) {
    var text by remember { mutableStateOf("") }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    val maxChar= 30

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = color_primary,
                elevation = 10.dp
            ){}
        }){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            BackgroundContent()
            Spacer(modifier = Modifier.height(24.dp))
            TextField(
                value = text,
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .onFocusEvent { focusState ->
                        if (focusState.isFocused) {
                            coroutineScope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                        }
                    },
                textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Normal),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Icono de buscar"
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = color_background,
                    cursorColor = Color.Black,
                    disabledLabelColor = Color.Blue,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                onValueChange = {
                    if (it.length <= maxChar) text = it

                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                placeholder = {
                    Text(
                        text = "Buscar un producto", fontFamily = Fonts,
                        fontWeight = FontWeight.Light
                    )
                },
            )
            Button(
                onClick = {
                    if (text.isNotEmpty()) goToResultsView(text) else Toast.makeText(
                        context,
                        "Por favor ingresar busqueda",
                        Toast.LENGTH_SHORT
                    ).show()
                }, colors = ButtonDefaults.buttonColors(backgroundColor = button_primary),
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(text = "Buscar", color = Color.White)

            }

        }
    }

}


@Composable
fun BackgroundContent() {
    Image(
        painter = painterResource(id = R.drawable.logo_1),
        contentDescription = "Logo",
        modifier = Modifier.size(150.dp)
    )

}
