import com.foxminded.university.dao.impl.CourseDaoImpl;
import com.foxminded.university.entity.Course;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseDaoTest extends DaoTest {
    private final CourseDaoImpl courseDao = new CourseDaoImpl(connector);

    @Test
    void courseDaoShouldInsertCourse() {
        Course expected = Course.builder()
                .withCourseId(11)
                .withCourseName("First aid")
                .withCourseDescription("How to attach that finger back in place")
                .build();
        courseDao.save(expected);
        Course course = courseDao.findById(11).get();
        assertEquals(expected, course);
    }

    @Test
    void courseDaoShouldReturnCourseById() {
        Course course = courseDao.findById(5).get();
        Course expected = Course.builder()
                .withCourseId(5)
                .withCourseName("Math")
                .withCourseDescription("Calculate how much you body costs")
                .build();
        assertEquals(expected, course);
    }

    @Test
    void courseDaoShouldUpdateCourse() {
        Course expected = Course.builder()
                .withCourseId(5)
                .withCourseName("First aid")
                .withCourseDescription("How to attach that finger back in place")
                .build();
        courseDao.update(expected);
        Course course = courseDao.findById(5).get();
        assertEquals(expected, course);
    }

    @Test
    void courseDaoShouldDeleteCourse() {
        courseDao.deleteById(5);
        Optional<Course> course = courseDao.findById(5);
        assertEquals(Optional.empty(), course);
    }
}
