
package controladores;

import baseDatos.ManejadorBD;
import dominio.Cliente;
import dominio.Compra;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



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
        
        String sql2 = "insert into version_descargada "
                + "values("+c.getCliente().getId()+", "+c.getJuego().getId()+")";
        
        mbd.INSERT(sql);
        mbd.INSERT(sql2);
    }
    
    public void bajaCompra(Compra c) throws SQLException{
        String sql = "delete from compras where id_juego = " + c.getJuego().getId() + " and id_usuario =" + c.getCliente().getId();
        PreparedStatement ps = mbd.getConexion().prepareStatement(sql);
        ps.execute();
    }

    public ArrayList verComprasPorJuego(int id){
        try {
            ArrayList compras = new ArrayList();
            String sql = "select u.* from usuarios u, compras c, juegos j "+
                    "where j.borrado = 0 and j.id_juego = c.id_juego and "
                    + "c.id_juego = "+id+" and c.id_usuario = u.id_usuario";
            
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
    
    public Boolean comproJuego(int idUsuario, int idJuego){
        
        try {
            String sql = "SELECT * FROM compras c, juegos j where j.borrado = 0 and "
                    + "j.id_juego = c.id_juego and id_juego =" + idJuego + " and id_usuario = " + idUsuario + ";";
            ResultSet res = mbd.SELECT(sql);
            if  (res.next()) return true;
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCompras.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
