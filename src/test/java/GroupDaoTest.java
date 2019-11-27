import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.dao.impl.GroupDaoImpl;
import com.foxminded.university.entity.Group;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


class GroupDaoTest extends DaoTest {
    private final GroupDao groupDao = new GroupDaoImpl(connector);

    @Test
    void groupDaoShouldInsertGroup() {
        Group expected = new Group(11, "XX-10");
        groupDao.save(expected);
        Group group = groupDao.findById(11).get();
        assertEquals(expected, group);
    }

    @Test
    void groupDaoShouldReturnGroupById() {
        Group group = groupDao.findById(5).get();
        Group expected = new Group(5, "LY-85");
        assertEquals(expected, group);
    }

    @Test
    public void groupDaoShouldUpdateGroup() {
        Group expected = new Group(5, "XX-10");
        groupDao.update(expected);
        Group group = groupDao.findById(5).get();
        assertEquals(expected, group);
    }

    @Test
    void groupDaoShouldDeleteGroup() {
        groupDao.deleteById(5);
        Optional<Group> group = groupDao.findById(5);
        assertEquals(Optional.empty(), group);
    }

    @Test
    void groupDaoShouldFindGroupsByStudentCount() {
        List<Group> expected = Arrays.asList(new Group(1, "BP-93"),
                new Group(4, "NT-31"));
        List<Group> groups = groupDao.findGroupsByStudentsCount(1);
        assertEquals(expected, groups);
    }
}
