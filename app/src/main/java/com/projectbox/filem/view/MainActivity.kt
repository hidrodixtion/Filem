package com.projectbox.filem.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.projectbox.filem.R
import com.projectbox.filem.service.IService
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUi()
    }

    private fun initUi() {
        supportFragmentManager.beginTransaction().replace(R.id.container, MovieListFragment()).commit()
    }
}
