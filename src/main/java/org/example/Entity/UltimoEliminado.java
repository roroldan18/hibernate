package org.example.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ultimo_registro_eliminado")
public class UltimoEliminado {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "id_nombre")
    private int id_nombre;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name="telefono")
    private int telefono;
    @Column(name = "email")
    private String email;
    @Column(name = "fecha_eliminacion")
    private Date fecha_eliminacion;

    public UltimoEliminado() {
    }

    public UltimoEliminado(int id_nombre, String nombre, String apellido, int telefono, String email, Date fecha_eliminacion) {
        this.id_nombre = id_nombre;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.fecha_eliminacion = fecha_eliminacion;
    }

    @Override
    public String toString() {
        return "UltimoEliminado{" +
                "id=" + id +
                ", id_nombre=" + id_nombre +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono=" + telefono +
                ", email='" + email + '\'' +
                ", fecha_eliminacion=" + fecha_eliminacion +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_nombre() {
        return id_nombre;
    }

    public void setId_nombre(int id_nombre) {
        this.id_nombre = id_nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFecha_eliminacion() {
        return fecha_eliminacion;
    }

    public void setFecha_eliminacion(Date fecha_eliminacion) {
        this.fecha_eliminacion = fecha_eliminacion;
    }
}
