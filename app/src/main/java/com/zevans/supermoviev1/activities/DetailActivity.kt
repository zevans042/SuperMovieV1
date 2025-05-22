package com.zevans.supermoviev1.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.supermoviev1.R
import com.example.supermoviev1.databinding.ActivityDetailBinding
import com.example.supermoviev1.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import com.zevans.supermoviev1.data.SuperMovieServiceApi
import com.zevans.supermoviev1.data.Supermovie
import com.zevans.supermoviev1.utils.RetrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "SUPERMOVIE_Search"
        const val EXTRA_NAME = "SUPERMOVIE_totalResults"
        const val EXTRA_IMAGE = "SUPERMOVIE_Response"
    }

    private lateinit var binding: ActivityDetailBinding
    private var supermovieId: String? = null
    private lateinit var supermovie: Supermovie



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initActionBar()





        supermovieId = intent.getStringExtra(EXTRA_ID)
        val name = intent.getStringExtra(EXTRA_NAME)
        val image = intent.getStringExtra(EXTRA_IMAGE)

        binding.toolbarLayout.title = name
        Picasso.get().load(image).into(binding.photoImageView)

        findSupermovieById(supermovieId!!)




    }

    private fun initActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun loadData() {
        binding.content.titleTextView.text = supermovie.title
        binding.content.yearTextView.text = supermovie.year
        binding.content.synopsisTextView.text = supermovie.synopsis
        binding.content.durationTextView.text = supermovie.log
        binding.content.directorTextView.text = supermovie.director
        binding.content.genreTextView.text = supermovie.genre
        binding.content.countryTextView.text = supermovie.country

    }

    private fun findSupermovieById(id: String) {
        binding.content.progress.visibility = View.VISIBLE

        val service: SuperMovieServiceApi = RetrofitProvider.getRetrofit()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.findById(id)

                runOnUiThread {
                    binding.content.progress.visibility = View.GONE
                    if (response.isSuccessful) {
                        Log.i("HTTP", "respuesta correcta :)")
                        supermovie = response.body()!!
                        loadData()
                    } else {
                        Log.i("HTTP", "respuesta erronea :(")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


















