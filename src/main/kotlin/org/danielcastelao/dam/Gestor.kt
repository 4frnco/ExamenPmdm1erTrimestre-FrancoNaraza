package org.danielcastelao.dam

// variables obligatorias suministradas por el profesor
var nivel_actual: Int = 0 // 0: gbarbón, 1: pcompostela, 2: movimiento, -1: Error
var estado_acual: Estados = Estados.Idle // Errata del profesor respetada literalmente

// tipos de variables adicionales para cumplir variedad de tipos de la rúbrica
val nivel_0: String = "Botón Nivel 0 (avenida g. barbón)"
val nivel_1: String = "Botón Nivel 1 (plaza de compostela)"


// gestiona el cambio de estados asignando y retornando el nivel actual.

fun gestor(): Int {
    when (estado_acual) {
        is Estados.Idle -> {
            nivel_actual = 0
            return nivel_actual
        }
        is Estados.MovingUP -> {
            nivel_actual = 2
            return nivel_actual
        }
        is Estados.MovingDOWN -> {
            nivel_actual = 2
            return nivel_actual
        }
        is Estados.Error -> {
            nivel_actual = -1
            return nivel_actual
        }
    }
}