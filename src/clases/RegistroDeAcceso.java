package clases;

import baseDatos.ManejadorBD;
import dominio.RegistroAcceso;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegistroDeAcceso {
    
    public ArrayList mostrarAccesos() throws SQLException{
        ResultSet res = ManejadorBD.getInstancia().SELECT("select * from registro_acceso");
        ArrayList registros = new ArrayList();
        while (res.next()){
            RegistroAcceso r = new RegistroAcceso();
            r.setId(res.getInt("id"));
            r.setSo(res.getString("so"));
            r.setNav(res.getString("navegador"));
            r.setUrl(res.getString("url"));
            r.setIp(res.getString("ip"));
            r.setFecha(res.getString("fecha"));
            
            registros.add(r);
        }
        return registros;
    }
}
