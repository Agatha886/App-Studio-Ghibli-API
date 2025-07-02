package br.com.agatha.monfredini.app_studio_ghibli_api.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.agatha.monfredini.app_studio_ghibli_api.databinding.ActivityCharacterDetailBinding
import br.com.agatha.monfredini.studio_ghibli_api.commons.LogsStudioGhibliApi.logInfo
import br.com.agatha.monfredini.studio_ghibli_api.model.GhibliCharacter

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val character = intent.getSerializableExtra("character") as? GhibliCharacter
        logInfo("Character ${character?.imageName} = $character")
        character?.let {
            binding.etName.setText("Name : ${it.imageName}")
            binding.etGender.setText("Gender : ${it.gender}")
            binding.etAge.setText("Age : ${it.age}")
            binding.etHairColor.setText("Hair Color : ${it.hair_color}")
            binding.etEyeColor.setText("Eye Color : ${it.eye_color}")
        }
    }
}
