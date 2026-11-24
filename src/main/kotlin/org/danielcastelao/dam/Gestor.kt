package org.danielcastelao.dam

var nivel_actual: Int = Niveles.ABAJO.numero
var estado_acual: Estados = Estados.Idle
var boton: Botones = Botones.ninguno

fun gestor(): Int {
    val pulsado = boton

    // consume la pulsación
    boton = Botones.ninguno

    val paso = when (val estado = estado_acual) {
        is Estados.Idle -> estado.atenderBoton(pulsado, nivel_actual)
        is Estados.MovingUP -> estado.empezarTrayecto()
    }

    estado_acual = paso.estado
    nivel_actual = paso.nivel

    return nivel_actual
}