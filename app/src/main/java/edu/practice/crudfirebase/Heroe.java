package edu.practice.crudfirebase;

public class Heroe {

    private String nombre;
    private String poder;
    private String enemigo;
    private Boolean vivo;

    public Heroe(String nombre, String poder, String enemigo) {
        this.nombre = nombre;
        this.poder = poder;
        this.enemigo = enemigo;
    }

    public Heroe() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPoder() {
        return poder;
    }

    public void setPoder(String poder) {
        this.poder = poder;
    }

    public String getEnemigo() {
        return enemigo;
    }

    public void setEnemigo(String enemigo) {
        this.enemigo = enemigo;
    }

    public Boolean getVivo() {
        return vivo;
    }

    public void setVivo(Boolean vivo) {
        this.vivo = vivo;
    }
}
