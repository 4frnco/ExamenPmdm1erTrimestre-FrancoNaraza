package org.danielcastelao.dam

enum class Niveles(val numero: Int) {
    ABAJO(0),
    ARRIBA(1),
    MOVER(2)
}

enum class Botones {
    nivel_0,
    nivel_1,
    ninguno
}

// estado y nivel de un paso
data class Paso(val estado: Estados, val nivel: Int)

sealed class Estados {

    object Idle : Estados() {
        // solo sube si está abajo
        fun atenderBoton(pulsado: Botones, nivel: Int): Paso {
            if (pulsado == Botones.nivel_1 && nivel == Niveles.ABAJO.numero) {
                return MovingUP.empezarTrayecto()
            }

            return seguirEnReposo(nivel)
        }

        private fun seguirEnReposo(nivel: Int): Paso {
            val planta = if (nivel == Niveles.ARRIBA.numero) {
                Niveles.ARRIBA
            } else {
                Niveles.ABAJO
            }

            return Paso(Idle, planta.numero)
        }
    }

    object MovingUP : Estados() {
        fun empezarTrayecto(): Paso {
            return Paso(MovingUP, Niveles.MOVER.numero)
        }

        // sigue subiendo hasta llegar
        fun revisarSubida(finDeViaje: Boolean): Paso {
            if (finDeViaje) {
                return Paso(Idle, Niveles.ARRIBA.numero)
            }

            return Paso(MovingUP, Niveles.MOVER.numero)
        }
    }
}