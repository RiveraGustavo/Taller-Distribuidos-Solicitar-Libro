/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rmi.ActorSolicitar;

import com.rmi.RMI.RemoteInterface;
import com.rmi.controllers.LibroController;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Gustavo Rivera
 */
public class ServerImplementsActor extends UnicastRemoteObject implements RemoteInterface {

    public ServerImplementsActor() throws RemoteException {
        super();
    }

    @Override
    public String solicitar(String solicitud) throws Exception {
        Semaphore sem = new Semaphore(1);
        LibroController libroController = new LibroController("libros.txt", sem);
        StringTokenizer sscanf = new StringTokenizer(solicitud, " ");
        String peticion = sscanf.nextToken();
        String codigoLibro = sscanf.nextToken().toString();
        int idSolicitante = Integer.valueOf(sscanf.nextToken().toString());
        String fechaSolicitud = sscanf.nextToken().toString();
        String fechaFinalizacion = sscanf.nextToken().toString();
        Date dat1, dat2;
        SimpleDateFormat objSDF = new SimpleDateFormat("dd-MM-yyyy");
        dat1 = objSDF.parse(fechaSolicitud);
        dat2 = objSDF.parse(fechaFinalizacion);
        String estado = libroController.solicitarLibro(codigoLibro, idSolicitante, dat1, dat2, sem);
        return estado;
    }

}
