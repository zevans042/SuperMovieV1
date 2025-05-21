package com.zevans.supermoviev1.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.supermoviev1.R

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "SUPERMOVIE_Search"
        const val EXTRA_NAME = "SUPERMOVIE_totalResults"
        const val EXTRA_IMAGE = "SUPERMOVIE_Response"
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)

    }
}