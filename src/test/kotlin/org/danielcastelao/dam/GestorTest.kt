package org.danielcastelao.dam

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GestorTest {

    // limpia las variables entre pruebas
    @BeforeTest
    @AfterTest
    fun reiniciarAscensor() {
        estado_acual = Estados.Idle
        nivel_actual = 0
        boton = Botones.ninguno
        fallo = false
        llegada = false
    }

    @Test
    fun `al arrancar espera abajo`() {
        comprobarPaso(Estados.Idle, 0)
    }

    @Test
    fun `corrige un nivel imposible cuando esta parado`() {
        nivel_actual = 5

        comprobarPaso(Estados.Idle, 0)
    }

    @Test
    fun `conserva el nivel de arriba mientras espera`() {
        nivel_actual = 1

        comprobarPaso(Estados.Idle, 1)
    }

    @Test
    fun `puede subir y bajar en el mismo recorrido de prueba`() {
        boton = Botones.nivel_1
        comprobarPaso(Estados.MovingUP, 2)
        comprobarPaso(Estados.MovingUP, 2)

        llegada = true
        comprobarPaso(Estados.Idle, 1)

        boton = Botones.nivel_0
        comprobarPaso(Estados.MovingDOWN, 2)
        comprobarPaso(Estados.MovingDOWN, 2)

        llegada = true
        comprobarPaso(Estados.Idle, 0)
    }

    @Test
    fun `pulsar el mismo nivel deja el ascensor donde estaba`() {
        boton = Botones.nivel_0
        comprobarPaso(Estados.Idle, 0)

        nivel_actual = 1
        boton = Botones.nivel_1
        comprobarPaso(Estados.Idle, 1)
    }

    @Test
    fun `la subida deja el nivel en movimiento aunque tuviera otro valor`() {
        estado_acual = Estados.MovingUP
        nivel_actual = 5

        comprobarPaso(Estados.MovingUP, 2)
    }

    @Test
    fun `la bajada deja el nivel en movimiento aunque tuviera otro valor`() {
        estado_acual = Estados.MovingDOWN
        nivel_actual = 5

        comprobarPaso(Estados.MovingDOWN, 2)
    }

    @Test
    fun `una averia pone el nivel a menos uno`() {
        estado_acual = Estados.Error
        nivel_actual = 5

        comprobarPaso(Estados.Error, -1)
    }

    @Test
    fun `una pulsacion durante la subida se pierde al llegar`() {
        boton = Botones.nivel_1
        comprobarPaso(Estados.MovingUP, 2)

        boton = Botones.nivel_0
        comprobarPaso(Estados.MovingUP, 2)

        llegada = true
        comprobarPaso(Estados.Idle, 1)
        comprobarPaso(Estados.Idle, 1)
    }

    @Test
    fun `un aviso de llegada antiguo no termina el siguiente viaje`() {
        llegada = true
        comprobarPaso(Estados.Idle, 0)

        boton = Botones.nivel_1
        comprobarPaso(Estados.MovingUP, 2)
        comprobarPaso(Estados.MovingUP, 2)
    }

    @Test
    fun `un fallo en reposo impide arrancar`() {
        boton = Botones.nivel_1
        fallo = true

        comprobarPaso(Estados.Error, -1)
    }

    @Test
    fun `un fallo puede cortar la subida`() {
        boton = Botones.nivel_1
        comprobarPaso(Estados.MovingUP, 2)

        fallo = true
        comprobarPaso(Estados.Error, -1)
    }

    @Test
    fun `si coinciden la llegada y el fallo gana el fallo`() {
        nivel_actual = 1
        boton = Botones.nivel_0
        comprobarPaso(Estados.MovingDOWN, 2)

        llegada = true
        fallo = true
        comprobarPaso(Estados.Error, -1)
    }

    @Test
    fun `quitar el aviso de fallo no permite otro viaje`() {
        fallo = true
        comprobarPaso(Estados.Error, -1)

        fallo = false
        boton = Botones.nivel_1
        comprobarPaso(Estados.Error, -1)
    }

    // revisa el resultado y las variables
    private fun comprobarPaso(estadoEsperado: Estados, nivelEsperado: Int) {
        val nivelDevuelto = gestor()

        assertEquals(nivelEsperado, nivelDevuelto)
        assertEquals(nivelEsperado, nivel_actual)
        assertEquals(estadoEsperado, estado_acual)
    }
}