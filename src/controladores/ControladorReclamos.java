/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import baseDatos.ManejadorBD;
import dominio.Cliente;
import dominio.Reclamo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ControladorReclamos {

    private ManejadorBD mbd = ManejadorBD.getInstancia();
    private static ControladorReclamos INSTANCIA = null;

    public static ControladorReclamos getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new ControladorReclamos();
        }
        return INSTANCIA;
    }

    public void AltaReclamo(Reclamo r) throws SQLException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fecha = sdf.format(r.getFechaReclamo()); //fecha actual

        String sql = "Insert into reclamos(id_juego, categoria, id_usuario, version, fecha, texto) values (" + r.getIDJueg() + ",'"+r.getCatReclamo()+"'," + r.getIDCli() + ", '" + r.getVersionRecl() + "', '" + fecha + "', '" + r.getTxtReclamo() + "')";
        System.out.println(sql);
        mbd.INSERT(sql);
    }

    public ArrayList ConsultaReclamo(int idj) throws SQLException {
        ArrayList consulta = new ArrayList();
        String sql = " Select r.* "
                + " From reclamos r "
                + " Where r.id_juego=" + idj + " ";

        ResultSet res = mbd.SELECT(sql);
        while (res.next()) {
            Reclamo r = new Reclamo();
            r.setCli(res.getInt("id_usuario"));
            r.setCatRecl(res.getString("categoria"));
            r.setFechaRecl(res.getDate("fecha"));
            r.setJueg(res.getInt("id_juego"));
            r.setNroVersion(res.getString("version"));
            r.setTxtRecl(res.getString("texto"));

            consulta.add(r);
        }
        return consulta;
    }
}
