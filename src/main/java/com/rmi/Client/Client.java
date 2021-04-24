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
                Registry reg = LocateRegistry.getRegistry("localHost", 3333);
                RemoteInterface remote = (RemoteInterface) reg.lookup("SOLICITAR");
                System.out.println("RS: " + remote.solicitar(peticiones.get(nEnviar)));
                nEnviar++;
                //RemoteInterface stub=(RemoteInterface)Naming.lookup("rmi://localhost:1995/SOLICITAR");
                //System.out.println("SUMA: " + stub.add(a, b));
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
