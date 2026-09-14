package com.colab.backend.ia;

/**
 * Cliente para generar la propuesta de planificación con un LLM.
 *
 * <p>La implementación real llama al servicio OpenWebUI de TECSUP ("La BestIA");
 * el mock devuelve un JSON de ejemplo, para poder desarrollar y probar sin la red
 * del instituto.</p>
 */
public interface PlanIAClient {

    /**
     * Genera la planificación y la devuelve como texto JSON (aún sin validar).
     *
     * @param promptSistema instrucciones de sistema (rol y reglas de salida).
     * @param promptUsuario información del proyecto y del equipo.
     * @return JSON con etapas, tareas, responsables, prioridades, dependencias y duraciones.
     */
    String generarPlanificacionJson(String promptSistema, String promptUsuario);
}
