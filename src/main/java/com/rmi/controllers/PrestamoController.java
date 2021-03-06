package com.rmi.controllers;

import com.rmi.models.Libro;
import com.rmi.models.Prestamo;
import com.rmi.repository.DataBase;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;



public class PrestamoController {

    private String arg;
    private DataBase data;
    

    public PrestamoController(String arg,Semaphore sem) {
        this.data = new DataBase(sem);
        this.arg = arg;
    }

    public PrestamoController(Semaphore sem) {
        this.data = new DataBase(sem);
    }

    public List<Prestamo> obtenerPrestamos() {
        List<Prestamo> prestamos = this.data.leerFicheroPrestamo(this.arg);
        return prestamos;
    }

    public Prestamo obtenerPrestamoById(int id) {
        List<Prestamo> prestamos = this.data.leerFicheroPrestamo(this.arg);
        Prestamo encontro = null;
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getIdSolicitud() == id) {
                encontro = prestamo;
            }
        }
        return encontro;
    }

    public boolean renovarPrestamo(int idSolicitud, Date fechaSolicitud, Date fechaDevolucion) {
        Prestamo prestamo = obtenerPrestamoById(idSolicitud);
        Boolean modificado = false;
        if (prestamo != null) {
            if (prestamo.getFechaSolicitud().before(fechaSolicitud)
                    && prestamo.getFechaFinalizacion().before(fechaDevolucion)) {
                prestamo.setFechaDevolucion(fechaDevolucion);
                prestamo.setFechaSolicitud(fechaSolicitud);
                prestamo.setFinalizado(false);
                modificado = this.data.modificarPrestamo(this.arg, prestamo);
            }
        }
        return modificado;
    }

    public boolean devolverPrestamo(int idSolicitud, Semaphore sem) {
        Prestamo prestamo = obtenerPrestamoById(idSolicitud);
        String codigo = prestamo.getCodigoLibro();
        Boolean modificado = false;
        LibroController libro = new LibroController("libros.txt",sem);
        Libro lib = libro.obtenerLibrosByCodigo(codigo);
        if (lib != null) {
            if (prestamo != null && !prestamo.getFinalizado()) {
                prestamo.setFinalizado(true);
                modificado = this.data.modificarPrestamo(this.arg, prestamo);
                libro.devolverLibro(lib);
            }
        }

        return modificado;
    }

    public boolean crearPrestamo(Prestamo nuevo) {
        return this.data.agregrar(this.arg, nuevo); 
    }
}