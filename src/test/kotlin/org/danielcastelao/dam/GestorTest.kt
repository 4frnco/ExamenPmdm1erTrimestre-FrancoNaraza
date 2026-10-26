package org.danielcastelao.dam

import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GestorTest {

    @AfterTest
    fun restaurarEstado() {
        estado_acual = Estados.Idle
        nivel_actual = 0
    }

    // --- TESTS DEL PROFESOR (INTACTOS) ---

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

    // - tests complementarios -

    @Test
    fun `transicion exitosa de subida de Nivel 0 a Nivel 1`() {
        nivel_actual = 0
        estado_acual = Estados.Idle

        presionarBoton(1)

        assertEquals(1, nivel_actual)
        assertEquals(Estados.Idle, estado_acual)
    }

    @Test
    fun `transicion exitosa de bajada de nivel 1 a nivel 0`() {
        nivel_actual = 1
        estado_acual = Estados.Idle

        presionarBoton(0)

        assertEquals(0, nivel_actual)
        assertEquals(Estados.Idle, estado_acual)
    }

    @Test
    fun `boton con nivel invalido cambia estado a error`() {
        presionarBoton(99)

        assertEquals(Estados.Error, estado_acual)
        assertEquals(-1, nivel_actual)
    }

    @Test
    fun `funciones internas de los estados ejecutan correctamente`() {
        val proximoEstado = Estados.Idle.evaluarPeticion(1, 0)
        assertEquals(Estados.MovingUP, proximoEstado)

        assertEquals(2, Estados.MovingUP.iniciarSubida())
        assertEquals(1, Estados.MovingUP.finalizarSubida())
        assertEquals(-1, Estados.Error.obtenerNivelFallo())
    }

    @Test
    fun `reparar restaura el sistema a Idle y nivel 0`() {
        provocarFallo()
        reparar()

        assertEquals(Estados.Idle, estado_acual)
        assertEquals(0, nivel_actual)
    }
}
