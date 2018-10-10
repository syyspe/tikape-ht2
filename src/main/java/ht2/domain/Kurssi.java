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
public class Kurssi extends AbstractHt2Object {
    private Integer id;
    private String nimi;
    private List<Kysymys> kysymykset;

    public Kurssi(Integer id, String nimi, List<Kysymys> kysymykset) {
        super(id);
        this.id = id;
        this.nimi = nimi;
        this.kysymykset = kysymykset;
    }


    public String getNimi() {
        return nimi;
    }

    public List<Kysymys> getKysymykset() {
        return kysymykset;
    }
    
     public void setNimi(String nimi) {
        this.nimi = nimi;
    }
     
    public void setKysymykset(List<Kysymys> kysymykset) {
        this.kysymykset = kysymykset;
    }
    
}
