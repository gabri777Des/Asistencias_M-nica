package com.example.ESFE.Asistencias.Repositorios;

import com.example.ESFE.Asistencias.Entidades.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEstudianteRepository extends JpaRepository<Estudiante, Integer> {
}
