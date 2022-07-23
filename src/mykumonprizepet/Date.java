/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mykumonprizepet;

import java.time.LocalDate;

/**
 *
 * @author sarah
 */
public class Date {

    LocalDate date;
    int state;

    public Date(LocalDate d, int s) {
        state = s;
        date = d;
    }
}
