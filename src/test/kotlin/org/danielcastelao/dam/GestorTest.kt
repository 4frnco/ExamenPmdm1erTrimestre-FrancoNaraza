package org.danielcastelao.dam

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GestorTest {
    @AfterTest
    fun restaurarEstado() {
        estado_acual = Estados.Idle
        nivel_actual = 0
    }

    @Test
    fun `gestor reinicia el nivel cuando el estado es Idle`() {
        estado_acual = Estados.Idle
        nivel_actual = 5

        assertEquals(0, gestor())
        assertEquals(0, nivel_actual)
    }

    @Test
    fun `gestor establece el nivel cuando el estado es MovingUP`() {
        estado_acual = Estados.MovingUP
        nivel_actual = 5

        assertEquals(2, gestor())
        assertEquals(2, nivel_actual)
    }

    @Test
    fun `gestor establece el nivel cuando el estado es MovingDOWN`() {
        estado_acual = Estados.MovingDOWN
        nivel_actual = 5

        assertEquals(2, gestor())
        assertEquals(2, nivel_actual)
    }

    @Test
    fun `gestor establece el nivel de error cuando el estado es Error`() {
        estado_acual = Estados.Error
        nivel_actual = 5

        assertEquals(-1, gestor())
        assertEquals(-1, nivel_actual)
    }

    // limpia las señales antes de cada prueba
    @BeforeTest
    fun limpiarSenales() {
        boton = Botones.ninguno
        fallo = false
        llegada = false
    }

    // comprueba el reposo inicial
    @Test
    fun `al arrancar espera abajo`() {
        comprobarPaso(Estados.Idle, 0)
    }

    // conserva el piso de arriba en reposo
    @Test
    fun `conserva el nivel de arriba mientras espera`() {
        nivel_actual = 1

        comprobarPaso(Estados.Idle, 1)
    }

    // comprueba el movimiento y la llegada en los dos sentidos
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

    // ignora el botón del piso actual
    @Test
    fun `pulsar el mismo nivel deja el ascensor donde estaba`() {
        boton = Botones.nivel_0
        comprobarPaso(Estados.Idle, 0)

        nivel_actual = 1
        boton = Botones.nivel_1
        comprobarPaso(Estados.Idle, 1)
    }

    // descarta una pulsación durante el viaje
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

    // descarta una llegada recibida en reposo
    @Test
    fun `un aviso de llegada antiguo no termina el siguiente viaje`() {
        llegada = true
        comprobarPaso(Estados.Idle, 0)

        boton = Botones.nivel_1
        comprobarPaso(Estados.MovingUP, 2)
        comprobarPaso(Estados.MovingUP, 2)
    }

    // da prioridad al fallo sobre la pulsación
    @Test
    fun `un fallo en reposo impide arrancar`() {
        boton = Botones.nivel_1
        fallo = true

        comprobarPaso(Estados.Error, -1)
    }

    // corta el viaje durante la subida
    @Test
    fun `un fallo puede cortar la subida`() {
        boton = Botones.nivel_1
        comprobarPaso(Estados.MovingUP, 2)

        fallo = true
        comprobarPaso(Estados.Error, -1)
    }

    // da prioridad al fallo sobre la llegada
    @Test
    fun `si coinciden la llegada y el fallo gana el fallo`() {
        nivel_actual = 1
        boton = Botones.nivel_0
        comprobarPaso(Estados.MovingDOWN, 2)

        llegada = true
        fallo = true
        comprobarPaso(Estados.Error, -1)
    }

    // mantiene el ascensor fuera de servicio
    @Test
    fun `quitar el aviso de fallo no permite otro viaje`() {
        fallo = true
        comprobarPaso(Estados.Error, -1)

        fallo = false
        boton = Botones.nivel_1
        comprobarPaso(Estados.Error, -1)
    }

    // comprueba lo que devuelve y lo que guarda el gestor
    private fun comprobarPaso(estadoEsperado: Estados, nivelEsperado: Int) {
        val nivelDevuelto = gestor()

        assertEquals(nivelEsperado, nivelDevuelto)
        assertEquals(nivelEsperado, nivel_actual)
        assertEquals(estadoEsperado, estado_acual)
    }
}