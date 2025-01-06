package clase;

import java.io.*;
import java.util.*;

public class FileDataManager {

    File studentFile = new File("inputData/studenti.txt");
    File profesorFile = new File("inputData/profesori.txt");

    public List<Student> createStudentsData() {
        List<Student> students = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(studentFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Student student = new Student(Integer.parseInt(data[0].trim()), data[1].trim(), data[2].trim(), Integer.parseInt(data[3].trim()), Integer.parseInt(data[4].trim()), data[5].trim(), data[6].trim());
                students.add(student);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }
}
