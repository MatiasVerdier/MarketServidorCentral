/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import baseDatos.ManejadorBD;
import dominio.Reclamo;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Alexandro
 */
public class ControladorReclamos {
    private ManejadorBD mbd = ManejadorBD.getInstancia();
    private static ControladorReclamos INSTANCIA = null;
    
    public static ControladorReclamos getInstancia(){
        if (INSTANCIA == null)
            INSTANCIA = new ControladorReclamos();
        return INSTANCIA;
    }
    
    public void AltaReclamo(Reclamo r) throws SQLException{
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fecha = sdf.format(r.getFechaReclamo()); //fecha actual
        
        String sql="Insert into reclamos (" +r.getIDJueg()+ "," +r.getCatReclamo()+ "," +r.getIDCli()+ ", " +r.getVersionRecl()+ ", " +fecha+ ", " +r.getTxtReclamo()+ ") " ;
        mbd.INSERT(sql);
    }
    
}
