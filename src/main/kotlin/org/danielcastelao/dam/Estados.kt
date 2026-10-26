package org.danielcastelao.dam

sealed class Estados {

    // estado idle con funciones internas
    object Idle : Estados() {
        fun evaluarPeticion(destino: Int, nivelActual: Int): Estados {
            if (destino == nivelActual) return this
            return when (destino) {
                1 -> MovingUP
                0 -> MovingDOWN
                else -> Error
            }
        }

        fun describirUbicacion(nivel: Int): String {
            return when (nivel) {
                0 -> "mecanismo en reposo en nivel 0 (avda. garcía barbón)."
                1 -> "mecanismo en reposo en nivel 1 (plaza de compostela)."
                else -> "ubicación inconsistente para estado idle."
            }
        }
    }

    // estado movingup con funciones internas
    object MovingUP : Estados() {
        fun iniciarSubida(): Int {
            println("ascensor subiendo hacia nivel 1...")
            return 2 // Retorna 2 (en movimiento)
        }

        fun finalizarSubida(): Int {
            println("llegada completada a nivel 1.")
            return 1
        }
    }

    // estado movingdown con funciones internas
    object MovingDOWN : Estados() {
        fun iniciarBajada(): Int {
            println("ascensor bajando hacia nivel 0..")
            return 2 // Retorna 2 (en movimiento)
        }

        fun finalizarBajada(): Int {
            println("llegada completada a nivel 0.")
            return 0
        }
    }

    // estado error con funciones internas
    object Error : Estados() {
        fun reportarAveria(): String {
            return "alerta: mecanismo fuera de servicio. nivel asignado: -1."
        }

        fun obtenerNivelFallo(): Int = -1
    }
}
