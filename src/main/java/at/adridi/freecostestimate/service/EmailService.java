/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.adridi.freecostestimate.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * Manages the sending of cost estimate emails
 * 
 * @author A.Dridi
 */
public class EmailService {
    
    public static ExecutorService executeEmailJob = Executors.newCachedThreadPool();
    
}
