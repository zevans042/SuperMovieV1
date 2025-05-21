package com.zevans.supermoviev1.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.supermoviev1.R
import com.example.supermoviev1.databinding.ActivityMainBinding
import com.zevans.supermoviev1.adapters.SuperMovieAdapter
import com.zevans.supermoviev1.data.Supermovie

class MainActivity<Supermovie> : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SuperMovieAdapter
    private var supermovieList: List<Supermovie> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SuperMovieAdapter() {
            onItemClickListener(it)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        binding.progress.visibility = View.GONE
        binding.emptyPlaceholder.visibility = View.VISIBLE


        /*// Configuración del OnClickListener para el botón "Acceder"
        binding.accederButton.setOnClickListener {
            // Llamar a la función para buscar todas las películas
            searchSupermovie("")
        }*/


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        if (menuInflater == null || menu == null) {

            return false
        }
        menuInflater.inflate(R.menu.main_menu, menu)


        initSearchView(menu.findItem(R.id.menu_search))

        return true

    }

    private fun initSearchView(searchItem: MenuItem?) {
        if (searchItem != null) {
            var searchView =
                (searchItem.actionView as SearchView).also {

                    it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            searchSupermovie(query!!)
                            it.clearFocus()
                            return true
                        }

                        override fun onQueryTextChange(query: String?): Boolean {
                            return false
                        }
                    })
                }
        }
    }

    private fun onItemClickListener(position: Int) {
    val superMovieItem: Supermovie = supermovieList[position]

     val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ID, superMovieItem.imdbId)
        intent.putExtra(DetailActivity.EXTRA_NAME, superMovieItem.title)
        intent.putExtra(DetailActivity.EXTRA_IMAGE, superMovieItem.poster)
        startActivity(intent)
    }
































}