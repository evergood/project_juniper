package com.foxminded.university.menu;

import java.util.Scanner;

public class MenuView {
    private final Scanner scanner;

    public MenuView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void printText(String text) {
        System.out.println(text);
    }

    public char readOption() {
        return scanner.next().charAt(0);
    }

    public String readText() {
        return scanner.next();
    }

    public Integer readDigit() {
        return scanner.nextInt();
    }
}
