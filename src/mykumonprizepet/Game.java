/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mykumonprizepet;

import java.awt.Color;
import java.io.File;
import java.time.LocalDate;

/**
 *
 * @author sarah simionescu
 */
public class Game extends Thread {

    protected Display D;
    
    public Color c(char s)
    {
        if(s == 'M')
        {
            return new Color(121, 196, 240);
        }else
        {
            return new Color(240, 171, 121);
        }
    }
    
    public int randomGIF(String f)
    {
         return (int) new MathTool().random(1, new File("PNG/" + f).listFiles().length); 
    }

    @Override
    public void run() {
        D.run = false;
        D.ui.clear();
        if (D.gameMode == 0) {
            D.students = D.readStudents();
            D.ui.add(new Graphic(D.w / 2, D.h / 2, 1, 1, "room", D));
            D.ui.add(new Graphic(D.w / 2, D.h / 2, 1, 1, "main", D));
            D.ui.add(new CircleButton("READING", D.w / 4, 600, 39, Color.WHITE, new Color(230, 119, 76), true, D));
            D.ui.add(new CircleButton("MATH", D.w / 4 * 3, 600, 56, Color.WHITE, new Color(70, 162, 212), true, D));      
            D.ui.add(new GIF(D.w / 2, 600, 0.6, 0.6, "paws", 1, D));
            D.ui.add(new CircleButton("+", 30, 50, 30, Color.WHITE, new Color(141, 212, 70), true, D));

        } else if (D.gameMode == 1) {
           
            D.ui.add(new Graphic(D.w / 2, D.h / 2, 1, 1, "room", D));
            D.ui.add(new Graphic(D.w / 2, D.h / 2, 1, 1, "login" + D.user.subject, D));
            D.ui.add(new Text(D.user.name, D.w / 2, 500, 70, Color.BLACK, Color.WHITE, true, D));
            D.ui.add(new Button("BACK", 400, 650, 56, Color.WHITE, c(D.user.subject), true, D));
            D.ui.add(new Button("NEXT", D.w - 400, 650, 56, Color.WHITE, c(D.user.subject), false, D));
            if (checkName()) {
                D.ui.get(D.ui.size() - 1).setActive(true);
            }
        } else if (D.gameMode == 2) {
            D.ui.add(new Graphic(D.w / 2, D.h / 2, 1, 1, "room", D));
            D.ui.add(new Text(D.user.name, D.w / 2, 100, 70, Color.BLACK, Color.white, true, D));
            D.ui.add(new GIF(D.w - 275, 600, 0.7, 0.7, D.user.pet, D.displayPet, D));
            D.ui.add(new Text(D.user.petName + " is Level " + D.user.level.toString(), D.w - 275, 270, 30,Color.white, c(D.user.subject), true, D));
            D.ui.add(new ProgressBar(D.user.XP, D.user.levelXP, D.w - 275, 320, 400, 50, c(D.user.subject), Color.WHITE, D));
            D.ui.add(new CircleButton(">", 275 + 200, 220, 30, Color.WHITE, c(D.user.subject), true, D));
            D.ui.add(new CircleButton("<", 275 - 200, 220, 30, Color.WHITE, c(D.user.subject), true, D));
            D.ui.add(new Calendar(D.viewCalendar, 275, 500, 450, 400, c(D.user.subject), D));
            D.ui.add(new Graphic(70, 100,0.2, 0.2, "token", D));
            D.ui.add(new Text(D.user.tokens.toString(), 140, 100, 30, Color.BLACK, Color.WHITE, true, D));
            D.ui.add(new Button("SAVE", D.w - 150, 100, 56, Color.WHITE, c(D.user.subject), true, D));
            D.ui.add(new Text("Student # " + D.user.index, 50, D.h - 20, 15, Color.white, c(D.user.subject), true, D));
        } else if (D.gameMode == 3) {
            int newPoints = D.user.XP+((D.user.level-1)*D.user.levelXP) - D.oldXP -((D.oldLevel-1)*D.user.levelXP);
            if (newPoints > 0) {
                D.ui.add(new Graphic(D.w / 2, D.h / 2, 1, 1, "room", D));
                D.ui.add(new Graphic(D.w / 2, D.h / 2, 1, 1, "congrats" + D.user.subject, D));
                D.ui.add(new GIF(D.w / 2, D.h / 2 - 100, 0.7, 0.7, D.user.pet + "C", randomGIF(D.user.pet + "C"), D));
                D.ui.add(new Button("DONE", D.w / 2, 500, 56, Color.WHITE, c(D.user.subject), true, D));
                System.out.println(D.oldXP + " " + D.user.XP);
                String congrats = "You earned " + newPoints + " point" + addS(newPoints);
                if (D.oldLevel < D.user.level) {
                    congrats += " and " + (D.user.level - D.oldLevel) + " prize token" + addS(D.user.level - D.oldLevel);
                }
                D.ui.add(new Text(congrats + "!", D.w / 2, 100, 30, c(D.user.subject), Color.WHITE, true, D));

            } else {
                D.mouse.runGameMode(0);
            }
        } else if (D.gameMode == 4) {
            D.ui.add(new Graphic(D.w / 2, D.h / 2, 1, 1, "room", D));
            D.ui.add(new Graphic(D.w / 2, D.h / 2, 1, 1, "password", D));
            D.ui.add(new Text(toStars(D.password), D.w / 2, 500, 70, Color.BLACK, Color.WHITE, true, D));
            D.ui.add(new Button("BACK", 400, 650, 56, Color.WHITE, c('M'), true, D));
            D.ui.add(new Button("NEXT", D.w - 400, 650, 56, Color.WHITE, c('M'), false, D));
            if (D.password.equals("KUMON")) {
                D.ui.get(D.ui.size() - 1).setActive(true);
            }
        } else if (D.gameMode == 5) {
            D.ui.add(new Graphic(D.w / 2, D.h / 2, 1, 1, "room", D));
            D.ui.add(new Graphic(D.w / 2, D.h / 2,1, 1, "subjectselect", D));
            D.ui.add(new CircleButton("READING", D.w / 3, 550, 39 *4/3, Color.WHITE, new Color(230, 119, 76), true, D));
            D.ui.add(new CircleButton("MATH", D.w / 3 * 2, 550, 56 *4/3, Color.WHITE, new Color(70, 162, 212), true, D));
            D.ui.add(new Button("BACK", 100, D.h - 50, 30, Color.WHITE, c('M'), true, D));
        } else if (D.gameMode == 6) {
            D.ui.add(new Graphic(D.w / 2, D.h / 2, 1, 1, "room", D));
            D.ui.add(new Graphic(D.w / 2, D.h / 2, 1, 1, "studentname" + D.user.subject, D));
            D.ui.add(new Text(D.user.name, D.w / 2, 500, 70, Color.BLACK, Color.WHITE, true, D));
            D.ui.add(new Button("BACK", 400, 650, 56, Color.WHITE, c(D.user.subject), true, D));
            D.ui.add(new Button("NEXT", D.w - 400, 650, 56, Color.WHITE, c(D.user.subject), false, D));
            if (D.user.name.length() > 0 && checkName() == false) {
                D.ui.get(D.ui.size() - 1).setActive(true);
            }
        } else if (D.gameMode == 7) {
            D.ui.add(new Graphic(D.w / 2, D.h / 2, 1, 1, "room", D));
            D.ui.add(new Graphic(D.w / 2, D.h / 2, 1, 1, "petselect", D));
            
            D.ui.add(new Button("BACK", 100, D.h - 50, 30, Color.WHITE, c(D.user.subject), true, D));
            D.ui.add(new Button("Puppy", D.w/3-50, 400, 30, Color.WHITE, c(D.user.subject), true, D));
            D.ui.add(new Button("Kitty", D.w/3*2+50, 400, 30, Color.WHITE, c(D.user.subject), true, D));
            D.ui.add(new GIF(D.w / 3, 600, 0.6, 0.6, "puppy", 5, D));
            D.ui.add(new GIF(D.w / 3*2, 600, 0.6, 0.6, "kitty", 5, D));
        } else if (D.gameMode == 8) {
            D.ui.add(new Graphic(D.w / 2, D.h / 2, 1, 1, "room", D));
            D.ui.add(new Graphic(D.w / 2, D.h / 2, 1, 1, "petname" + D.user.subject, D));
            D.ui.add(new Text(D.user.petName, D.w / 2, 500, 70, Color.BLACK, Color.WHITE, true, D));
            D.ui.add(new Button("BACK", 400, 650, 56, Color.WHITE, c(D.user.subject), true, D));
            D.ui.add(new Button("DONE", D.w - 400, 650, 56, Color.WHITE, c(D.user.subject), false, D));
            if (D.user.petName.length() > 0) {
                D.ui.get(D.ui.size() - 1).setActive(true);
            }
        }
        D.run = true;
    }
    
    public String addS(int  n)
    {
        if(n != 1)
        {
            return "s";
        }else
        {
            return "";
        }
    }

    public String toStars(String s)
    {
        String n = "";
        for(int i = 0; i < s.length(); i++)
        {
            n += "*";
        }
        return n;
    }
    

    public boolean checkName() {
        for (int i = 0; i < D.students.size(); i++) {
            if (D.user.name.equals(D.students.get(i).name) && D.user.subject == D.students.get(i).subject) {
                return true;
            }
        }
        return false;
    }

}
