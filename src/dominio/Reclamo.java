/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.Date;


/**
 *
 * @author Alexandro
 */
public class Reclamo {

    private int id_reclamo;
    private String texto;
    private String categoria;
    private Juego id_juego;
    private Cliente id_usuario;
    private Date fecha;
    private Version version;

    public Reclamo() {
        this.id_usuario = new Cliente();
        this.id_reclamo = 0;
        this.categoria = "";
        this.id_juego = new Juego();
        this.texto = "";
        this.fecha = new Date();
        this.version = new Version();
    }

    public Date getFechaReclamo(){
    return fecha;
}
    
    public Version getVersionRecl(){
    return version;    
    }
    public Juego getIDJueg() {
        return id_juego;
    }

    public Cliente getIDCli() {
        return id_usuario;
    }
    
    public String getCatReclamo(){
        return categoria;
    }
    
    public String getTxtReclamo(){
        return texto;
    }
    
    public int getIdReclamo(){
        return id_reclamo;
    }
    
    public void setJueg(Juego juego){
        this.id_juego = juego;
    }
    
    public void setCatRecl(String cat_reclamo){
        this.categoria = cat_reclamo;
    }
    
    public void setIdRecl(int id_reclamo){
        this.id_reclamo =id_reclamo;
    }
    
    public void setcli(Cliente cli){
        this.id_usuario = cli;
    }
    
    public void setTxtRecl(String txt_reclamo){
        this.texto = txt_reclamo;
    }
    
    public void setFechaRecl(Date fechaReclamo){
        this.fecha = fechaReclamo;
    }
    
    public void setVersion(Version numero){
        this.version = numero;
    }
}
