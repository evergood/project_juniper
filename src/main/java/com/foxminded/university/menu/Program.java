package com.foxminded.university.menu;

public class Program {
    public static void main(String[] args) {
        ApplicationContextInjector injector = ApplicationContextInjector.getInstance();
        MenuController controller = injector.getController();
        controller.executeMenu();
    }
}
