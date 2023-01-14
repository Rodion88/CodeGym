package com.javarush.task.task35.task3513;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        Menu menu = new Menu();
        Controller controller = new Controller(model);

        JFrame game = new JFrame();
        game.setTitle("2048");
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setSize(460, 510);
        game.setResizable(false);
        game.setJMenuBar(menu.menuBar);

        game.add(controller.getView());
        game.setLocationRelativeTo(null);
        game.setVisible(true);
    }
}
