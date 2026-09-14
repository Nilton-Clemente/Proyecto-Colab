package com.colab.backend.dto;

import java.time.LocalDate;

public record ProyectoResponse(
        Long id,
        String nombre,
        String descripcion,
        String problematica,
        String objetivoGeneral,
        String objetivosEspecificos,
        String alcance,
        String restricciones,
        LocalDate fechaEntrega,
        String tipo
) {}
