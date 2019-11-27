package com.foxminded.university.dao;

import com.foxminded.university.entity.Student;

import java.util.List;

public interface StudentDao extends CrudDao<Student, Integer> {
    List<Student> findStudentsByCourseName(String courseName);

    void addStudentToCourse(Integer studentId, String courseName);

    void deleteStudentFromCourse(Integer studentId, String courseName);
}
