package org.danielcastelao.dam

// variables obligatorias suministradas por el profesor
var nivel_actual: Int = 0
var estado_acual: Estados = Estados.Idle

// variables adicionales para enriquecer la solucion
val nivel_0: String = "botón nivel 0 (avenida g. barbón)"
val nivel_1: String = "botón nivel 1 (plaza de compostela)"


 // gestiona el cambio de estados. Mantiene la firma exacta exigida por el profesor.

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


// procesa la pulsación de la botonera interior y ejecuta las transiciones.

fun presionarBoton(destino: Int) {
    when (val estado = estado_acual) {
        is Estados.Idle -> {
            if (destino != 0 && destino != 1) {
                provocarFallo()
                return
            }

            if (nivel_actual == destino) {
                println("el ascensor ya está en el nivel $destino.")
                return
            }

            estado_acual = estado.evaluarPeticion(destino, nivel_actual)
            ejecutarTransicion(destino)
        }
        is Estados.MovingUP, is Estados.MovingDOWN -> {
            println("petición ignorada: el ascensor está en movimiento.")
        }
        is Estados.Error -> {
            println(estado.reportarAveria())
        }
    }
}

private fun ejecutarTransicion(destino: Int) {
    when (val estado = estado_acual) {
        is Estados.MovingUP -> {
            nivel_actual = estado.iniciarSubida()
            nivel_actual = estado.finalizarSubida()
            estado_acual = Estados.Idle
        }
        is Estados.MovingDOWN -> {
            nivel_actual = estado.iniciarBajada()
            nivel_actual = estado.finalizarBajada()
            estado_acual = Estados.Idle
        }
        else -> {}
    }
}

fun provocarFallo() {
    estado_acual = Estados.Error
    nivel_actual = Estados.Error.obtenerNivelFallo()
    println(Estados.Error.reportarAveria())
}

fun reparar() {
    if (estado_acual is Estados.Error) {
        println("reajustando mecanismo a Nivel 0..")
        estado_acual = Estados.Idle
        nivel_actual = 0
    }
}
