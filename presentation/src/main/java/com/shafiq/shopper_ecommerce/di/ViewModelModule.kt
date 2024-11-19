package com.shafiq.shopper_ecommerce.di

import com.shafiq.shopper_ecommerce.ui.feature.home.HomeViewModel
import com.shafiq.shopper_ecommerce.ui.feature.product_details.ProductDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(get(), get())
    }
    viewModel {
        ProductDetailsViewModel()
    }
}