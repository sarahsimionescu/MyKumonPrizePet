package mykumonprizepet;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.sin;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author sarah simionescu
 */
public class Display extends Thread {

    protected Game G;
    Mouse mouse;
    Keyboard keyboard;
    DisplayScreen displayScreen;
    boolean run;
    protected Image dbImage;
    public FontMetrics metrics;
    private Graphics dbg;
    int gameMode;
    ArrayList<UI> ui = new ArrayList<UI>();
    int w, h;
    YearMonth viewCalendar;
    int oldXP, oldLevel, displayPet;
    int counter;
    Student user;
    ArrayList<Student> students = new ArrayList<Student>();
    String password;

    @Override
    public void run() {
        w = 1024;
        h = 768;
        mouse = new Mouse();
        keyboard = new Keyboard();
        students = readStudents();
        displayScreen = new DisplayScreen();

    }
    
    public void updateStudents() {
        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt"));
            for (int i = 0; i < students.size(); i++) {
                Student s = students.get(i);
                writer.write(s.index + "," + s.subject + "," + s.name + "," + DateTimeFormatter.ofPattern("d/MM/yyyy").format(s.start) + "," + s.petName + "," + s.pet + "," + s.tokens + "," + s.level + "," + s.XP + "," + s.levelXP + "," + s.goalTime);    
                writer.newLine();
            }
            writer.close();
            System.out.println("Successfully updated student index.");
        } catch (IOException e) {
            System.out.println("An error occurred with updating student index.");
            e.printStackTrace();
        }
    }

    public ArrayList<Student> readStudents() {
        ArrayList<Student> data = new ArrayList<Student>();
        try {
            File s = new File("students.txt");
            if (s.createNewFile()) {
                System.out.println("Student list file created: " + s.getName());
            } else {
            }
            try {
                Scanner scanner = new Scanner(s);
                String[] info;
                String line;
                while (scanner.hasNextLine()) {

                    line = scanner.nextLine();
                    if (line.isEmpty() == false) {
                        info = line.split(",");
                        data.add(new Student(Integer.parseInt(info[0]), info[1].charAt(0), info[2], LocalDate.parse(info[3], DateTimeFormatter.ofPattern("d/MM/yyyy")), info[4], info[5], Integer.parseInt(info[6]), Integer.parseInt(info[7]), Integer.parseInt(info[8]), Integer.parseInt(info[9]), Integer.parseInt(info[10])));
                    }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred with reading student list.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("An error occurred with creating the student list.");
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<Date> readDays(Student s) {
        ArrayList<Date> data = new ArrayList<Date>();
        try {
            File f = new File("DATES/" + s.index + "," + s.name + "," + s.subject + ".txt");
            if (f.createNewFile()) {
                System.out.println("Student date file created: " + f.getName());
            } else {
            }
            try {
                Scanner scanner = new Scanner(f);
                String[] line;
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine().split(",");
                    data.add(new Date(LocalDate.parse(line[0], DateTimeFormatter.ofPattern("d/MM/yyyy")), Integer.parseInt(line[1])));
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred with reading days list.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("An error occurred with creating the days list.");
            e.printStackTrace();
        }
        return data;
    }

    public void writeDays(Student s) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("DATES/" + s.index + "," + s.name + "," + s.subject + ".txt"));
            for (int i = 0; i < s.dates.size(); i++) {
                writer.write(DateTimeFormatter.ofPattern("d/MM/yyyy").format(s.dates.get(i).date) + "," + s.dates.get(i).state);
                writer.newLine();
            }
            writer.close();
            System.out.println("Successfully updated student's completion dates.");
        } catch (IOException e) {
            System.out.println("An error occurred with recording student's completion dates.");
            e.printStackTrace();
        }
    }

    public class Mouse extends MouseAdapter {

        public double x;
        public double y;

        public void runGameMode(int i) {
            gameMode = i;
            Thread update = new Thread(G);
            update.start();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            x = e.getX();
            y = e.getY();

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (gameMode == 0) {
                user = new Student(null, null, "",null,"", null, null, null, null, null, null);
                if (ui.get(3).checkMouse() == true) { //MATH button
                    user.subject = 'M';
                    runGameMode(1);
                } else if (ui.get(2).checkMouse() == true) //READING button
                {
                    user.subject = 'R';
                    runGameMode(1);
                } else if (ui.get(5).checkMouse() == true)
                {
                    password = "";
                    runGameMode(4);
                }
            } else if (gameMode == 1) {
                if (ui.get(3).checkMouse() == true) //BACK button
                {
                    runGameMode(0);
                } else if (ui.get(4).checkMouse() == true) { //NEXT button
                    for (int i = 0; i < students.size(); i++) {
                        if (user.name.equals(students.get(i).name) && user.subject == students.get(i).subject) {
                            user = students.get(i);
                        }
                    }
                    user.dates = readDays(user);
                    oldXP = user.XP;
                    oldLevel = user.level;
                    viewCalendar = YearMonth.now();
                    displayPet = G.randomGIF(user.pet);
                    runGameMode(2);
                }
            } else if (gameMode == 2) {
                if (ui.get(5).checkMouse() == true) { //next month
                    viewCalendar = viewCalendar.plusMonths(1);
                    runGameMode(2);
                } else if (ui.get(6).checkMouse() == true) { //previous month
                    viewCalendar = viewCalendar.minusMonths(1);
                    runGameMode(2);
                } else if (ui.get(7).checkMouse() == true) //calendar
                {
                    runGameMode(2);
                } else if (ui.get(10).checkMouse() == true) //done
                {
                    writeDays(user);
                    students.set(user.index, user);
                    updateStudents();
                    runGameMode(3);
                }
            } else if (gameMode == 3)
            {
                if (ui.get(3).checkMouse() == true) {
                    runGameMode(0);
                }
            } else if (gameMode == 4) {
                if (ui.get(3).checkMouse() == true) {
                    runGameMode(0);
                } else if (ui.get(4).checkMouse() == true) {
                    runGameMode(5);
                }
            } else if (gameMode == 5) {
                if (ui.get(2).checkMouse() == true) { //MATH button
                    user.subject = 'R';
                    runGameMode(6);
                } else if (ui.get(3).checkMouse() == true) //READING button
                {
                    user.subject = 'M';
                    runGameMode(6);
                } else if (ui.get(4).checkMouse() == true) //READING button
                {
                    runGameMode(4);
                }
            } else if (gameMode == 6) {
                if (ui.get(3).checkMouse() == true) {
                    runGameMode(5);
                } else if (ui.get(4).checkMouse() == true) {
                    runGameMode(7);
                }
            } else if (gameMode == 7) {
                if (ui.get(2).checkMouse() == true) {
                    runGameMode(6);

                } else if (ui.get(3).checkMouse() == true) {
                    user.pet = "puppy";
                    runGameMode(8);
                } else if (ui.get(4).checkMouse() == true) {
                    user.pet = "kitty";
                    runGameMode(8);
                }
            } else if (gameMode == 8) {
                if (ui.get(3).checkMouse() == true) {
                    runGameMode(7);
                } else if (ui.get(4).checkMouse() == true) {
                    students.add(new Student(students.size(), user.subject, user.name, LocalDate.now(), user.petName, user.pet, 0, 1, 0, 1000, 15));
                    updateStudents();
                    runGameMode(0);
                }
            }
        }
    }

    public class Keyboard extends KeyAdapter {

        protected Display D;

        @Override
        public void keyPressed(KeyEvent e) {
            if (gameMode == 1) {
                user.name = textBoxType(e.getKeyCode(), user.name);
                mouse.runGameMode(1);
            } else if (gameMode == 4) {
                password = textBoxType(e.getKeyCode(), password);
                mouse.runGameMode(4);
            } else if (gameMode == 6) {
                user.name = textBoxType(e.getKeyCode(), user.name);
                mouse.runGameMode(6);
            } else if (gameMode == 8) {
                user.petName = textBoxType(e.getKeyCode(), user.petName);
                mouse.runGameMode(8);
            }
        }

        public String textBoxType(int keyCode, String str) {
            if (keyCode >= 65 && keyCode <= 90 && str.length() < 15 || keyCode == 32 && str.length() < 15) {
                str += Character.toString((char) keyCode);
            } else if (keyCode == 8) {
                str = deleteOne(str);
            }

            return str;
        }

        public String deleteOne(String str) {
            if (str != null && str.length() > 0) {
                str = str.substring(0, str.length() - 1);
            }
            return str;
        }

    }

    public class DisplayScreen extends JFrame {

        public DisplayScreen() {
            windowManager();
            addKeyListener(keyboard);
            addMouseMotionListener(mouse);
            addMouseListener(mouse);
            addMouseWheelListener(mouse);
            G.start();
        }

        public void windowManager() {
            setTitle("Engine");
            setVisible(true);
            setResizable(false);
            setSize(w, h);
            setBackground(new Color(255, 255, 255));
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

        @Override
        public void paint(Graphics g) {
            dbImage = createImage(getWidth(), getHeight());
            dbg = dbImage.getGraphics();
            paintComponent(dbg);
            if (run == true) {
                g.drawImage(dbImage, 0, 0, this);
            }
        }

        public void paintComponent(Graphics g) {
            if (run == true) {
                for (int i = 0; i < ui.size(); i++) {
                    ui.get(i).paint(g);
                }
                if (gameMode == 1 || gameMode == 4 || gameMode == 6 || gameMode == 8) {
                    paintTextBlinker(ui.get(2),g);
                }
            }
            counter++;
            repaint();
        }

        public void paintTextBlinker(UI t, Graphics g) {
            if (sin(((double) counter) * 0.1) > 0) {
                g.setColor(Color.black);
            } else {
                g.setColor(new Color(0, 0, 0, 0));
            }
            g.fillRect(t.getPosX() + t.getWidth() / 2, t.getPosY() - t.getHeight() / 2 + 20, 2, t.getHeight()-35);
        }

    }
}
