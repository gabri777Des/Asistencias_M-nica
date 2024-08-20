package com.example.ESFE.Asistencias.Servicios.Implementaciones;

import com.example.ESFE.Asistencias.Entidades.DocenteGrupo;
import com.example.ESFE.Asistencias.Repositorios.IDocenteGrupoRepository;
import com.example.ESFE.Asistencias.Servicios.Interfaces.IDocenteGrupoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocenteGrupoServices implements IDocenteGrupoServices {

    @Autowired
    private IDocenteGrupoRepository docenteGrupoRepository;

    @Override
    public Page<DocenteGrupo> BuscarTodosPaginados(Pageable pageable) {
        return docenteGrupoRepository.findByOrderByDocenteDesc(pageable);
    }

    @Override
    public List<DocenteGrupo> ObtenerTodos() {
        return docenteGrupoRepository.findAll();
    }

    @Override
    public Optional<DocenteGrupo> BuscarPorId(Integer id) {
        return docenteGrupoRepository.findById(id);
    }

    @Override
    public DocenteGrupo CrearOeditar(DocenteGrupo docenteGrupo) {
        return docenteGrupoRepository.save(docenteGrupo);
    }

    @Override
    public void EliminarPorId(Integer id) {
        docenteGrupoRepository.deleteById(id);
    }
}
