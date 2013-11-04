
package controladores;
import baseDatos.ManejadorBD;
import dominio.Categoria;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorCategorias {
    private static ControladorCategorias INSTANCIA = null;
    private ManejadorBD mbd = ManejadorBD.getInstancia();
    public static ControladorCategorias getInstancia(){
        if (INSTANCIA == null)
             INSTANCIA = new ControladorCategorias();
         return INSTANCIA;
    }

    private ControladorCategorias() {
        //mbd.conectar();
    }
    
    
    public int altaCategoria(Categoria c) throws SQLException{
        String sql = "insert into categorias (nombre, foto) values ('$1','$2')";
        sql = sql.replace("$1", c.getNombre());
        sql = sql.replace("$2", c.getImagen());
        System.out.println(sql);
        return mbd.INSERT(sql);
    }
    
    public ArrayList listarCategorias(){
        try {
            ArrayList categorias = new ArrayList();
            String consulta = "select id_categoria, nombre from categorias order by nombre";
            ResultSet res = mbd.SELECT(consulta);
            while(res.next()){
                Categoria cat = new Categoria();
                cat.setNombre(res.getString("nombre"));
                cat.setId(res.getInt("id_categoria"));
                categorias.add(cat);
            }
            
            return categorias;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList verCategoriasPorJuego(int id){
        try {
            ArrayList cats = new ArrayList();
            String sql = "select c.id_categoria, c.nombre from categorias c, categorias_juegos cj, "+
                        " juegos j where j.borrado = 0 and j.id_juego = cj.id_juego and "
                        + "cj.id_juego = "+id+" and c.id_categoria = cj.id_categoria";
            ResultSet res = mbd.SELECT(sql);
            
            while(res.next()){
                Categoria c = new Categoria();
                c.setId(res.getInt("id_categoria"));
                c.setNombre(res.getString("nombre"));
                cats.add(c);
            }
            return cats;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
}
