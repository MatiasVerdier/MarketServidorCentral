
package dominio;

import java.util.ArrayList;

public class Juego {
    private int id;
    private String nombre;
    private String descripcion;
    private double size;
    private double precio;
    private Desarrollador des;
    private String portada;
    private ArrayList categorias;
    private ArrayList compras;
    private ArrayList comentarios;
    private double ganancias;

    // private ArrayList <Version> versiones;
    public double getGanancias() {
        return ganancias;
    }

    public void setGanancias(double ganancias) {
        this.ganancias = ganancias;
    }

    public Juego() {
        this.id = 0;
        this.nombre = "";
        this.descripcion = "";
        this.size = 0;
        this.precio = 0;
        this.des = new Desarrollador();
        this.portada = "";
        this.categorias = new ArrayList();
        this.compras = new ArrayList();
        this.comentarios = new ArrayList();
        //this.versiones = new ArrayList();
//        Version v = new Version();
//        v.setJar("");
//        this.versiones.add(v);
    }

    public Desarrollador getDes() {
        return des;
    }

    public void setDes(Desarrollador des) {
        this.des = des;
    }

    
    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public ArrayList getCategorias() {
        return categorias;
    }

    public void setCategorias(ArrayList categorias) {
        this.categorias = categorias;
    }

    public ArrayList getCompras() {
        return compras;
    }

    public void setCompras(ArrayList compras) {
        this.compras = compras;
    }

    public ArrayList getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList comentarios) {
        this.comentarios = comentarios;
    }

   /* public ArrayList<Version> getVersiones() {
        return versiones;
    }

    public void setVersiones(ArrayList<Version> versiones) {
        this.versiones = versiones;
    }
    */

    @Override
    public String toString() {
        return this.nombre;
    }
}
