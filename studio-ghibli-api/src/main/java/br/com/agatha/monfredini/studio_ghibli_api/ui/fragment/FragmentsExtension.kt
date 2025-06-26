package br.com.agatha.monfredini.studioghibli.ui.fragment

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


var Fragment.quandoTelaFinalizada: () -> Unit
    get() = {}
    set(value) = Unit


fun Fragment.fechaFragement() {
    mostraErro("Filme não encontrado", activity?.baseContext!!)
    quandoTelaFinalizada()
}

private fun mostraErro(mensagem: String, context: Context) {
    Toast.makeText(
        context,
        mensagem,
        Toast.LENGTH_LONG
    ).show()
}
