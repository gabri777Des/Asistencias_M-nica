package com.example.ESFE.Asistencias.Servicios.Interfaces;

import com.example.ESFE.Asistencias.Entidades.Estudiante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IEstudianteServices {
    Page<Estudiante> BuscarTodosPaginados(Pageable pageable);
    List<Estudiante> ObtenerTodos();
    Optional<Estudiante> BuscarPorId(Integer id);
    Estudiante CrearOeditar(Estudiante estudiante);
    void EliminarPorId(Integer id);
}
