package br.com.agatha.monfredini.studioghibli.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.agatha.monfredini.studioghibli.R
import br.com.agatha.monfredini.studioghibli.ui.CHAVE_PERSONAGEM
import br.com.agatha.monfredini.studioghibli.ui.MENSAGEM_PERSONAGEM_INVALIDO
import br.com.agatha.monfredini.studioghibli.ui.model.Personagem
import kotlinx.android.synthetic.main.detalhes_personagem_fragment.*
import java.io.Serializable

class DetalhesPersonagemFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detalhes_personagem_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val personagem: Serializable by lazy {
            arguments?.getSerializable(CHAVE_PERSONAGEM) ?: throw IllegalArgumentException(
                MENSAGEM_PERSONAGEM_INVALIDO
            )
        }
        try {
            val personagemEncontrado = personagem as Personagem
            preencheCampos(personagemEncontrado)
        }catch (e: Exception){

        }
    }

    private fun preencheCampos(personagem: Personagem) {
        detalhes_personagens_nome.text = personagem.name
        detalhes_personagens_genero.text = personagem.gender
        detalhes_personagens_idade.text = personagem.age
        detalhes_personagens_olhos.text = personagem.eye_color
        detalhes_personagens_cabelo.text = personagem.hair_color
        detalhes_personagens_imagem.setImageResource(personagem.foto)
    }
}