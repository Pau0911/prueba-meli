package com.meli.productsearch.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberAsyncImagePainter
import com.meli.productsearch.model.Product
import com.meli.productsearch.ui.screen.state.ErrorItem
import com.meli.productsearch.ui.screen.state.LoadingItem
import com.meli.productsearch.ui.screen.state.LoadingView
import com.meli.productsearch.ui.theme.Fonts
import com.meli.productsearch.ui.theme.button_primary
import com.meli.productsearch.ui.theme.color_primary
import com.meli.productsearch.viewModel.ResultsViewModel
import kotlinx.coroutines.flow.Flow


@Composable
fun ResultsView(
    text: String,
    goToDetailsView: (String) -> Unit,
    resultViewModel: ResultsViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            IconButton(
                modifier = Modifier.padding(10.dp),
                onClick = { }
            ) {
                Icon(
                    Icons.Filled.Search,
                    "contentDescription", tint = button_primary
                )
            }
            Text(
                text = "Mostrando resultados para: $text",
                style = TextStyle(
                    fontSize = 21.sp,
                    fontFamily = Fonts,
                    fontWeight = FontWeight.Normal
                ),
            )
        }, backgroundColor = color_primary)
    }) { paddingValues ->
        Column(
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            ProductList(products = resultViewModel.getResults(text), goToDetailsView)
        }
    }

}


@Composable
fun ProductList(products: Flow<PagingData<Product>>, goToDetailsView: (String) -> Unit) {
    val lazyProductItems = products.collectAsLazyPagingItems()

    LazyColumn {

        items(lazyProductItems) { product ->
            ProductItem(product = product!!, onClick = {
                goToDetailsView(product.id)
            })
        }
        lazyProductItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                }
                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = lazyProductItems.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(message = e.error.localizedMessage!!,
                            modifier = Modifier.fillParentMaxSize(),
                            onClickRetry = { retry() })
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = lazyProductItems.loadState.append as LoadState.Error
                    item {
                        ErrorItem(message = e.error.localizedMessage!!, onClickRetry = { retry() })
                    }
                }
            }
        }
    }
}


@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(
                bottom = 5.dp, top = 5.dp,
                start = 5.dp, end = 5.dp
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(15.dp),
        elevation = 12.dp
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colors.surface)
        ) {
            Surface(
                modifier = Modifier.size(110.dp),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colors.surface.copy(
                    alpha = 0.2f
                )
            ) {
                val painter = rememberAsyncImagePainter(product.thumbnail)

                ProductImage(painter)
            }
            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = product.title,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = Fonts,
                        fontWeight = FontWeight.Normal
                    ),
                    color = Color.Black
                )
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.medium
                ) {
                    if (product.price.toString() != null) {
                        Text(
                            text = String.format("$ %,d", product.price),
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = Fonts,
                                fontWeight = FontWeight.Light
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(end = 25.dp)
                        )
                    }

                }
            }
        }
    }
}


@Composable
fun ProductImage(
    painter: Painter
) {
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .height(24.dp)
            .clip(shape = RoundedCornerShape(12.dp)),
        contentScale = ContentScale.Crop
    )
}


