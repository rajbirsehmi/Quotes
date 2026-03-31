package com.creative.quotes

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        // Force the runner to use HiltTestApplication instead of your production App class
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}