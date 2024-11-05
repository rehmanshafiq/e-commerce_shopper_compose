package com.shafiq.shopper_ecommerce

import android.app.Application
import com.shafiq.data.di.dataModule
import com.shafiq.domain.di.domainModule
import com.shafiq.shopper_ecommerce.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShopperApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ShopperApp)
            modules(
                listOf(
                    presentationModule,
                    dataModule,
                    domainModule
                )
            )
        }
    }
}