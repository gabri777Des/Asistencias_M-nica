package com.example.ESFE.Asistencias.Servicios.Interfaces;

import com.example.ESFE.Asistencias.Entidades.Grupo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IGrupoServices {
    Page<Grupo> BuscarTodosPaginados(Pageable pageable);
    List<Grupo> ObtenerTodos();
    Optional<Grupo> BuscarPorId(Integer id);
    Grupo CrearOeditar(Grupo grupo);
    void EliminarPorId(Integer id);

}
