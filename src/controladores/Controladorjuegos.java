
package controladores;

import baseDatos.ManejadorBD;
import dominio.Categoria;
import dominio.Comentario;
import dominio.Desarrollador;
import dominio.Juego;
import dominio.Version;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Controladorjuegos {
    
    private static Controladorjuegos INSTANCIA = null;
    private ManejadorBD mbd = ManejadorBD.getInstancia();
    private ControladorUsuarios cu = ControladorUsuarios.getInstancia();
    
    public static Controladorjuegos getInstancia(){
        if (INSTANCIA == null)
             INSTANCIA = new Controladorjuegos();
         return INSTANCIA;
    }

    private Controladorjuegos() {
        //mbd.conectar();
    }
    
    /*------------------------------------------------------*/
    public ArrayList <Juego> buscar(String busqueda) throws SQLException{
        String sql = "select distinct j.id_juego, j.nombre, j.foto "
                   + "from juegos j, categorias_juegos cj, categorias c, usuarios u " 
                   + "where j.id_juego = cj.id_juego and c.id_categoria = cj.id_categoria"
                   + " and j.id_desarrollador = u.id_usuario and "
                   + "(j.nombre like '%"+busqueda+"%' or c.nombre like '%"+busqueda+"%' "
                   + "or u.nick like '%"+busqueda+"%' or j.descripcion like '%"+busqueda+"%')";
        
        ResultSet res = mbd.SELECT(sql);
        ArrayList juegos = new ArrayList();
        while(res.next()){
//            Juego j = new Juego();
//            j.setId(res.getInt("id_juego"));
//            j.setNombre(res.getString("nombre"));
//            j.setPortada(res.getString("foto"));
            Juego j = this.verInfoBasica(res.getInt("id_juego"));
            juegos.add(j);
        }
        
        return juegos;
    }
    
    /*-------------------------------------------------------*/
    
    
    public Juego buscarJuegoPorID(int id) throws SQLException{
        ResultSet res = mbd.SELECT("select id_juego, nombre from juegos where id_juego = "+id);
        Juego j = new Juego();
        while (res.next()){
            j.setId(res.getInt("id_juego"));
            j.setNombre(res.getString("nombre"));
        }
        return j;
    }
    
    public int altaJuego(Juego juego, ArrayList cats) throws SQLException {
        int i=0;
        String sql ="insert into juegos (nombre, descripcion, size, precio, id_desarrollador, foto) "
                    + "values ('$1','$2',$3,$4,$5,'$6')";
        sql = sql.replace("$1", juego.getNombre());
        sql = sql.replace("$2", juego.getDescripcion());
        sql = sql.replace("$3", String.valueOf(juego.getSize()));
        sql = sql.replace("$4", String.valueOf(juego.getPrecio()));
        sql = sql.replace("$5", String.valueOf(juego.getDes().getId()));
        sql = sql.replace("$6", juego.getPortada());

        int idj = mbd.INSERT(sql);
        

        while(i< cats.size()){
         Categoria c = (Categoria)cats.get(i);
         mbd.INSERT("insert into categorias_juegos (id_juego, id_categoria) "
                 + "values ("+idj+", "+c.getId()+")");
         i++;
         
        }
        return idj;
    }
    
    public ArrayList listarJuegosPorCategoria(int id_cat) throws SQLException{
        ArrayList juegos = new ArrayList();
        String sql = "select j.id_juego from juegos j, categorias_juegos cj "+
                "where cj.id_categoria = "+id_cat+
                " and cj.id_juego = j.id_juego";

        ResultSet res = mbd.SELECT(sql);
        while(res.next()){
            Juego j = this.verInfoJuego(res.getInt("id_juego"));
            
            juegos.add(j);
        }

        return juegos;
    }
    
    public Juego verInfoJuego(int id) throws SQLException{
        Juego j = new Juego();
        String sql = "select j.*, u.nick from juegos j, usuarios u "+
                     "where j.id_desarrollador = u.id_usuario and j.id_juego ="+ id;
        ResultSet res = mbd.SELECT(sql);
        res.next();

        j.setId(res.getInt("id_juego"));
        j.setNombre(res.getString("nombre"));
        j.setDescripcion(res.getString("descripcion"));
        j.setPrecio(res.getDouble("precio"));
        j.setSize(res.getDouble("size"));
        j.setPortada(res.getString("foto"));
        j.setComentarios(ControladorComentarios.getInstancia().verComentariosJuego(j.getId()));
        j.setCategorias(ControladorCategorias.getInstancia().verCategoriasPorJuego(j.getId()));
        Desarrollador des = new Desarrollador();
        des.setNick(res.getString("nick"));
        j.setDes(des);
        
        ResultSet res2 = mbd.SELECT("select numero_version, size from versiones where id_juego = "+id
                                    +" and estado = 'aprobada'");
        ArrayList <Version> vers = new ArrayList<>();
        while(res2.next()){
            Version v = new Version();
            v.setId_juego((id));
            v.setNro_version(res2.getString("numero_version"));
            v.setSize(res2.getDouble("size"));
            vers.add(v);
        }
        //j.setVersiones(vers);

        return j;
    }
    
    public ArrayList listarJuegos() throws SQLException{
        ArrayList juegos = new ArrayList();
        String sql = "select id_juego, nombre from juegos";
        ResultSet res = mbd.SELECT(sql);
        while(res.next()){
            Juego j = new Juego();
            j.setId(res.getInt("id_juego"));
            j.setNombre(res.getString("nombre"));
            juegos.add(j);
        }

        return juegos;
    }
    
    public ArrayList listarJuegosConCompras() throws SQLException{
        ArrayList juegos = new ArrayList();

        String sql = "select id_juego, nombre from juegos where id_juego in "+
                     "(select id_juego from compras)";

        ResultSet res = mbd.SELECT(sql);
        while(res.next()){
            Juego j = new Juego();
            j.setId(res.getInt("id_juego"));
            j.setNombre(res.getString("nombre"));
            juegos.add(j);
        }

        return juegos;
    } 
    
    public int altaComentario(Comentario c) throws SQLException{
        String sql = "insert into comentarios (id_juego, texto, fecha, id_usuario, id_padre) "+
                         " values ($1,'$2','$3',$4,$5)";
        
        Date fecha = new Date(c.getFecha().getTime());
        
        sql = sql.replace("$1", String.valueOf(c.getId_juego()));
        sql = sql.replace("$2", c.getTexto());
        sql = sql.replace("$3", fecha.toString());
        sql = sql.replace("$4", String.valueOf(c.getId_usu()));
        sql = sql.replace("$5", String.valueOf(c.getId_padre()));
        
        return mbd.INSERT(sql);
    }
    
    public ArrayList listarJuegosPorCliente(int id_usuario) throws SQLException{
        ArrayList juegos = new ArrayList();
        String sql = "select j.id_juego, j.nombre, j.foto, c.id_usuario from juegos j, compras c "+
                "where c.id_usuario = "+id_usuario+
                " and c.id_juego = j.id_juego";

        ResultSet res = mbd.SELECT(sql);
        while(res.next()){
            Juego j = new Juego();
            j.setNombre(res.getString("nombre"));
            j.setId(res.getInt("id_juego"));
            j.setPortada(res.getString("foto"));
            juegos.add(j);
        }

        return juegos;
    }
    
    public ArrayList listarJuegosPorDesarrollador(int id_usuario) throws SQLException{
        ArrayList <Juego> juegos = new ArrayList<>();
        String sql = "select id_juego, nombre from juegos "+
                     "where id_desarrollador = "+id_usuario;

        ResultSet res = mbd.SELECT(sql);
        while(res.next()){
            Juego j = Controladorjuegos.getInstancia().verInfoJuego(res.getInt("id_juego"));
            
            juegos.add(j);
        }

        return juegos;
    }

    public ArrayList listarJuegosPorDesarrolladorVersionAprobada(int id_usuario) throws SQLException{
        ArrayList <Juego> juegos = new ArrayList();
        String sql = "select Distinct(j.id_juego) from juegos j, versiones v " +
                        "where id_desarrollador = " + id_usuario + " AND  v.estado = 'aprobada' and j.id_juego = v.id_juego;";

        ResultSet res = mbd.SELECT(sql);
        while(res.next()){
            Juego j = Controladorjuegos.getInstancia().verInfoJuego(res.getInt("id_juego"));
            
            juegos.add(j);
        }

        return juegos;
    }
    /*--------------- OBTENER INFO BASICA DEL JUEGO PARA LISTAR ----------------*/
    public Juego verInfoBasica(int id) throws SQLException{
        String sql = "select j.*, u.nick from juegos j, usuarios u where id_juego = "+id
                      +" and j.id_desarrollador = u.id_usuario";
        
        //System.out.println("consulta ver info basica: "+sql);
        
        ResultSet res = mbd.SELECT(sql);
        Juego j = new Juego();
        while (res.next()){
            j.setId(id);
            j.setNombre(res.getString("nombre"));
            j.setPrecio(res.getDouble("precio"));
            j.setPortada(res.getString("foto"));
            j.setDescripcion(res.getString("descripcion"));
            j.setSize(res.getDouble("size"));
            j.setDes((Desarrollador)cu.find(res.getString("nick")));
            //System.out.println(j.getNombre());
        }
        return j;
    }
    /*----------------- OBTENER LOS 'X' JUEGOS MAS COMPRADOS ---------------------*/
    public ArrayList listarMasComprados(int cant) throws SQLException{
        String sql = "select id_juego, count(*)as cant from compras"
                    + " group by id_juego order by cant DESC limit "+cant;
        
        //System.out.println("consulta mas comprados: "+sql);
        
        ResultSet res = mbd.SELECT(sql);
        ArrayList juegos = new ArrayList();
        while (res.next()){
            Juego j = this.verInfoBasica(res.getInt("id_juego"));
            juegos.add(j);
        }
        
        return juegos;
    }
    
    public ArrayList listarMasComentados(int cant) throws SQLException{
        String sql = "select id_juego, count(*)as cant from comentarios"
                    + " group by id_juego order by cant DESC limit "+cant;
        
        //System.out.println("consulta mas comentados: "+sql);
        
        ResultSet res = mbd.SELECT(sql);
        ArrayList juegos = new ArrayList();
        while (res.next()){
            Juego j = this.verInfoBasica(res.getInt("id_juego"));
            juegos.add(j);
        }
        
        return juegos;
    }
    
    public void bajaJuego(int idJ) throws SQLException{
        
        /*String sql1 = " Delete From comentarios c, respuestas r, respuestas_cometarios rc "
                    + " Where c.id_comentario = rc.id_comentario and rc.id_comentario = r.id_com ";
        String sql2 = " Delete From juegos j, versiones v, versiones_rechazadas vr "
                    + " Where  j.id_juego = "+idJ+" v.orden_de_alta = "+ordenAlta+" and j.j.id_juego = v.id_juego "
                    + " and v.id_juego = vr.id_juego "; */
        try {
            String sql = "Delete From compras Where id_juego = "+idJ+" ";
               mbd.UPDATE(sql);
                   sql = " Delete From comentarios Where id_juego="+idJ+"";
               mbd.UPDATE(sql);
                   sql = " Delete From versiones_rechazadas Where id_juego="+idJ+"";
               mbd.UPDATE(sql);
                   sql = " Delete From versiones Where id_juego="+idJ+"";
               mbd.UPDATE(sql);
                   sql = " Delete From juegos Where id_juego="+idJ+"";
               mbd.UPDATE(sql);
                   
        } catch (SQLException ex) {
            throw ex;
        }
    
    }
}
