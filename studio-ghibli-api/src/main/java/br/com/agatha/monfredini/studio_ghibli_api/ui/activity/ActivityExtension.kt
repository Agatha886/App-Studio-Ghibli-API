package br.com.agatha.monfredini.studioghibli.ui.activity

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction

// Método da Activity
fun AppCompatActivity.transacaoFragment(executa: FragmentTransaction.() -> Unit) {
    val transacao = supportFragmentManager.beginTransaction()
    executa(transacao)
    transacao.commit()
}

fun AppCompatActivity.alertDialog(mensagem: String) {
    AlertDialog
        .Builder(this)
        .setMessage(mensagem)
        .setNegativeButton("Ok", null)
        .show()
}