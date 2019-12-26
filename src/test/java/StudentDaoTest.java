import com.foxminded.university.dao.StudentDao;
import com.foxminded.university.dao.impl.StudentDaoImpl;
import com.foxminded.university.entity.Group;
import com.foxminded.university.entity.Student;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


class StudentDaoTest extends DaoTest {
    private final StudentDao studentDao = new StudentDaoImpl(connector);

    @Test
    void studentDaoShouldInsertStudent() {
        Student expected = new Student.Builder()
                .withFirstName("Garry")
                .withLastName("Cooper")
                .withId(201)
                .withGroup(new Group(7))
                .build();
        studentDao.save(expected);
        Student student = studentDao.findById(201).get();
        assertEquals(expected, student);
    }

    @Test
    void studentDaoShouldReturnStudentById() {
        Student students = studentDao.findById(13).get();
        Student expected = new Student.Builder().withFirstName("Noah")
                .withLastName("Thompson").withId(13).withGroup(new Group(4)).build();
        assertEquals(expected, students);
    }

    @Test
    void studentDaoShouldUpdateStudent() {
        Student expected = new Student.Builder()
                .withFirstName("Garry")
                .withLastName("Cooper")
                .withId(100)
                .withGroup(new Group(7))
                .build();
        studentDao.update(expected);
        Student student = studentDao.findById(100).get();
        assertEquals(expected, student);
    }

    @Test
    void studentDaoShouldDeleteStudent() {
        studentDao.deleteById(100);
        Optional<Student> student = studentDao.findById(100);
        assertEquals(Optional.empty(), student);
    }

    @Test
    void studentDaoShouldAddStudentToCourse() throws SQLException {
        Integer expectedStudentId = 10;
        Integer expectedCourseId = 5;
        List<Integer> expected = Arrays.asList(expectedStudentId, expectedCourseId);
        String expectedCourseName = "Math";
        studentDao.addStudentToCourse(expectedStudentId, expectedCourseName);
        List<Integer> studentCourses = getStudentCourses(
                "SELECT * FROM studentcourses WHERE student_id = 10 AND course_id = 5").get();
        assertEquals(expected, studentCourses);
    }

    @Test
    void studentDaoShouldDeleteStudentFromCourse() throws SQLException {
        Integer studentId = 10;
        String courseName = "Law";
        studentDao.deleteStudentFromCourse(studentId, courseName);
        Optional<List<Integer>> studentCourses = getStudentCourses(
                "SELECT * FROM studentcourses WHERE student_id = 10 AND course_id = 6");
        assertEquals(Optional.empty(), studentCourses);
    }

    private Optional<List<Integer>> getStudentCourses(String query) throws SQLException {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.of(mapResultStudentCourse(resultSet)) : Optional.empty();
            }
        }
    }

    private List<Integer> mapResultStudentCourse(ResultSet resultSet) throws SQLException {
        return Arrays.asList(resultSet.getInt(1), resultSet.getInt(2));
    }

}
