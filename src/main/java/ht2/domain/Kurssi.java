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
public class Kurssi {
    private int id;
    private String nimi;
    private List<Kysymys> kysymykset;

    public Kurssi(int id, String nimi, List<Kysymys> kysymykset) {
        this.id = id;
        this.nimi = nimi;
        this.kysymykset = kysymykset;
    }

    public int getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }

    public List<Kysymys> getKysymykset() {
        return kysymykset;
    }
    
    public void setId(int id) {
        this.id = id;
    }

     public void setNimi(String nimi) {
        this.nimi = nimi;
    }
     
    public void setKysymykset(List<Kysymys> kysymykset) {
        this.kysymykset = kysymykset;
    }
    
}
