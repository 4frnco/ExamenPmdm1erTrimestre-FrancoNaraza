package org.danielcastelao.dam

enum class Niveles(val numero: Int) {
    ABAJO(0),
    ARRIBA(1),
    MOVER(2),
    AVERIA(-1)
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
        // arranca si se pide el otro nivel
        fun atenderBoton(pulsado: Botones, nivel: Int): Paso {
            return when {
                pulsado == Botones.nivel_1 && nivel == Niveles.ABAJO.numero ->
                    MovingUP.empezarTrayecto()

                pulsado == Botones.nivel_0 && nivel == Niveles.ARRIBA.numero ->
                    MovingDOWN.empezarTrayecto()

                else -> seguirEnReposo(nivel)
            }
        }

        // corrige un nivel inválido a cero
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

        fun revisarSubida(finDeViaje: Boolean): Paso {
            if (finDeViaje) {
                return Paso(Idle, Niveles.ARRIBA.numero)
            }

            return Paso(MovingUP, Niveles.MOVER.numero)
        }
    }

    object MovingDOWN : Estados() {
        fun empezarTrayecto(): Paso {
            return Paso(MovingDOWN, Niveles.MOVER.numero)
        }

        fun revisarBajada(finDeViaje: Boolean): Paso {
            if (finDeViaje) {
                return Paso(Idle, Niveles.ABAJO.numero)
            }

            return Paso(MovingDOWN, Niveles.MOVER.numero)
        }
    }

    object Error : Estados() {
        fun dejarFueraDeServicio(): Paso {
            return Paso(Error, Niveles.AVERIA.numero)
        }
    }
}