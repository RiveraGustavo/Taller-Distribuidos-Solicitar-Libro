/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rmi.Client;

import com.rmi.RMI.RemoteInterface;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gustavo Rivera
 */
public class Client {

    public Client() {

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Client c = new Client();
        c.conectRemote();
    }

    private void conectRemote() {
        int a = 1;
        int b = 2;

        try {
            int nEnviar = 0;
            DataBase db = new DataBase();
            List<String> peticiones = db.leerFichero("peticiones.txt");
            String request = "";
            while (nEnviar < peticiones.size()) {

                //System.setProperty("java.rmi.server.hostname","192.168.5.102");
                //System.setSecurityManager(new RMISecurityManager());
                //Registry reg = LocateRegistry.getRegistry("localhost", 3333);
                //Registry reg = LocateRegistry.getRegistry("192.168.5.102", 3333);                
                //RemoteInterface remote = (RemoteInterface) reg.lookup("SOLICITAR");
                nEnviar++;

                RemoteInterface remote = (RemoteInterface) Naming.lookup("rmi://localhost:3333/SOLICITAR");
                System.out.println("RS: " + remote.solicitar(peticiones.get(nEnviar)));
            }

        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
