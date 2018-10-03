package ht2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
/**
 *
 * @author syyspe
 */
public class HT2App {
    public static void main(String[] args) {
        if (System.getenv("PORT") != null) {
           Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        
        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap();
            map.put("otsake", "Olen otsake");
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
    }
}
