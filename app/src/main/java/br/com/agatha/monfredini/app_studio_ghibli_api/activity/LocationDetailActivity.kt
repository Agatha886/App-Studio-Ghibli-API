package br.com.agatha.monfredini.app_studio_ghibli_api.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.agatha.monfredini.app_studio_ghibli_api.databinding.ActivityCharacterDetailBinding
import br.com.agatha.monfredini.studio_ghibli_api.commons.LogsStudioGhibliApi.logInfo
import br.com.agatha.monfredini.studio_ghibli_api.model.Location
import br.com.agatha.monfredini.studio_ghibli_api.viewmodel.LocationsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LocationDetailActivity : AppCompatActivity() {
    private val viewModel: LocationsViewModel by viewModel()
    private lateinit var binding: ActivityCharacterDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val location = intent.getSerializableExtra("location") as? Location
        logInfo("Location ${location?.imageName} = $location")
        location?.let {
            viewModel.residentsLiveData.observe(this) { list ->
                logInfo("Location ${location.imageName} Residents ${list}")
                binding.etEyeColor.setText("First Residents : ${list[0]?.imageName}")
            }
            viewModel.getResidents(location) { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
            binding.etName.setText("Name : ${it.imageName}")
            binding.etGender.setText("Climate : ${it.climate}")
            binding.etAge.setText("Terrain : ${it.terrain}")
            binding.etHairColor.setText("Surface Water : ${it.surface_water}")
        }
    }
}
