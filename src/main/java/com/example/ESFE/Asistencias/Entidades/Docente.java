package com.example.ESFE.Asistencias.Entidades;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "docentes")

public class Docente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Ingrese el nombre del docente")
    private String nombre;

    @NotBlank(message = "Ingrese el apellido del docente")
    private String apellido;

    @Nullable
    private String email;

    @Nullable
    private Integer telefono;

    @Nullable
    private String escuela;

    @ManyToMany
    @JoinTable(
            name = "docentes_grupos",
            joinColumns = @JoinColumn(name = "docente_id"),
            inverseJoinColumns = @JoinColumn(name = "grupo_id")
    )
    private Set<Grupo> grupos = new HashSet<>();

    public Set<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(Set<Grupo> grupos) {
        this.grupos = grupos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotBlank(message = "Ingrese el nombre del docente") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank(message = "Ingrese el nombre del docente") String nombre) {
        this.nombre = nombre;
    }

    public @NotBlank(message = "Ingrese el apellido del docente") String getApellido() {
        return apellido;
    }

    public void setApellido(@NotBlank(message = "Ingrese el apellido del docente") String apellido) {
        this.apellido = apellido;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(@Nullable Integer telefono) {
        this.telefono = telefono;
    }

    @Nullable
    public String getEscuela() {
        return escuela;
    }

    public void setEscuela(@Nullable String escuela) {
        this.escuela = escuela;
    }
}
