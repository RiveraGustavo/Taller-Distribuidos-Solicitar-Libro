/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rmi.ActorSolicitar;

import com.rmi.Server.ServerImplements;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gustavo Rivera
 */
public class ActorSolicitar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws AlreadyBoundException {
        // TODO code application logic here
        Registry reg;
        try {
            reg = LocateRegistry.createRegistry(3334);
            reg.bind("SOLICITAR", new ServerImplementsActor());
        } catch (RemoteException ex) {
            Logger.getLogger(ActorSolicitar.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        System.out.println("SERVER ON");
    }
    
}
