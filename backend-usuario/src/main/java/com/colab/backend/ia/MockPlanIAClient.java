package com.colab.backend.ia;

/**
 * Implementación de pruebas de {@link PlanIAClient}: devuelve un JSON fijo de
 * ejemplo, sin depender de la red de TECSUP.
 */
public class MockPlanIAClient implements PlanIAClient {

    private static final String JSON_EJEMPLO = """
            {
              "etapas": [
                {
                  "nombre": "Análisis",
                  "orden": 1,
                  "tareas": [
                    {
                      "id": "t1",
                      "nombre": "Definir requerimientos",
                      "descripcion": "Redactar los RF del proyecto.",
                      "responsable": "correo.del.integrante@ejemplo.com",
                      "habilidadesRequeridas": ["Análisis", "Requerimientos"],
                      "prioridad": "ALTA",
                      "duracionDias": 2,
                      "dependencias": []
                    },
                    {
                      "id": "t2",
                      "nombre": "Validar requerimientos",
                      "descripcion": "Revisar los RF con el equipo.",
                      "responsable": "otro.integrante@ejemplo.com",
                      "habilidadesRequeridas": ["Análisis"],
                      "prioridad": "ALTA",
                      "duracionDias": 1,
                      "dependencias": ["t1"]
                    }
                  ]
                }
              ]
            }
            """;

    @Override
    public String generarPlanificacionJson(String promptSistema, String promptUsuario) {
        return JSON_EJEMPLO;
    }
}
