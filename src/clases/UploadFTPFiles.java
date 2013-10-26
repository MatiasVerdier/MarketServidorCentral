
package clases;

import controladores.Controladorjuegos;
import dominio.Categoria;
import dominio.Desarrollador;
import dominio.Juego;
import dominio.Version;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UploadFTPFiles {
    
public void subirArchivo(String archivo, InputStream inp){
    try {
                String user = "a8680950";
                String pass = "random123456";//url
                String ruta= "progapli2013.comule.com/public_html/imagenes/juegos/" + archivo;
                //String rutaHtml= extension;
                URL url = new URL("ftp://" + user + ":" + pass + "@" + ruta + ";type=i");

                URLConnection urlc = url.openConnection();
                OutputStream os = urlc.getOutputStream();

                byte bytes[] = new byte[1024];
                int readCount = 0;


                //subo el archivo
                while ((readCount = inp.read(bytes)) > 0) {
                    os.write(bytes, 0, readCount);
                }

                os.flush();
                os.close();
                inp.close();
    } catch (MalformedURLException ex) {
        Logger.getLogger(UploadFTPFiles.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(UploadFTPFiles.class.getName()).log(Level.SEVERE, null, ex);
    }
 
}
}