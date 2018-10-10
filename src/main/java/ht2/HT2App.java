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
import java.sql.SQLException;
import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import com.google.gson.Gson;
import ht2.domain.Kysymys;
import ht2.domain.Vastaus;
import java.util.ArrayList;
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
                map.put("statistiikka", "Palvelussa on tällä hetkellä " 
                        + kurssiDao.getCount() + " kurssia, " 
                        + kysymysDao.getCount() + " kysymystä ja " 
                        + vastausDao.getCount() + " vastausta.");
            } catch (SQLException e) {
                System.out.println("e: " + e.getMessage());
                e.printStackTrace();
                map.clear();
                map.put("otsake", "Virhe");
                map.put("virhe", "SQL: " + e.getMessage());
                return new ModelAndView(map, "error");
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
                map.clear();
                map.put("otsake", "Virhe");
                map.put("virhe", "SQL: " + e.getMessage());
                return new ModelAndView(map, "error");
            }
            return new ModelAndView(map, "kurssit");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/error", (req, res) -> {
            HashMap map = new HashMap();
            map.put("otsake", "Odottamaton virhe");
            map.put("virhe", "Jotain meni pieleen :(");
            return new ModelAndView(map, "error");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/kurssit", (req, res) -> {
            String nimi = req.queryParams("nimi");
            if (nimi == null || nimi.length() == 0 || nimi.length() > 256) {
                res.redirect("/error");
                return "";
            }
            try {
                kurssiDao.add(new Kurssi(-1, nimi, null));
            } catch (SQLException e) {
                System.out.println("e: " + e.getMessage());
                res.redirect("/error");
                return "";
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
                res.redirect("/error");
                return "";
            } catch (Exception e) {
                System.out.println("e: " + e.getMessage());
                res.redirect("/error");
                return "";
            }
            
            res.redirect("/kurssit");
            return ""; 
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
                map.clear();
                map.put("otsake", "Virhe");
                map.put("virhe", "SQL: " + e.getMessage());
                return new ModelAndView(map, "error");
            }
            return new ModelAndView(map, "kysymykset");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/kysymykset", (req, res) -> {
            try {
                String aihe = req.queryParams("aihe");
                String teksti = req.queryParams("teksti");
                Integer kurssi_id = Integer.parseInt(req.queryParams("kurssi"));

                if (teksti == null
                        || aihe.length() > 256
                        || teksti.length() < 1 
                        || teksti.length() > 1024
                        || kurssi_id == null
                        || kurssiDao.findById(kurssi_id) == null) {
                    res.redirect("/kysymykset");
                    return "";
                }

                Kysymys kysymys = new Kysymys(
                        -1,
                        aihe,
                        teksti,
                        kurssi_id,
                        null);

                kysymysDao.add(kysymys);
                
            
            } catch (SQLException e) {
                System.out.println("SQL: " + e.getMessage());
                e.printStackTrace();
                res.redirect("/error");
                return "";
            } catch (Exception e) {
                System.out.println("Exc: " + e.getMessage());
                e.printStackTrace();
                res.redirect("/error");
                return "";
            }
            
            res.redirect("/kysymykset");
            return "";
        });
        
        Spark.post("/kysymys/:id/poista", (req, res) -> {
            try {
                Integer kysymysId = Integer.parseInt(req.params(":id"));
                kysymysDao.delete(kysymysId);
            } catch (SQLException e) {
                System.out.println("SQL: " + e.getMessage());
                res.redirect("/error");
                return "";
            } catch (Exception e) {
                System.out.println("Exc: " + e.getMessage());
                res.redirect("/error");
                return "";
            }
            res.redirect("/kysymykset");
            return "";
        });
        
        Spark.post("/kysymys/:id/vastaus", (req, res) -> {
            try {
                Integer kysymysId = Integer.parseInt(req.params(":id"));
                String teksti = req.queryParams("teksti");
                if (teksti == null || teksti.length() == 0 || teksti.length() > 1024) {
                    res.redirect("/error");
                    return "";
                }
                if (kysymysDao.findById(kysymysId) == null) {
                    res.redirect("/error");
                    return "";
                }
                
                String oikein = req.queryParams("oikein");
                System.out.println("oikein: " + oikein);
                if(oikein == null || oikein.length() < 1) {
                    oikein = "0";
                }
                vastausDao.add(new Vastaus(-1, teksti, oikein.equals("1"), kysymysId));
                
            } catch (SQLException e) {
                System.out.println("SQL: " + e.getMessage());
                res.redirect("/error");
                return "";
            } catch (Exception e) {
                System.out.println("Exc: " + e.getMessage());
                res.redirect("/error");
                return "";
            }
            res.redirect("/kysymykset");
            return "";
        });
        
        Spark.post("/vastaus/:id/poista", (req, res) -> {
            try {
                Integer vastausId = Integer.parseInt(req.params(":id"));
                
                if (vastausDao.findById(vastausId) == null) {
                    res.redirect("/error");
                    return "";
                }
                
                vastausDao.delete(vastausId);
                
            } catch (SQLException e) {
                System.out.println("SQL: " + e.getMessage());
                res.redirect("/error");
                return "";
            } catch (Exception e) {
                System.out.println("Exc: " + e.getMessage());
                res.redirect("/error");
                return "";
            }
            res.redirect("/kysymykset");
            return "";
        });
        
    }
}
