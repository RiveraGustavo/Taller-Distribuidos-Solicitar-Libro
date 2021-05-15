package com.rmi.Server;

import com.rmi.RMI.RemoteInterface;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Hello world!
 *
 */
public class GestorCarga
{
    public static void main( String[] args ) throws AccessException
    {
        try {
            Registry reg = LocateRegistry.createRegistry(3333);
            //Naming.rebind("rmi://192.168.0.5:3333/SOLICITAR", new ServerImplements());
            //Naming.rebind("rmi://localhost:3333/SOLICITAR", new ServerImplements());
            //RemoteInterface stub = (RemoteInterface) UnicastRemoteObject.exportObject(in, 0);
            reg.rebind("SOLICITAR", new ServerImplements());
            System.out.println("SERVER ON");
        } catch (RemoteException ex) {
            Logger.getLogger(GestorCarga.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}