package com.colab.backend.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ProyectoRequest(
        @NotBlank String nombre,
        String descripcion,
        String problematica,
        String objetivoGeneral,
        String objetivosEspecificos,
        String alcance,
        String restricciones,
        LocalDate fechaEntrega
) {}
