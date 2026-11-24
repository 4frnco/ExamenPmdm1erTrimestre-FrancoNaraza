package org.danielcastelao.dam

var nivel_actual: Int = Niveles.ABAJO.numero
var estado_acual: Estados = Estados.Idle
var boton: Botones = Botones.ninguno
var llegada: Boolean = false

fun gestor(): Int {
    val pulsado = boton
    val finDeViaje = llegada

    // cada señal se usa una vez
    boton = Botones.ninguno
    llegada = false

    val paso = when (val estado = estado_acual) {
        is Estados.Idle -> estado.atenderBoton(pulsado, nivel_actual)
        is Estados.MovingUP -> estado.revisarSubida(finDeViaje)
        is Estados.MovingDOWN -> estado.revisarBajada(finDeViaje)
    }

    estado_acual = paso.estado
    nivel_actual = paso.nivel

    return nivel_actual
}