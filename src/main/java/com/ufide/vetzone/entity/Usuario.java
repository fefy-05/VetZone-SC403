package com.ufide.vetzone.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nombre;


    private String apellidos;


    private String correo;


    private String password;


    private Boolean activo = true;


    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;


    public Usuario() {
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getApellidos() {
        return apellidos;
    }


    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }


    public String getCorreo() {
        return correo;
    }


    public void setCorreo(String correo) {
        this.correo = correo;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public Boolean getActivo() {
        return activo;
    }


    public void setActivo(Boolean activo) {
        this.activo = activo;
    }


    public Rol getRol() {
        return rol;
    }


    public void setRol(Rol rol) {
        this.rol = rol;
    }

}