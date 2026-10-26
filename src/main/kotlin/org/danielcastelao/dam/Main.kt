package org.danielcastelao.dam

fun main() {
    println("--- Encendiendo mecanismo del Halo de Vigo ---")
    println("Estado inicial: $estado_acual, Nivel: $nivel_actual")

    println("\n- Presionando Botón Nivel 1 -")
    presionarBoton(1)

    println("\n- Presionando Botón Nivel 0 -")
    presionarBoton(0)

    println("\n- Provocando fallo en el sistema -")
    provocarFallo()

    println("\n- Reparando el ascensor -")
    reparar()
}
