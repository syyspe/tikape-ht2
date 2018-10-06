package ht2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ht2.dao.KurssiDao;
import ht2.dao.KysymysDao;
import ht2.dao.VastausDao;
import ht2.database.Database;
import ht2.domain.Kurssi;
import ht2.domain.Kysymys;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import com.google.gson.Gson;
import ht2.domain.Kysymys;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author syyspe
 */
public class HT2App {
    public static void main(String[] args) {
        if (System.getenv("PORT") != null) {
           Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        
        Database db = new Database("jdbc:sqlite:ht2.db");
        KurssiDao kurssiDao = new KurssiDao(db);
        KysymysDao kysymysDao = new KysymysDao(db);
        VastausDao vastausDao = new VastausDao(db);
        Gson json = new Gson();
        
        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap();
            map.put("otsake", "Hallinnoi kursseja, kysymyksiä ja vastauksia");
            try {
                map.put("kursseja", "Palvelussa on tällä hetkellä " 
                        + kurssiDao.getCount() + " kurssia, " 
                        + kysymysDao.getCount() + " kysymystä ja " 
                        + vastausDao.getCount() + " vastausta.");
            } catch (SQLException e) {
                System.out.println("e: " + e.getMessage());
            }
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/kurssit", (req, res) -> {
            HashMap map = new HashMap();
            map.put("otsake", "Kurssit");
            map.put("theader1", "Nimi");
            map.put("theader2", "Poista kurssi");
            map.put("lisaa", "Lisää kurssi");
  
            try {
                List<Kurssi> kurssit = kurssiDao.findAll();
                List<Kysymys> kysymykset = kysymysDao.findAll();
                for (Kysymys k : kysymykset) {
                    k.setVastaukset(vastausDao.findByQuestionId(k.getId()));
                }
                for (Kurssi kurssi : kurssit) {
                    kurssi.setKysymykset(
                            kysymykset.stream()
                                    .filter(k -> k.getKurssi_id() == kurssi.getId())
                                    .collect(Collectors.toList()));
                }
                map.put("kurssit", kurssit);
            } catch (SQLException e) {
                System.out.println("e: " + e.getMessage());
            }
            return new ModelAndView(map, "kurssit");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/kurssit", (req, res) -> {
            String nimi = req.queryParams("nimi");
            if (nimi == null || nimi.length() == 0) {
                // TODO: error messages
                res.redirect("/kurssit");
                return "";
            }
            try {
                kurssiDao.add(new Kurssi(-1, nimi, null));
            } catch (SQLException e) {
                System.out.println("e: " + e.getMessage());
            }
            res.redirect("/kurssit");
            return "";
        });
                
        Spark.post("/kurssi/:id/poista", (req, res) -> {
            try {
                Integer kurssiId = Integer.parseInt(req.params(":id"));
                kurssiDao.delete(kurssiId);
            } catch (SQLException e) {
                System.out.println("e: " + e.getMessage());
                
            } catch (Exception e) {
                System.out.println("e: " + e.getMessage());
                
            } finally {
               res.redirect("/kurssit");
               return ""; 
            }
        });
        
        Spark.get("/kysymykset", (req, res) -> {
            HashMap map = new HashMap();
            map.put("otsake", "Kysymykset ja vastaukset");
            map.put("lisaa", "Lisää uusi kysymys");
            try {
                List<Kysymys> kysymykset = kysymysDao.findAll();
                List<Kurssi> kurssit = kurssiDao.findAll();
                for (Kysymys kysymys : kysymykset) {
                    kysymys.setVastaukset(vastausDao.findByQuestionId(kysymys.getId()));
                }
                map.put("kysymykset", kysymykset);
                map.put("kurssit", kurssit);
            } catch (SQLException e) {
                System.out.println("e: " + e.getMessage());
            }
            return new ModelAndView(map, "kysymykset");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/kysymykset", (req, res) -> {
            res.type("application/json");
            
            return "";
        });
        
    }
}
