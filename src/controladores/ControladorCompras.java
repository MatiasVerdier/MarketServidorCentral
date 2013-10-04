
package controladores;

import baseDatos.ManejadorBD;
import dominio.Cliente;
import dominio.Compra;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;



public class ControladorCompras {
    private static ControladorCompras INSTANCIA = null;
    private ManejadorBD mbd = ManejadorBD.getInstancia();
    
    private ControladorCompras() {
        //mbd.conectar();
    }
    
    public static ControladorCompras getInstancia(){
        if (INSTANCIA == null)
             INSTANCIA = new ControladorCompras();
         return INSTANCIA;
    }
    
    public void altaCompra(Compra c) throws SQLException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fecha = sdf.format(c.getFecha());
        
        String sql = "insert into compras values ("+c.getJuego().getId()+
                     ","+c.getCliente().getId()+", '"+fecha+"')";
        mbd.INSERT(sql);
    }

    public ArrayList verComprasPorJuego(int id){
        try {
            ArrayList compras = new ArrayList();
            String sql = "select u.* from usuarios u, compras c "+
                    "where c.id_juego = "+id+" and c.id_usuario = u.id_usuario";
            
            ResultSet res = mbd.SELECT(sql);
            
            while(res.next()){
                Cliente c = new Cliente();
                c.setId(res.getInt("id_usuario"));
                c.setNick(res.getString("nick"));
                compras.add(c);
            }
            
            return compras;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
}
