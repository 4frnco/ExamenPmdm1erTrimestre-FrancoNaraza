package org.danielcastelao.dam

enum class Niveles(val numero: Int) {
    ABAJO(0),
    ARRIBA(1)
}

// estado y nivel de un paso
data class Paso(val estado: Estados, val nivel: Int)

sealed class Estados {

    object Idle : Estados() {
        // conserva un nivel válido
        fun seguirEnReposo(nivel: Int): Paso {
            val planta = if (nivel == Niveles.ARRIBA.numero) {
                Niveles.ARRIBA
            } else {
                Niveles.ABAJO
            }

            return Paso(Idle, planta.numero)
        }
    }
}