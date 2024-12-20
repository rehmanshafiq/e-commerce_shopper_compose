package com.shafiq.shopper_ecommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.shafiq.shopper_ecommerce.model.UIProductModel
import com.shafiq.shopper_ecommerce.navigation.CartScreen
import com.shafiq.shopper_ecommerce.navigation.CartSummaryScreen
import com.shafiq.shopper_ecommerce.navigation.HomeScreen
import com.shafiq.shopper_ecommerce.navigation.ProductDetails
import com.shafiq.shopper_ecommerce.navigation.ProfileScreen
import com.shafiq.shopper_ecommerce.navigation.UserAddressRoute
import com.shafiq.shopper_ecommerce.navigation.UserAddressRouteWrapper
import com.shafiq.shopper_ecommerce.navigation.productNavType
import com.shafiq.shopper_ecommerce.navigation.userAddressNavType
import com.shafiq.shopper_ecommerce.ui.feature.cart.CartScreen
import com.shafiq.shopper_ecommerce.ui.feature.home.HomeScreen
import com.shafiq.shopper_ecommerce.ui.feature.product_details.ProductDetailsScreen
import com.shafiq.shopper_ecommerce.ui.feature.summary.CartSummaryScreen
import com.shafiq.shopper_ecommerce.ui.feature.user_address.UserAddressScreen
import com.shafiq.shopper_ecommerce.ui.theme.Shopper_ecommerceTheme
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Shopper_ecommerceTheme {
                val shouldShowBottomNav = remember {
                    mutableStateOf(true)
                }
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        AnimatedVisibility(
                            visible = shouldShowBottomNav.value,
                            enter = fadeIn()
                        ) {
                            BottomNavigationBar(navController = navController)
                        }
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        NavHost(navController = navController, startDestination = HomeScreen) {
                            composable<HomeScreen> {
                                shouldShowBottomNav.value = true
                                HomeScreen(navController)
                            }
                            composable<CartScreen> {
                                shouldShowBottomNav.value = true
                                CartScreen(navController)
                            }
                            composable<ProfileScreen> {
                                shouldShowBottomNav.value = true
                                HomeScreen(navController)
                            }
                            composable<CartSummaryScreen> {
                                shouldShowBottomNav.value = false
                                CartSummaryScreen(navController = navController)
                            }
                            composable<ProductDetails>(
                                typeMap = mapOf(typeOf<UIProductModel>() to productNavType)
                            ) {
                                shouldShowBottomNav.value = false
                                val productRoute = it.toRoute<ProductDetails>()
                                ProductDetailsScreen(
                                    navController = navController,
                                    product = productRoute.product
                                )
                            }
                            composable<UserAddressRoute>(
                                typeMap = mapOf(typeOf<UserAddressRouteWrapper>() to userAddressNavType)
                            ) {
                                shouldShowBottomNav.value = false
                                val userAddressRoute = it.toRoute<UserAddressRoute>()
                                UserAddressScreen(
                                    navController = navController,
                                    userAddress = userAddressRoute.userAddressRouteWrapper.userAddress
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomAppBar {
        // current route
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        val items = listOf(
            BottomNavItems.Home,
            BottomNavItems.Cart,
            BottomNavItems.Profile
        )
        items.forEach { item ->
            val isSelected = currentRoute?.substringBefore("?") == item.route::class.qualifiedName
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { startRoute ->
                            popUpTo(startRoute) {
                                saveState = true
                            }
                        }
                    }
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(
                            if (isSelected)
                                MaterialTheme.colorScheme.primary
                            else
                                Color.Gray
                        )
                    )
                },
                colors = NavigationBarItemDefaults.colors().copy(
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = Color.Gray,
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}

sealed class BottomNavItems(val route: Any, val title: String, val icon: Int) {
    data object Home : BottomNavItems(HomeScreen, "Home", icon = R.drawable.ic_home)
    data object Cart : BottomNavItems(CartScreen, "Cart", icon = R.drawable.ic_cart)
    data object Profile : BottomNavItems(ProfileScreen, "Profile", icon = R.drawable.ic_profile_nav)
}