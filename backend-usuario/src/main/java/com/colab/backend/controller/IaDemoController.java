package com.colab.backend.controller;

import com.colab.backend.dto.IaDemoRequest;
import com.colab.backend.dto.IaDemoResponse;
import com.colab.backend.ia.PlanIAClient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Endpoint de prueba rápida para comprobar que la app puede obtener
 * respuestas de la IA, sin necesidad de frontend.
 */
@RestController
@RequestMapping("/api/ia")
public class IaDemoController {

    private final PlanIAClient planIAClient;
    private final String provider;

    public IaDemoController(PlanIAClient planIAClient,
                            @Value("${colab.ia.provider:mock}") String provider) {
        this.planIAClient = planIAClient;
        this.provider = provider;
    }

    @PostMapping("/demo")
    public IaDemoResponse demo(@Valid @RequestBody IaDemoRequest request) {
        String sistema = "Eres un asistente de pruebas del backend de Colab. Responde de forma breve y clara.";
        String respuesta = planIAClient.generarPlanificacionJson(sistema, request.mensaje(), List.of());
        return new IaDemoResponse(respuesta);
    }

    @GetMapping("/proveedor")
    public Map<String, String> proveedor() {
        return Map.of("proveedor", provider);
    }
}
