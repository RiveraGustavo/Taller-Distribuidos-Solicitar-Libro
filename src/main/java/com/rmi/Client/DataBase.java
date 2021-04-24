package com.rmi.Client;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class DataBase {

    public DataBase() {

    }

    public List<String> leerFichero(String arg) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        List<String> peticiones = new ArrayList<>();
        String peticion;
        try {
            archivo = new File(arg);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            String linea;
            String[] textElements;
            while ((linea = br.readLine()) != null) {
                textElements = linea.split(",");
                peticion = textElements[0].toString() + " " + textElements[1].toString() + " "+textElements[2].toString(); 
                        
                peticiones.add(peticion);
            }
            fr.close();
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
            return peticiones;
        }
    }
    /**
     * Puede retardar la escritura de un archivo
     * 
     * @param fFichero
     * @param cadena
     */
    public void Escribir(File fFichero, String cadena) {
        BufferedWriter bw;
        try {
            if (!fFichero.exists()) {
                fFichero.createNewFile();
            }
            bw = new BufferedWriter(new FileWriter(fFichero, true));
            bw.write(cadena + "\n");
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
}