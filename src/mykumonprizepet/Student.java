/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mykumonprizepet;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author sarah
 */
public class Student {

    Integer index;
    Character subject;
    String name;
    String petName;
    Integer tokens;
    Integer level, XP, levelXP;
    Integer goalTime;
    ArrayList<Date> dates;
    LocalDate start;
    String pet;

    public Student(Integer i, Character s, String n, LocalDate st, String pn, String p, Integer t, Integer l, Integer xp, Integer lxp, Integer gt) {
        index = i;
        subject = s;
        name = n;
        start = st;
        petName = pn;
        tokens = t;
        level = l;
        XP = xp;
        levelXP = lxp;
        goalTime = gt;
        pet = p;
    }

  
    
   
}
