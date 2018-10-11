/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht2.domain;

import java.util.List;

/**
 *
 * @author syyspe
 */
public class Kysymys extends AbstractHt2Object {
    private Integer id;
    private String aihe;
    private String teksti;
    private int kurssi_id;
    private Kurssi kurssi;
    private List<Vastaus> vastaukset;

    public Kysymys(Integer id, String aihe, String teksti, int kurssi_id, List<Vastaus> vastaukset) {
        super(id);
        this.id = id;
        this.aihe = aihe;
        this.teksti = teksti;
        this.kurssi_id = kurssi_id;
        this.vastaukset = vastaukset;
        this.kurssi = null;
    }

    public String getAihe() {
        return aihe;
    }

    public String getTeksti() {
        return teksti;
    }

    public int getKurssi_id() {
        return kurssi_id;
    }

    public Kurssi getKurssi() {
        return kurssi;
    }

    public void setKurssi(Kurssi kurssi) {
        this.kurssi = kurssi;
    }
  
    public List<Vastaus> getVastaukset() {
        return vastaukset;
    }
    
    public void setAihe(String aihe) {
        this.aihe = aihe;
    }

    public void setTeksti(String teksti) {
        this.teksti = teksti;
    }

    public void setKurssi_id(int kurssi_id) {
        this.kurssi_id = kurssi_id;
    }
   
    public void setVastaukset(List<Vastaus> vastaukset) {
        this.vastaukset = vastaukset;
    }
 
}
