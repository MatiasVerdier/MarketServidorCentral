package test;


import baseDatos.ManejadorBD;
import controladores.ControladorVersiones;
import controladores.Controladorjuegos;
import dominio.Categoria;
import dominio.Juego;
import dominio.Version;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestJuegos {
    ManejadorBD mbd = ManejadorBD.getInstancia();
    ControladorVersiones cv = ControladorVersiones.getInstancia();
    Controladorjuegos cj = Controladorjuegos.getInstancia();
    Juego j = new Juego();
    Version v = new Version();
    public TestJuegos() {
        try {
            mbd.setHost("localhost");
            mbd.setPuerto("3306");
            mbd.setBd("market");
            mbd.setUsuario("root");
            mbd.setPassword("root");
            if (mbd.estaDesconectado())
                mbd.conectar();
        } catch (SQLException ex) {
            Logger.getLogger(TestVersiones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Before
    public void setUp() {
        try {
            v.setEstado("pendiente");
            v.setExtension("");
            v.setFecha_alta(new Date());
            v.setId_juego(120);
            v.setJuego(controladores.Controladorjuegos.getInstancia().buscarJuegoPorID(v.getId_juego()));
            try {
                v.setJar(new FileInputStream("prueba"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TestJuegos.class.getName()).log(Level.SEVERE, null, ex);
            }
            v.setExtension("");
          
            Categoria c = new Categoria();
            c.setId(18);
            c.setNombre("accion");
            ArrayList<Categoria> cats = new ArrayList<>();
            cats.add(c);
            j.setCategorias(cats);
            j.setDescripcion("esto es una prueba");
            j.setNombre("matias w");
            j.setPrecio(25.2);
            j.setSize(25);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(TestVersiones.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }


    @Test
    public void AltaJuego() {
       /* try {
           
            cj.altaJuego(j, j.getCategorias());
            cj.bajaJuego(j.getId());
        } catch (SQLException ex) {
            fail();
        }*/
    }

}