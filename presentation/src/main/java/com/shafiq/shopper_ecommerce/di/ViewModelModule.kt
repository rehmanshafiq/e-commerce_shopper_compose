package com.shafiq.shopper_ecommerce.di

import com.shafiq.shopper_ecommerce.feature.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(get(), get())
    }
}