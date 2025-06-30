package br.com.agatha.monfredini.app_studio_ghibli_api.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.agatha.monfredini.app_studio_ghibli_api.adapter.ListLocationsAdapter
import br.com.agatha.monfredini.app_studio_ghibli_api.databinding.ActivityMovieDetailsBinding
import br.com.agatha.monfredini.studio_ghibli_api.commons.LogsStudioGhibliApi.logErro
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.LocationsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LocationsListActivity : AppCompatActivity() {
    private val viewModel: LocationsViewModel by viewModel()
    private lateinit var adapter: ListLocationsAdapter
    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.movieTitle.text = "ALL LOCATIONS"

        adapter = ListLocationsAdapter { location ->
            val intent = Intent(this, LocationDetailActivity::class.java)
            intent.putExtra("location", location)
            startActivity(intent)
        }

        binding.recyclerCharacters.layoutManager = LinearLayoutManager(this)
        binding.recyclerCharacters.adapter = adapter

        viewModel.locationLiveData.observe(this) { characters ->
            adapter.submitList(characters)
        }

        binding.btnGetCharacters.setText("GET ALL LOCATIONS")
        binding.btnGetCharacters.setOnClickListener {
            viewModel.getLocations { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
