package com.colab.backend.ia;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MockPlanIAClientTest {

    private final MockPlanIAClient cliente = new MockPlanIAClient();

    @Test
    void asignaLosCorreosRealesDelEquipo() {
        List<String> correos = List.of("creador@test.com", "integrante@test.com", "otro@test.com");

        String json = cliente.generarPlanificacionJson("sistema", "usuario", correos);

        assertThat(json).contains("creador@test.com");
        assertThat(json).contains("integrante@test.com");
        assertThat(json).contains("otro@test.com");
    }

    @Test
    void devuelveJsonConEtapasYTareas() {
        String json = cliente.generarPlanificacionJson("sistema", "usuario", List.of("a@test.com", "b@test.com"));

        assertThat(json).contains("\"etapas\"");
        assertThat(json).contains("\"tareas\"");
        assertThat(json).contains("\"nombre\"");
        assertThat(json).contains("\"duracionDias\"");
    }
}
