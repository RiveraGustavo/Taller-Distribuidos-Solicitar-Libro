/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rmi.Server;

import com.rmi.RMI.RemoteInterface;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Gustavo Rivera
 */
public class ServerImplements implements RemoteInterface {

    public ServerImplements() {

    }

    @Override
    public String solicitar(String solicitud) throws Exception {
        
        Registry reg = LocateRegistry.getRegistry("192.168.5.102", 3334);
        RemoteInterface remote = (RemoteInterface) reg.lookup("SOLICITAR");
        Date dateS = new Date(), dateF = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateS);
        cal.add(Calendar.DATE, 7);
        dateF = cal.getTime();

        int year = 1900 + dateS.getYear(), year2 = 1900 + dateF.getYear();
        int mes = dateS.getMonth() + 1, mes2 = dateF.getMonth() + 1;

        String fecha = dateS.getDate() + "-" + mes + "-" + year;
        String fecha2 = dateF.getDate() + "-" + mes2 + "-" + year2;
        String enviar = fecha + " " + fecha2;
        solicitud = solicitud +" "+enviar; 
        return remote.solicitar(solicitud);
    }
}
