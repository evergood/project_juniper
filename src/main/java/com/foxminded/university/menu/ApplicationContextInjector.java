package com.foxminded.university.menu;

import com.foxminded.university.dao.Connector;
import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.dao.StudentDao;
import com.foxminded.university.dao.impl.GroupDaoImpl;
import com.foxminded.university.dao.impl.StudentDaoImpl;

import java.util.Scanner;

public final class ApplicationContextInjector {
    private static final Connector CONNECTOR = new Connector("database");
    private static final GroupDao GROUP_DAO = new GroupDaoImpl(CONNECTOR);
    private static final StudentDao STUDENT_DAO = new StudentDaoImpl(CONNECTOR);
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final MenuView VIEW = new MenuView(SCANNER);
    private static final MenuController CONTROLLER = new MenuController(VIEW, STUDENT_DAO, GROUP_DAO);

    private static ApplicationContextInjector applicationContextInjector;

    public static ApplicationContextInjector getInstance() {
        if (applicationContextInjector == null) {
            synchronized (ApplicationContextInjector.class) {
                if (applicationContextInjector == null) {
                    applicationContextInjector = new ApplicationContextInjector();
                }
            }
        }
        return applicationContextInjector;
    }

    public MenuController getController() {
        return CONTROLLER;
    }
}

