/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author Juan Carlos
 */
public class SessionManager {
    
    public static final long SESSION_DURATION = 60000;//ms = 1 minute 
    
    /**
     * Devuelve un nuevo valor de una sesi�n v�lida desde el instante de la petici�n
     * @return el instante de validez final de la sesi�n
     */
    public static String newSession(){
        String expires=null;
        
        Date f= new Date();
        TimeZone tz = TimeZone.getTimeZone("GMT+1");
        Date fecha = new Date(f.getTime() + tz.getDSTSavings()+SESSION_DURATION);
        SimpleDateFormat dt1 = new SimpleDateFormat("y-M-d-H-m-s");
        expires = dt1.format(fecha);
    
        return expires;
    }
}