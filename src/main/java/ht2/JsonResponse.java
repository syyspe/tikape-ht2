/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht2;

/**
 *
 * @author syyspe
 */
public class JsonResponse {
    String error;
    String ok;

    public JsonResponse(String error, String ok) {
        this.error = error;
        this.ok = ok;
    }
    
    
}
