
package controladores;

import baseDatos.ManejadorBD;

import dominio.Version;
import java.io.FileInputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorVersiones {
    
    private static ControladorVersiones INSTANCIA = null;
    private ManejadorBD mbd = ManejadorBD.getInstancia();
    private Controladorjuegos cj = Controladorjuegos.getInstancia();
    
    public static ControladorVersiones getInstancia(){
        if (INSTANCIA == null)
             INSTANCIA = new ControladorVersiones();
         return INSTANCIA;
    }
    
    public ArrayList listarVersiones(int id_juego, char filtro) throws SQLException{
        String sql = "select * from versiones v, juegos j where j.borrado = 0 and"
                + " j.id_juego = v.id_juego and v.id_juego = "+id_juego;
        if (filtro == 'a'){
            sql += " and estado = 'aprobada'";
        }
        else if(filtro == 'p'){
            sql += " and estado = 'pendiente'";
        }
        else if (filtro == 'r'){
            sql += " and estado = 'rechazada'";
        }
           
        
        ResultSet res = mbd.SELECT(sql);
        ArrayList <Version> versiones = new ArrayList<>();
        while (res.next()){
            Version v = new Version();
            v.setId_juego((id_juego));
            v.setNro_version(res.getString("numero_version"));
            v.setSize(res.getDouble("size"));
            //v.setJar(res.getString("jar"));
            v.setEstado(res.getString("estado"));
            v.setFecha_alta(res.getDate("fecha_alta"));
            v.setOrden_alta(res.getInt("orden_de_alta"));
            v.setJuego(cj.buscarJuegoPorID(id_juego));
            
            if (v.getEstado().equals("rechazada")){
                String sql2 = "select motivo from versiones_rechazadas v, juegos j "
                        + "where j.borrado = 0 and v.id_juego = j.id_juego and v.id_juego = "+id_juego
                        +" and numero_version = '"+v.getNro_version()+"'";
                
                ResultSet res2 = mbd.SELECT(sql2);
                res2.next();
                if (res2.getString("motivo") !=null)
                v.setMotivo_recahazo(res2.getString("motivo"));
                else
                    v.setMotivo_recahazo(null);
            }
            versiones.add(v);
        }
        return versiones;
    }
    
    public void altaversion(Version v) throws SQLException{
        try {
            String sql = "INSERT INTO versiones (`id_juego`,`orden_de_alta`,`numero_version`,`size`,`jar`, " +
                        "`estado`, `fecha_alta`,`extension`)" +
                        "VALUES (?,?,?,?,?,?,?,?);";
            
            v.setOrden_alta(getOrdenDeAlta(v.getId_juego()));
//            sql = sql.replace("$1", String.valueOf(v.getId_juego()));
//            sql = sql.replace("$2", String.valueOf(v.getOrden_alta()));
//            sql = sql.replace("$3", (v.getNro_version()));
//            sql = sql.replace("$4", String.valueOf(v.getSize()));
            
//            sql = sql.replace("$6", v.getEstado());
            PreparedStatement ps = mbd.getConexion().prepareStatement(sql);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = sdf.format(v.getFecha_alta());
             
//            sql = sql.replace("$7", fecha);
            ps.setInt(1, v.getId_juego());
            ps.setInt(2, v.getOrden_alta());
            ps.setString(3, v.getNro_version());
            ps.setDouble(4, v.getSize());
            ps.setBinaryStream(5, v.getJarFi());
            ps.setString(6, v.getEstado());
            ps.setDate(7, Date.valueOf(fecha));
            ps.setString(8, v.getExtension());
            ps.executeUpdate();
//            mbd.INSERT(sql);
        } catch (SQLException ex) {
            throw ex;
        }

    }
    
    public ArrayList verInfoVer() throws SQLException{
        String sql = "select u.nick, j.nombre, v.numero_version, v.orden_de_alta, v.id_juego, v.size, v.fecha_alta"
                + " from versiones v, juegos j, usuarios u where j.borrado = 0 and j.id_juego=v.id_juego and "
            + "j.id_desarrollador=u.id_usuario and v.estado='pendiente' order by id_juego";
        ResultSet res = mbd.SELECT(sql);
        ArrayList versiones = new ArrayList();
         while(res.next())
         {
         //Version v= datosVersion(res.getInt("j.id_juego"), res.getInt("orden_de_alta"));
             Version v = new Version();
             v.setJuego(cj.verInfoJuego(res.getInt("id_juego")));
             v.setSize(res.getDouble("size"));
             v.setFecha_alta(res.getDate("fecha_alta"));
             v.setNro_version(res.getString("numero_version"));
             v.setOrden_alta(res.getInt("orden_de_alta"));
             versiones.add(v);
         }
         return versiones;
        }
    
    public void CambiarEstado(String estado, int idj, int orden, String motivo) throws SQLException{
        try {
            String sql;
            sql="UPDATE versiones SET estado='"+estado+"' where id_juego="+idj+" and orden_de_alta="+orden+" ";
            System.out.println(sql);
            mbd.UPDATE(sql);
            if(estado.equals("rechazada")){
                sql="insert into versiones_rechazadas " + 
                "values(" + idj + ", " + orden + ", '" + motivo + "')";
                mbd.INSERT(sql);
            }
             
            
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public void bajaVersion(int idJ, int ordenAlta) throws SQLException{
        try {
            String sql = "DELETE FROM versiones" +
                           " WHERE id_juego = " + idJ 
                    + " and orden_de_alta = " +
                    ordenAlta +";";
               
               PreparedStatement ps = mbd.getConexion().prepareStatement(sql);
               ps.execute();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    private int getOrdenDeAlta(int idJuego ){
        int ordenA = 1;
        try {
            String sql = "SELECT max(orden_de_alta) as orden FROM versiones where id_juego = " + idJuego;
            
            ResultSet res;
            
            res = mbd.SELECT(sql);
            
             while(res.next()){
                ordenA = (res.getInt("orden")+1);
            
        }
             
                     

            
        } catch (SQLException ex) {
            Logger.getLogger(ControladorVersiones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ordenA;

    }
    
    public Version ultimaVerAprobada(int idJuego){
        Version v = new Version();
        try {
           
            String sql = "SELECT * FROM versiones v, juegos j where j.borrado = 0 and "
                       + " j.id_juego = v.id_juego and v.id_juego = " + idJuego + " and v.estado = 'aprobada' and " + 
                        "v.orden_de_alta = (select max(orden_de_alta) from versiones where id_juego = " + idJuego
                        + " and estado = 'aprobada')";
        
            ResultSet res = mbd.SELECT(sql);
            
           res.next();
                v.setId_juego((idJuego));
                v.setNro_version(res.getString("numero_version"));
                v.setSize(res.getDouble("size"));
                //v.setJar((FileInputStream) res.getBinaryStream(5));
                v.setJarIs(res.getBinaryStream("jar"));
                v.setExtension(res.getString("extension"));
                v.setEstado(res.getString("estado"));
                v.setFecha_alta(res.getDate("fecha_alta"));
                v.setOrden_alta(res.getInt("orden_de_alta"));
                v.setJuego(cj.buscarJuegoPorID(idJuego));
                
               
            
          
        } catch (SQLException ex) {
            Logger.getLogger(ControladorVersiones.class.getName()).log(Level.SEVERE, null, ex);
        }
          return v;
    }
    
}
