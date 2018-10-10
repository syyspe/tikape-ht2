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
public class Vastaus extends AbstractHt2Object {
    private Integer id;
    private String teksti;
    private Boolean oikein;
    private int kysymys_id;

    public Vastaus(Integer id, String teksti, Boolean oikein, int kysymys_id) {
        super(id);
        this.id = id;
        this.teksti = teksti;
        this.oikein = oikein;
        this.kysymys_id = kysymys_id;
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
