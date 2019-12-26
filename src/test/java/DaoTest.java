import com.foxminded.university.dao.Connector;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.apache.poi.util.StringUtil.UTF8;

public abstract class DaoTest {
    static final ResourceBundle BUNDLE = ResourceBundle.getBundle("database_test");
    static final String JDBC_URL = BUNDLE.getString("url");
    static final String USER = BUNDLE.getString("username");
    static final String PASSWORD = BUNDLE.getString("password");
    final Connector connector = new Connector("database_test");

    @BeforeAll
    static void executeSchema() throws SQLException {
        RunScript.execute(JDBC_URL, USER, PASSWORD, "src/test/resources/schema.sql", UTF8, false);
    }

    @BeforeEach
    void executeUpload() throws SQLException {
        RunScript.execute(JDBC_URL, USER, PASSWORD, "src/test/resources/schema_upload.sql", UTF8, false);
    }
}
