package br.com.agatha.monfredini.app_studio_ghibli_api.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.agatha.monfredini.app_studio_ghibli_api.adapter.ListVehiclesAdapter
import br.com.agatha.monfredini.app_studio_ghibli_api.databinding.ActivityMovieDetailsBinding
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.VehiclesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class VehiclesListActivity : AppCompatActivity() {
    private val viewModel: VehiclesViewModel by viewModel()
    private lateinit var adapter: ListVehiclesAdapter
    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.movieTitle.text = "ALL VEHICLES"

        adapter = ListVehiclesAdapter { vehicle ->
            val intent = Intent(this, VehiclesDetailActivity::class.java)
            intent.putExtra("vehicle", vehicle)
            startActivity(intent)
        }

        binding.recyclerCharacters.layoutManager = LinearLayoutManager(this)
        binding.recyclerCharacters.adapter = adapter

        viewModel.vehiclesLiveData.observe(this) { characters ->
            adapter.submitList(characters)
        }

        binding.btnGetCharacters.setText("GET ALL VEHICLES")
        binding.btnGetCharacters.setOnClickListener {
            viewModel.getVehicles { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
