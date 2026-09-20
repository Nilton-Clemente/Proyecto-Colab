package com.colab.backend.repository;

import com.colab.backend.domain.DependenciaTarea;
import com.colab.backend.domain.DependenciaTareaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DependenciaTareaRepository extends JpaRepository<DependenciaTarea, DependenciaTareaId> {

    @Query("select d from DependenciaTarea d where d.tarea.etapa.planificacion.id = :planificacionId")
    List<DependenciaTarea> findByPlanificacionId(@Param("planificacionId") Long planificacionId);
}
