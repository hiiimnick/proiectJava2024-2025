package testCase;

import clase.Curs;
import clase.FileDataManager;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FileDataManagerTest {

    @Test
    public void testCreateCoursesData() {
        FileDataManager fileDataManager = new FileDataManager();
        List<Curs> courses = fileDataManager.createCoursesData();

        assertNotNull(courses, "The courses list should not be null");
        assertFalse(courses.isEmpty(), "The courses list should not be empty");

        for (Curs course : courses) {
            assertNotNull(course.getNume(), "Course name should not be null");
            assertNotNull(course.getDescriere(), "Course description should not be null");
            assertTrue(course.getID() > 0, "Course ID should be greater than 0");
            assertTrue(course.getIDProfesor() > 0, "Professor ID should be greater than 0");
            assertTrue(course.getAnCurs() > 0, "Course year should be greater than 0");
        }
    }
}