/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mykumonprizepet;

import java.util.Comparator;

/**
 *
 * @author sarah
 */
public class compareDate implements Comparator<Date> {

    @Override
    public int compare(Date a, Date b) {
        return a.date.compareTo(b.date);
    }
}
