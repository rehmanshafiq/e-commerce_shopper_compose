package com.shafiq.domain.di

import com.shafiq.domain.usecase.AddToCartUseCase
import com.shafiq.domain.usecase.DeleteProductUseCase
import com.shafiq.domain.usecase.GetCartUseCase
import com.shafiq.domain.usecase.GetCategoriesUseCase
import com.shafiq.domain.usecase.GetProductUseCase
import com.shafiq.domain.usecase.UpdateQuantityUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        GetProductUseCase(get())
    }

    factory {
        GetCategoriesUseCase(get())
    }

    factory {
        AddToCartUseCase(get())
    }

    factory {
        GetCartUseCase(get())
    }

    factory {
        UpdateQuantityUseCase(get())
    }

    factory {
        DeleteProductUseCase(get())
    }
}