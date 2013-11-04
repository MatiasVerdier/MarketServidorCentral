
package controladores;

import baseDatos.ManejadorBD;
import dominio.Comentario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ControladorComentarios {
    private ManejadorBD mbd = ManejadorBD.getInstancia();
    private static ControladorComentarios INSTANCIA = null;
    
     public static ControladorComentarios getInstancia(){
        if (INSTANCIA == null)
             INSTANCIA = new ControladorComentarios();
         return INSTANCIA;
    }
    
        public ArrayList verComentariosJuego(int id) throws SQLException{
        ArrayList coments = new ArrayList();
        String sql = "select c.* from comentarios c, juegos j where "
                    + "j.borrado = 0 and j.id_juego = c.id_juego and c.id_juego = "+id;
        ResultSet res = mbd.SELECT(sql);
        while(res.next()){
            Comentario com = new Comentario();
            com.setId(res.getInt("id_comentario"));
            com.setTexto(res.getString("texto"));
            com.setId_juego(res.getInt("id_juego"));
            com.setFecha(res.getDate("fecha"));
            com.setId_usu(res.getInt("id_usuario"));
            com.setId_padre(res.getInt("id_padre"));
            coments.add(com);
        }

        return coments;
    }
    
    public ArrayList<Comentario> obtenerHijos(int idP){
         ArrayList coments = new ArrayList();
        try {
           
            String sql = "select * from comentarios where id_padre = "+idP;
            ResultSet res = mbd.SELECT(sql);
            while(res.next()){
                Comentario com = new Comentario();
                com.setId(res.getInt("id_comentario"));
                com.setTexto(res.getString("texto"));
                com.setId_juego(res.getInt("id_juego"));
                com.setFecha(res.getDate("fecha"));
                com.setId_usu(res.getInt("id_usuario"));
                com.setId_padre(res.getInt("id_padre"));
                coments.add(com);
            }

            
        } catch (SQLException ex) {
            Logger.getLogger(ControladorComentarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return coments;
    }
}
