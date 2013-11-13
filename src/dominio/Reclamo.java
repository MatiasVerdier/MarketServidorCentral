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
    private int id_juego;
    private int id_usuario;
    private Date fecha;
    private String version;

    public Reclamo() {
        this.id_usuario = 0;
        this.id_reclamo = 0;
        this.categoria = "";
        this.id_juego = 0;
        this.texto = "";
        this.fecha = new Date();
        this.version = "";
    }

    public Date getFechaReclamo(){
        return fecha;
    }
    
    public String getVersionRecl(){
        return version;    
    }
    public int getIDJueg() {
        return id_juego;
    }

    public int getIDCli() {
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
    
    public void setJueg(int juego){
        this.id_juego = juego;
    }
    
    public void setCatRecl(String cat_reclamo){
        this.categoria = cat_reclamo;
    }
    
    public void setIdRecl(int id_reclamo){
        this.id_reclamo =id_reclamo;
    }
    
    public void setCli(int cli){
        this.id_usuario = cli;
    }
    
    public void setTxtRecl(String txt_reclamo){
        this.texto = txt_reclamo;
    }
    
    public void setFechaRecl(Date fechaReclamo){
        this.fecha = fechaReclamo;
    }
    
    public void setNroVersion(String numero){
        this.version = numero;
    }
}
