package br.com.agatha.monfredini.app_studio_ghibli_api.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.agatha.monfredini.app_studio_ghibli_api.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMovies.setOnClickListener {
            startActivity(Intent(this, MoviesListActivity::class.java))
        }

        binding.btnCharacters.setOnClickListener {
            startActivity(Intent(this, AllCharactersActivity::class.java))
        }

        binding.btnVehicles.setOnClickListener {
//            startActivity(Intent(this, VehiclesListActivity::class.java))
        }

        binding.btnLocations.setOnClickListener {
//            startActivity(Intent(this, LocationsListActivity::class.java))
        }
    }
}
