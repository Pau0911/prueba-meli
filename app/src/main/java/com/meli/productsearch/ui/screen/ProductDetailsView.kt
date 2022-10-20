package com.meli.productsearch.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.meli.productsearch.R
import com.meli.productsearch.model.Attribute
import com.meli.productsearch.model.ProductDetails
import com.meli.productsearch.ui.theme.Fonts
import com.meli.productsearch.ui.theme.button_primary
import com.meli.productsearch.ui.theme.color_primary
import com.meli.productsearch.utils.Response
import com.meli.productsearch.viewModel.ProductDetailsViewModel


@Composable
fun DetailsView(
    id: String, viewModel: ProductDetailsViewModel = hiltViewModel(), modifier: Modifier = Modifier
) {
    viewModel.getDetailProduct(id)
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()

    ) {
        Scaffold(topBar = {
            TopAppBar(title = {
                IconTopAppBar()
                Text(
                    text = "Detalles del producto",
                    fontFamily = Fonts,
                    fontWeight = FontWeight.Normal
                )
            }, backgroundColor = color_primary, modifier = modifier.align(Alignment.Center))
        }) {
            when (val productResponse = viewModel.productState.value) {
                is Response.Loading -> {
                }
                is Response.Success -> {
                    if (productResponse.data != null) {
                        Card(
                            Modifier.align(Alignment.Center), productResponse.data, context
                        )
                    }
                }
                else -> {
                    Toast.makeText(
                        context, "Error cargando el producto", Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    }

}

@Composable
fun Card(
    modifier: Modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxWidth()
        .padding(top = 20.dp),

    productDetails: ProductDetails,
    context: Context,
    elevation: Dp = 1.dp,
    border: BorderStroke? = null,
    background: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(background),
    shape: Shape = MaterialTheme.shapes.medium,
) {
    Card(
        backgroundColor = background,
        contentColor = contentColor,
        shape = shape,
        elevation = elevation,
        border = border,
        modifier = modifier
    ) {
        ContentCard(productDetails, context)
    }
}

@Composable
fun ContentCard(productDetails: ProductDetails, context: Context) {
    Column(
        modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color.LightGray, shape = CircleShape)
                    .size(30.dp), contentAlignment = Alignment.Center
            ) {
                IconMeli()
            }

            Spacer(modifier = Modifier.width(32.dp))

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp)
            ) {
                Text(
                    text = productDetails.title, style = TextStyle(
                        fontSize = 16.sp, fontFamily = Fonts, fontWeight = FontWeight.Normal
                    )
                )
            }
        }

        ProductImage(productDetails.pictures[0].url)

        Row(Modifier.padding(start = 16.dp, end = 24.dp, top = 16.dp)) {
            Text(
                color = Color.Black,
                text = String.format("$ %,d", productDetails.price),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 16.sp, fontFamily = Fonts, fontWeight = FontWeight.Light
                ),
            )
        }
        Row(Modifier.padding(start = 16.dp, end = 24.dp, top = 16.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.location),
                contentDescription = "Ubicación"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                color = Color.Black,
                text = productDetails.seller_address.city.name,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 16.sp, fontFamily = Fonts, fontWeight = FontWeight.Light
                ),
            )
        }
        Row(Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                color = Color.Black,
                text = "Caracteristicas",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 18.sp, fontFamily = Fonts, fontWeight = FontWeight.Normal
                ),
            )
        }
        AtributteList(productDetails)
        Spacer(modifier = Modifier.height(10.dp))
        Buttons(context)
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun AtributteList(productDetails: ProductDetails) {
    val list = productDetails?.attributes.subList(0, 4)
    Column() {
        list.forEach { item ->
            Atributte(item)
        }
    }
}

@Composable
fun Atributte(atributte: Attribute) {
    Row(Modifier.padding(start = 16.dp, end = 24.dp, top = 16.dp)) {
        if (atributte.name != null) {
            Text(
                color = Color.Black,
                text = atributte.name + ":",
                style = TextStyle(
                    fontSize = 16.sp, fontFamily = Fonts, fontWeight = FontWeight.Normal
                ),
            )
        }
    }
    Row(Modifier.padding(start = 16.dp, end = 24.dp, top = 16.dp)) {
        if (atributte.value_name != null) {
            Text(
                color = Color.Black,
                text = atributte.value_name,
                style = TextStyle(
                    fontSize = 16.sp, fontFamily = Fonts, fontWeight = FontWeight.Light
                ),
            )
        }
    }
}

@Composable
fun IconMeli() {
    Image(
        painter = painterResource(id = R.drawable.icon), contentDescription = null
    )
}

@Composable
fun ProductImage(url: String) {
    Image(
        rememberAsyncImagePainter(url),
        contentDescription = "Product",
        Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .height(194.dp),


        )
}

@Composable
fun Buttons(context: Context) {
    Box(
        Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.align(Alignment.CenterStart)) {

            Button(
                onClick = {
                    Toast.makeText(
                        context, "Comprar", Toast.LENGTH_SHORT
                    ).show()
                }, colors = ButtonDefaults.buttonColors(backgroundColor = button_primary)
            ) {
                Text(text = "Comprar", color = Color.White)

            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    Toast.makeText(
                        context, "Agregar al carrito", Toast.LENGTH_SHORT
                    ).show()

                }, colors = ButtonDefaults.buttonColors(backgroundColor = button_primary)
            ) {
                Text(text = "Agregar al carrito", color = Color.White)
            }
        }

        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            IconButton(onClick = {
                Toast.makeText(
                    context, "Agregado a favoritos", Toast.LENGTH_SHORT
                ).show()
            }) {
                Icon(Icons.Default.Favorite, contentDescription = null)
            }

            IconButton(onClick = {
                Toast.makeText(
                    context, "Compartiendo", Toast.LENGTH_SHORT
                ).show()
            }) {
                Icon(Icons.Default.Share, contentDescription = null)
            }
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
fun IconTopAppBar() {
    IconButton(modifier = Modifier.padding(start = 6.dp), onClick = {}) {
        Icon(
            Icons.Filled.Star, "contentDescription", tint = button_primary
        )
    }
}
