package org.danielcastelao.dam

var nivel_actual: Int = Niveles.ABAJO.numero
var estado_acual: Estados = Estados.Idle
var boton: Botones = Botones.ninguno
var fallo: Boolean = false
var llegada: Boolean = false

fun gestor(): Int {
    val pulsado = boton
    val finDeViaje = llegada

    // cada señal se usa una vez
    boton = Botones.ninguno
    llegada = false

    // el fallo tiene prioridad
    val paso = when {
        fallo -> Estados.Error.dejarFueraDeServicio()

        else -> when (val estado = estado_acual) {
            is Estados.Idle -> estado.atenderBoton(pulsado, nivel_actual)
            is Estados.MovingUP -> estado.revisarSubida(finDeViaje)
            is Estados.MovingDOWN -> estado.revisarBajada(finDeViaje)
            is Estados.Error -> estado.dejarFueraDeServicio()
        }
    }

    estado_acual = paso.estado
    nivel_actual = paso.nivel

    return nivel_actual
}