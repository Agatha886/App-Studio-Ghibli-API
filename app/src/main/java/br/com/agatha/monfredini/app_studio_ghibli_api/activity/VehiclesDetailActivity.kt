package br.com.agatha.monfredini.app_studio_ghibli_api.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.agatha.monfredini.app_studio_ghibli_api.databinding.ActivityCharacterDetailBinding
import br.com.agatha.monfredini.studio_ghibli_api.commons.LogsStudioGhibliApi.logInfo
import br.com.agatha.monfredini.studio_ghibli_api.model.Vehicle

class VehiclesDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val vehicle = intent.getSerializableExtra("vehicle") as? Vehicle
        logInfo("Vehicle ${vehicle?.name} = $vehicle")
        vehicle?.let {
            binding.etName.setText("Name : ${it.name}")
            binding.etGender.setText("Description: ${it.description}")
            binding.etAge.setText("Class: ${it.vehicle_class}")
            binding.etHairColor.setText("Length: ${it.length}")
            binding.etEyeColor.setText("Pilot Name: ${it.pilotCharacter?.name}")
        }
    }
}
