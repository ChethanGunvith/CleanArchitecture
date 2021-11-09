package com.chethan.mericari.util

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.chethan.example.TestApp

/**
 * Created by Chethan on 10/12/2021.
 * Custom runner to disable dependency injection.
 */
class TestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}
