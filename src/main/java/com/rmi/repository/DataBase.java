package com.rmi.repository;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.rmi.models.Libro;
import com.rmi.models.Prestamo;
import java.util.*;
import java.util.concurrent.Semaphore;

public class DataBase {

    Semaphore sem;

    public DataBase(Semaphore sem) {
        //this.sem = sem;
    }

    public boolean waitFile(String nameFile) throws InterruptedException {
        File file = new File(nameFile);
        boolean fileIsNotLocked = file.renameTo(file);
        while (!fileIsNotLocked) {
            Thread.sleep(100);
            fileIsNotLocked = file.renameTo(file);
        }
        return true;
    }

    public List<Libro> leerFicheroLibro(String arg) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        List<Libro> libros = new ArrayList<>();
        Libro libroNuevo;
        try {
            //sem.acquire();
            if (waitFile(arg)) {
                archivo = new File(arg);
                fr = new FileReader(archivo);
                br = new BufferedReader(fr);
                String linea;
                String[] textElements;
                while ((linea = br.readLine()) != null) {
                    textElements = linea.split(",");
                    libroNuevo = new Libro(Integer.valueOf(textElements[0]), textElements[1].toString(),
                            Integer.valueOf(textElements[2]), Integer.valueOf(textElements[3]), textElements[4].toString(),
                            textElements[5].toString());
                    libros.add(libroNuevo);
                }
                fr.close();
            }
            //sem.release();
        } catch (Exception e) {
            System.out.println("Error en DataBase por " + e.getMessage());
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                System.out.println("Error en DataBase Leer Fichero libro por " + e2.getMessage());
            }
            return libros;
        }
    }

    public List<Prestamo> leerFicheroPrestamo(String arg) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        List<Prestamo> prestamos = new ArrayList<>();
        Prestamo prestamo;
        try {
            if (waitFile(arg)) {
                archivo = new File(arg);
                if (archivo.exists()) {
                    fr = new FileReader(archivo);
                    br = new BufferedReader(fr);
                    String linea;
                    String[] textElements;
                    SimpleDateFormat objSDF = new SimpleDateFormat("dd-MM-yyyy");
                    Date dt_1;
                    Date dt_2;
                    while ((linea = br.readLine()) != null) {
                        if (linea.equals("")) {
                            break;
                        }
                        textElements = linea.split(",");
                        dt_1 = objSDF.parse(textElements[1].toString());
                        dt_2 = objSDF.parse(textElements[2].toString());
                        prestamo = new Prestamo(Integer.valueOf(textElements[0]), dt_1, dt_2,
                                Integer.valueOf(textElements[3]), textElements[4].toString(),
                                Boolean.parseBoolean(textElements[5]));
                        prestamos.add(prestamo);
                    }
                    fr.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Error en DataBase leer fichero prestamo por " + e.getMessage());
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                System.out.println("Error en DataBase fichero prestamo por" + e2.getMessage());
            }
            return prestamos;
        }
    }

    public Boolean modificarPrestamo(String arg, Prestamo prestamo) {
        String nFnuevo = "prestamosAux.txt";
        File fNuevo = new File(nFnuevo);
        File archivo = null;
        BufferedReader br;
        Boolean modifico = false;
        try {
            if (waitFile(arg)) {
                archivo = new File(arg);
                if (archivo.exists()) {
                    br = new BufferedReader(new FileReader(archivo));

                    Date fechaSolicitud = prestamo.getFechaSolicitud();
                    Date fechaFinalizacion = prestamo.getFechaFinalizacion();

                    int annioDate1 = fechaSolicitud.getYear() + 1900;
                    int annioDate2 = fechaFinalizacion.getYear() + 1900;
                    int mesDate1 = fechaSolicitud.getMonth() + 1;
                    int mesDate2 = fechaFinalizacion.getMonth() + 1;

                    String fechaInicial = fechaSolicitud.getDate() + "-" + mesDate1 + "-" + annioDate1;
                    String fechaFinal = fechaFinalizacion.getDate() + "-" + mesDate2 + "-" + annioDate2;

                    String insertar = prestamo.getIdSolicitud() + "," + fechaInicial + "," + fechaFinal + ","
                            + prestamo.getIdSolicitante() + "," + prestamo.getCodigoLibro() + ","
                            + prestamo.getFinalizado().toString();

                    String[] textElements;
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        textElements = linea.split(",");
                        if (Integer.parseInt(textElements[0]) == prestamo.getIdSolicitud()) {
                            escribir(fNuevo, insertar);
                        } else {
                            escribir(fNuevo, linea);
                        }
                    }
                    br.close();
                    borrar(archivo);
                    fNuevo.renameTo(archivo);
                    modifico = true;

                } else {
                    throw new Exception("Error en DataBase fichero prestamo por fichero no existe");
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return modifico;
    }

    public boolean modificar(String arg, Prestamo prestamo) {
        return true;
    }

    public Boolean modificarLibro(String arg, Libro libro) {
        String nFnuevo = "librosAux.txt";
        File fNuevo = new File(nFnuevo);
        File archivo = null;
        BufferedReader br;
        Boolean modifico = false;
        try {
            if (waitFile(arg)) {
                archivo = new File(arg);
                if (archivo.exists()) {
                    br = new BufferedReader(new FileReader(archivo));

                    String insertar = libro.getID() + "," + libro.getCodigo() + "," + libro.getUnidades() + ","
                            + libro.getUnidadesPrestadas() + ","
                            + libro.getTitulo() + "," + libro.getAutor();
                    String[] textElements;
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        textElements = linea.split(",");
                        if (Integer.parseInt(textElements[0]) == libro.getID()) {
                            escribir(fNuevo, insertar);

                        } else {
                            escribir(fNuevo, linea);
                        }
                    }
                    br.close();
                    borrar(archivo);
                    fNuevo.renameTo(archivo);
                    modifico = true;

                } else {
                    throw new Exception("Error en DataBase fichero prestamo por fichero no existe");
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return modifico;
    }

    /**
     * Puede retardar la escritura de un archivo
     *
     * @param fFichero
     * @param cadena
     */
    public void escribir(File fFichero, String cadena) {
        BufferedWriter bw;
        try {
            if (!fFichero.exists()) {
                fFichero.createNewFile();
            }
            bw = new BufferedWriter(new FileWriter(fFichero, true));
            bw.write(cadena);
            bw.newLine();
            bw.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void borrar(File Ffichero) {
        try {
            if (Ffichero.exists()) {
                Ffichero.delete();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean agregrar(String arg, Prestamo prestamo) {
        File archivo = null;
        BufferedReader br;
        Boolean modifico = false;
        try {
            if (waitFile(arg)) {
                archivo = new File(arg);
                if (archivo.exists()) {
                    br = new BufferedReader(new FileReader(archivo));

                    Date fechaSolicitud = prestamo.getFechaSolicitud();
                    Date fechaFinalizacion = prestamo.getFechaFinalizacion();

                    int annioDate1 = fechaSolicitud.getYear() + 1900;
                    int annioDate2 = fechaFinalizacion.getYear() + 1900;
                    int mesDate1 = fechaSolicitud.getMonth() + 1;
                    int mesDate2 = fechaFinalizacion.getMonth() + 1;

                    String fechaInicial = fechaSolicitud.getDate() + "-" + mesDate1 + "-" + annioDate1;
                    String fechaFinal = fechaFinalizacion.getDate() + "-" + mesDate2 + "-" + annioDate2;
                    String lastLine = "";
                    String sCurrentLine;

                    while ((sCurrentLine = br.readLine()) != null) {
                        lastLine = sCurrentLine;
                    }
                    String [] contenLastLine = lastLine.split(",");
                    int id = 1; 
                    if(contenLastLine.length > 0){
                        id = Integer.parseInt(contenLastLine[0])+1; 
                    }
                     
                    String insertar = String.valueOf(id) + "," + fechaInicial + "," + fechaFinal + ","
                            + prestamo.getIdSolicitante() + "," + prestamo.getCodigoLibro() + ","
                            + prestamo.getFinalizado().toString();

                    escribir(archivo, insertar);
                    br.close();
                    modifico = true;

                } else {
                    throw new Exception("Error en DataBase fichero prestamo por fichero no existe");
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return modifico;
    }
}
