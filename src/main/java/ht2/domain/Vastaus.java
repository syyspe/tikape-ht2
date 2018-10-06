/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht2.domain;

/**
 *
 * @author syyspe
 */
public class Vastaus {
    private int id;
    private String teksti;
    private Boolean oikein;
    private int kysymys_id;

    public Vastaus(int id, String teksti, Boolean oikein, int kysymys_id) {
        this.id = id;
        this.teksti = teksti;
        this.oikein = oikein;
        this.kysymys_id = kysymys_id;
    }

    public int getId() {
        return id;
    }

    public String getTeksti() {
        return teksti;
    }

    public Boolean getOikein() {
        return oikein;
    }

    public int getKysymys_id() {
        return kysymys_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTeksti(String teksti) {
        this.teksti = teksti;
    }

    public void setOikein(Boolean oikein) {
        this.oikein = oikein;
    }

    public void setKysymys_id(int kysymys_id) {
        this.kysymys_id = kysymys_id;
    }
     
   
}
