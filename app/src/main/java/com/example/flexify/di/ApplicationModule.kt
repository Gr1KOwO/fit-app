package com.example.flexify.di


import android.content.Context
import com.example.flexify.MyApplication
import dagger.Module
import dagger.Provides

@Module
open class ApplicationModule {

    @Provides
    fun provideApplicationContext(app: MyApplication): Context {
        return app.applicationContext
    }
}