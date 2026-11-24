package org.danielcastelao.dam

fun main() {
    mostrarSituacion("Arranque del Halo")

    // prueba la subida completa
    boton = Botones.nivel_1
    mostrarSituacion("Pulsamos el botón de arriba")
    mostrarSituacion("El ascensor sigue subiendo")

    llegada = true
    mostrarSituacion("Llegamos a la plaza de Compostela")

    // simula una avería al bajar
    boton = Botones.nivel_0
    mostrarSituacion("Pulsamos el botón de abajo")

    fallo = true
    mostrarSituacion("hjay una avería durante la bajada")

    fallo = false
    mostrarSituacion("El mecanismo sigue fuera de servicio")
}

private fun mostrarSituacion(mensaje: String) {
    val nivel = gestor()
    val nombreEstado = estado_acual.javaClass.simpleName

    println("$mensaje → $nombreEstado, nivel $nivel")
}