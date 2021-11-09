package com.chethan.example.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chethan.example.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Chethan on 10/12/2021.
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
