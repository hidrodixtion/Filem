package com.projectbox.filem.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.projectbox.filem.R

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
