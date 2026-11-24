package org.danielcastelao.dam

fun main() {
    mostrarSituacion("Arranque del Halo")

    boton = Botones.nivel_1
    mostrarSituacion("Pulsamos el botón de arriba")
    mostrarSituacion("El ascensor sigue subiendo")

    llegada = true
    mostrarSituacion("Llegamos a la plaza de Compostela")

    boton = Botones.nivel_0
    mostrarSituacion("Pulsamos el botón de abajo")

    llegada = true
    mostrarSituacion("Llegamos a García Barbón")
}

private fun mostrarSituacion(mensaje: String) {
    val nivel = gestor()
    val nombreEstado = estado_acual.javaClass.simpleName

    println("$mensaje → $nombreEstado, nivel $nivel")
}