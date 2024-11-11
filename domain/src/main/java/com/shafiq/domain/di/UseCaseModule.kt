package com.shafiq.domain.di

import com.shafiq.domain.usecase.GetCategoriesUseCase
import com.shafiq.domain.usecase.GetProductUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        GetProductUseCase(get())
    }

    factory {
        GetCategoriesUseCase(get())
    }
}