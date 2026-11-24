package org.danielcastelao.dam

fun main() {
    mostrarSituacion("Arranque del Halo")
}

private fun mostrarSituacion(mensaje: String) {
    val nivel = gestor()
    val nombreEstado = estado_acual.javaClass.simpleName

    println("$mensaje → $nombreEstado, nivel $nivel")
}