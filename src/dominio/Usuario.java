
package dominio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import dominio.mesGanancia;
        
public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private String tipo;
    private Date fecha_nac;
    private int edad;
    private String nick;
    private String email;
    private String img;
    private String pass;
    private ArrayList<mesGanancia> listaGananciasPorMes;
    
    
    public Usuario() {
        this.id = 0;
        this.nombre = "";
        this.apellido = "";
        this.tipo = "";
        this.fecha_nac = new Date();
        this.edad = 0;
        this.nick = "";
        this.email = "";
        this.img = "";
        this.pass = "";
    }

    public ArrayList<mesGanancia> getListaGananciasPorMes() {
        return listaGananciasPorMes;
    }

    public void setListaGananciasPorMes(ArrayList<mesGanancia> listaGananciasPorMes) {
        this.listaGananciasPorMes = listaGananciasPorMes;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(Date fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public int getEdad() {
        if (edad == 0)
            calcularEdad();
        return edad;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    private void calcularEdad() {
        Calendar hoy = Calendar.getInstance();
        Calendar fnac = new GregorianCalendar();
        fnac.setTime(fecha_nac);
        
        this.edad = hoy.get(Calendar.YEAR) - fnac.get(Calendar.YEAR);
        if (hoy.get(Calendar.DAY_OF_YEAR) < fnac.get(Calendar.DAY_OF_YEAR)){
            this.edad --;
        }
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
