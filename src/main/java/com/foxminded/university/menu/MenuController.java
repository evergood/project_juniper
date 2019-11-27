package com.foxminded.university.menu;

import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.dao.StudentDao;
import com.foxminded.university.entity.Group;
import com.foxminded.university.entity.Student;

public class MenuController {

    private static final String MENU = "Pick an option\n" +
            "\tA. Find all groups with less or equals student count\n" +
            "\tB. Find all students related to course with given name\n" +
            "\tC. Add new student\n" +
            "\tD. Delete student by STUDENT_ID\n" +
            "\tE. Add a student to the course (from a list)\n" +
            "\tF. Remove the student from one of his or her courses";
    private final GroupDao groupDao;
    private final StudentDao studentDao;
    private final MenuView view;

    public MenuController(MenuView view, StudentDao studentDao, GroupDao groupDao) {
        this.groupDao = groupDao;
        this.studentDao = studentDao;
        this.view = view;
    }

    public void executeMenu() {
        while (true) {
            view.printText(MENU);
            switch (view.readOption()) {
                case 'A':
                    findGroupsByStudentsCount();
                case 'B':
                    findStudentsByCourse();
                case 'C':
                    addStudent();
                case 'D':
                    deleteStudentById();
                case 'E':
                    addStudentToCourse();
                case 'F':
                    removeStudentFromCourse();
                default:
                    view.printText("Pick an option from the list");
            }
        }
    }

    public void findGroupsByStudentsCount() {
        view.printText("Input max amount of students");
        int amount = view.readDigit();
        StringBuilder groupsToText = new StringBuilder();
        groupDao.findGroupsByStudentsCount(amount).forEach(p -> groupsToText.append(p.toString()).append("\n"));
        view.printText(groupsToText.toString());
    }

    public void findStudentsByCourse() {
        view.printText("Input course name");
        String courseName = view.readText();
        StringBuilder coursesToText = new StringBuilder();
        studentDao.findStudentsByCourseName(courseName).forEach(p -> coursesToText.append(p.toString())
                .append("\n"));
        view.printText(coursesToText.toString());
    }

    public void addStudent() {
        view.printText("Input student's ID");
        int studentId = view.readDigit();
        view.printText("Input student's first name");
        String firstName = view.readText();
        view.printText("Input student's last name");
        String lastName = view.readText();
        view.printText("Input student's group ID");
        int groupId = view.readDigit();
        studentDao.save(new Student.Builder()
                .withId(studentId)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withGroup(new Group(groupId))
                .build());
    }

    public void deleteStudentById() {
        view.printText("Input student's ID");
        int studentId = view.readDigit();
        studentDao.deleteById(studentId);
    }

    public void addStudentToCourse() {
        view.printText("Input student's ID");
        int studentId = view.readDigit();
        view.printText("Input course name");
        String courseName = view.readText();
        studentDao.addStudentToCourse(studentId, courseName);
    }

    public void removeStudentFromCourse() {
        view.printText("Input student's ID");
        int studentId = view.readDigit();
        view.printText("Input course name");
        String courseName = view.readText();
        studentDao.deleteStudentFromCourse(studentId, courseName);
    }

}
