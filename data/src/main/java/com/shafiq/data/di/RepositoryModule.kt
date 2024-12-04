package com.shafiq.data.di

import com.shafiq.data.repository.CartRepositoryImpl
import com.shafiq.data.repository.CategoryRepositoryImpl
import com.shafiq.data.repository.OrderRepositoryImpl
import com.shafiq.data.repository.ProductRepositoryImpl
import com.shafiq.domain.repository.CartRepository
import com.shafiq.domain.repository.CategoryRepository
import com.shafiq.domain.repository.OrderRepository
import com.shafiq.domain.repository.ProductRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ProductRepository> {
        ProductRepositoryImpl(get())
    }

    single<CategoryRepository> {
        CategoryRepositoryImpl(get())
    }

    single<CartRepository> {
        CartRepositoryImpl(get())
    }

    single<OrderRepository> {
        OrderRepositoryImpl(get())
    }
}