package com.shafiq.shopper_ecommerce.ui.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.shafiq.domain.model.Product
import com.shafiq.shopper_ecommerce.R
import com.shafiq.shopper_ecommerce.model.UIProductModel
import com.shafiq.shopper_ecommerce.navigation.ProductDetails
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {

    val uiState = viewModel.uiState.collectAsState()

    val loading = remember {
        mutableStateOf(false)
    }

    val error = remember {
        mutableStateOf<String?>(null)
    }

    val feature = remember {
        mutableStateOf<List<Product>>(emptyList())
    }

    val popular = remember {
        mutableStateOf<List<Product>>(emptyList())
    }

    val categories = remember {
        mutableStateOf<List<String>>(emptyList())
    }

    Scaffold {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when(uiState.value) {
                is HomeScreenUIEvents.Loading -> {
                    loading.value = true
                    error.value = null
                }
                is HomeScreenUIEvents.Success -> {
                    val data = (uiState.value as HomeScreenUIEvents.Success)

                    feature.value = data.featured
                    popular.value = data.popularProduct
                    categories.value = data.categories

                    loading.value = false
                    error.value = null
                }
                is HomeScreenUIEvents.Error -> {
                    val errorMessage = (uiState.value as HomeScreenUIEvents.Error).message
                    loading.value = false
                    error.value = errorMessage
                }
            }

            HomeContent(
                featured = feature.value,
                popularProducts = popular.value,
                categories = categories.value,
                isLoading = loading.value,
                errorMessage = error.value,
                onClick = {
                    navController.navigate(ProductDetails(UIProductModel.fromProduct(it)))
                }
            )
        }
    }
}

@Composable
fun ProfileHeader() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Hello!",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "John Doe",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.ic_bell),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterEnd)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = 0.3f))
                .padding(8.dp),
            contentScale = ContentScale.Inside
        )
    }
}

@Composable
fun HomeContent(
    featured: List<Product>,
    popularProducts:List<Product>,
    categories: List<String>,
    isLoading: Boolean = false,
    errorMessage: String? = null,
    onClick: (Product) -> Unit
) {
    LazyColumn {
        item {
            ProfileHeader()
            Spacer(modifier = Modifier.size(8.dp))
            SearchBar(value = "", onValueChange = {})
            Spacer(modifier = Modifier.size(16.dp))
        }
        item {
            if (isLoading) {
                ShowLoader()
            }
            errorMessage?.let { 
                Text(text = it, style = MaterialTheme.typography.bodyMedium)
            }
            if (categories.isNotEmpty()) {
                LazyRow {
                    items(
                        categories,
                        key = {it}
                    ) { category ->
                        val isVisible = remember {
                            mutableStateOf(false)
                        }
                        LaunchedEffect(key1 = true) {
                            isVisible.value = true
                        }
                        AnimatedVisibility(
                            visible = isVisible.value,
                            enter = fadeIn() + expandVertically()
                        ) {
                            Text(
                                text = category.replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(8.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.size(22.dp))
            }
            if (featured.isNotEmpty()) {
                HomeProductRow(products = featured, title = "Featured", onClick = onClick)
                Spacer(modifier = Modifier.size(16.dp))
            }
            if (popularProducts.isNotEmpty()) {
                HomeProductRow(products = popularProducts, title = "Popular", onClick = onClick)
            }
        }
    }
}

@Composable
private fun ShowLoader() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(modifier = Modifier.size(50.dp))
        Text(text = "Loading...", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(32.dp),
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_search), 
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedPlaceholderColor = Color.Transparent,
            unfocusedLabelColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.LightGray.copy(alpha = 0.3f),
            unfocusedContainerColor = Color.LightGray.copy(alpha = 0.3f)
        ),
        placeholder = {
            Text(
                text = "Search for products",
                style = MaterialTheme.typography.bodySmall
            )
        }
    )
}

@Composable
fun HomeProductRow(products: List<Product>, title:String, onClick: (Product) -> Unit) {
    Column {
        Box(modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterStart),
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "View all",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow {
            items(
                products,
                key = {it.id}
            ) { product ->
                val isVisible = remember {
                    mutableStateOf(false)
                }
                LaunchedEffect(key1 = true) {
                    isVisible.value = true
                }
                androidx.compose.animation.AnimatedVisibility(
                    visible = isVisible.value,
                    enter = fadeIn() + expandVertically()
                ) {
                    ProductItem(product = product, onClick = onClick)
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick: (Product) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .size(width = 126.dp, height = 150.dp)
            .clickable { onClick(product) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(contentColor = Color.LightGray.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = product.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 8.dp),
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}