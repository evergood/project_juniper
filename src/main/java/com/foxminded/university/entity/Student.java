package com.foxminded.university.entity;

import java.util.Objects;

public final class Student {

    private final Integer studentId;
    private final String firstName;
    private final String lastName;
    private Group group;

    private Student(Builder builder) {
        this.studentId = builder.studentId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.group = builder.group;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("ID").append(studentId)
                .append(" ").append(firstName)
                .append(" ").append(lastName)
                .append(" ").append(group)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId) &&
                Objects.equals(firstName, student.firstName) &&
                Objects.equals(lastName, student.lastName) &&
                Objects.equals(group, student.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, firstName, lastName, group);
    }


    public static class Builder {
        private Integer studentId;
        private String firstName;
        private String lastName;
        private Group group;

        public Builder() {

        }

        public Student build() {
            return new Student(this);
        }

        public Builder withId(Integer studentID) {
            this.studentId = studentID;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withGroup(Group group) {
            this.group = group;
            return this;
        }
    }
}
