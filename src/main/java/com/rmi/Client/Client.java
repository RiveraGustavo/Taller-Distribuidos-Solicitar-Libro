/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rmi.Client;

import com.rmi.RMI.RemoteInterface;
import com.rmi.Server.ServerImplements;
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

        try {
            int nEnviar = 0;
            DataBase db = new DataBase();
            List<String> peticiones = db.leerFichero("peticiones.txt");
            while (nEnviar < peticiones.size()) {

                //System.setProperty("java.rmi.server.hostname","192.168.5.102");
                Registry reg = LocateRegistry.getRegistry("10.0.4.87", 3333);
                //Registry reg = LocateRegistry.getRegistry("192.168.5.102", 3333);                
                RemoteInterface remote = (RemoteInterface) reg.lookup("SOLICITAR");
                //RemoteInterface remote = (RemoteInterface) Naming.lookup("rmi://192.168.0.5:3333/SOLICITAR");
                //RemoteInterface remote = (RemoteInterface) Naming.lookup("rmi://192.168.5.102:3333/SOLICITAR");
                System.out.println("RS: " + remote.solicitar(peticiones.get(nEnviar)));
                nEnviar++;
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
