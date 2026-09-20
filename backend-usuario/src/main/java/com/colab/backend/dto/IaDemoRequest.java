package com.colab.backend.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Petición de la demo de IA: un único mensaje de usuario.
 */
public record IaDemoRequest(@NotBlank String mensaje) {}
