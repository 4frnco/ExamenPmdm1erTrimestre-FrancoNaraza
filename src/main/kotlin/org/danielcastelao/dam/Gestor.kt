package org.danielcastelao.dam

var nivel_actual: Int = Niveles.ABAJO.numero
var estado_acual: Estados = Estados.Idle

fun gestor(): Int {
    val paso = Estados.Idle.seguirEnReposo(nivel_actual)

    estado_acual = paso.estado
    nivel_actual = paso.nivel

    return nivel_actual
}