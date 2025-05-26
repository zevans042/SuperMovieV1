package com.zevans.supermoviev1.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.supermoviev1.R
import com.example.supermoviev1.databinding.ActivityMainBinding
import com.zevans.supermoviev1.adapters.SuperMovieAdapter
import com.zevans.supermoviev1.data.SuperMovieServiceApi
import com.zevans.supermoviev1.data.Supermovie
import com.zevans.supermoviev1.utils.RetrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.HTTP

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SuperMovieAdapter
    private var supermovieList: List<Supermovie> = listOf()

    private lateinit var service: SuperMovieServiceApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar Retrofit service
        service = RetrofitProvider.getRetrofit()

        // Inicializar adapter pasándole la referencia correcta a onItemClickListener
        adapter = SuperMovieAdapter(onClickListener = ::onItemClickListener)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        binding.progress.visibility = View.GONE
        binding.emptyPlaceholder.visibility = View.VISIBLE
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
            val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

            searchView.setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchSupermovie(query ?: "")
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean = false
            })
        }
    }

    private fun onItemClickListener(position: Int) {
        val superMovieItem: Supermovie = supermovieList[position]

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ID, superMovieItem.imdbID)
        intent.putExtra(DetailActivity.EXTRA_NAME, superMovieItem.title)
        intent.putExtra(DetailActivity.EXTRA_IMAGE, superMovieItem.poster)
        startActivity(intent)
    }

    private fun searchSupermovie(query: String) {
        binding.progress.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Pasa el parámetro con nombre para que se entienda bien qué es cada uno
                val response = service.searchByName(query = query)

                runOnUiThread {
                    if (response.isSuccessful) {
                        val superMovieResponse = response.body()
                        if (superMovieResponse != null && superMovieResponse.response == "True") {
                            supermovieList = superMovieResponse.search
                            adapter.updateItems(supermovieList)

                            if (supermovieList.isNotEmpty()) {
                                binding.recyclerView.visibility = View.VISIBLE
                                binding.emptyPlaceholder.visibility = View.GONE
                            } else {
                                binding.recyclerView.visibility = View.GONE
                                binding.emptyPlaceholder.visibility = View.VISIBLE
                            }
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "No se encontraron resultados",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Hubo un error inesperado, vuelva a intentarlo más tarde",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    binding.progress.visibility = View.GONE
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        "Error de red o conexión con el servidor",
                        Toast.LENGTH_LONG
                    ).show()
                    binding.progress.visibility = View.GONE
                }
            }
        }
    }
}






























