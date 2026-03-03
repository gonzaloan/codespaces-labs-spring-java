package grader;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

class QualityTest {

    private static final Path SRC  = Path.of(System.getProperty("user.dir")).resolve("src/main/java");
    private static final Path TEST = Path.of(System.getProperty("user.dir")).resolve("src/test/java");

    @Test
    void testControllerHasRequestMappingOnClass() throws IOException {
        String content = readFile("UserController.java");
        assertTrue(content.contains("@RequestMapping"),
                "Add @RequestMapping(\"/users\") on the UserController class, then use @GetMapping(\"/{id}\") on the method");
    }

    @Test
    void testEndpointUsesGetMappingOnMethod() throws IOException {
        String content = readFile("UserController.java");
        assertTrue(content.contains("@GetMapping"),
                "Use @GetMapping(\"/{id}\") on the method to handle GET /users/{id}");
    }

    @Test
    void testEndpointUsesPathVariable() throws IOException {
        String content = readFile("UserController.java");
        assertTrue(content.contains("@PathVariable"),
                "Use @PathVariable Long id to extract the user ID from the URL");
    }

    @Test
    void testControllerReturnsDTONotEntity() throws IOException {
        String content = readFile("UserController.java");
        assertTrue(content.contains("UserDTO"),
                "Return UserDTO from the controller, not the User entity directly");
    }

    @Test
    void testServiceIsInterface() throws IOException {
        String content = readFile("UserService.java");
        assertTrue(content.contains("interface"),
                "UserService should be an interface, not a class");
    }

    @Test
    void testServiceImplHasServiceAnnotation() throws IOException {
        String content = readFile("UserServiceImpl.java");
        assertTrue(content.contains("@Service"),
                "UserServiceImpl must be annotated with @Service");
    }

    @Test
    void testUserModelHasRequiredFields() throws IOException {
        String content = readFile("User.java");
        assertTrue(content.contains("name") && content.contains("email") && content.contains("id"),
                "User model must have id, name, and email fields");
    }

    @Test
    void testTestsUseWebMvcTest() throws IOException {
        List<Path> testFiles = findFiles(TEST, ".java");
        boolean found = testFiles.stream().anyMatch(f -> {
            try { return Files.readString(f).contains("@WebMvcTest"); }
            catch (IOException e) { return false; }
        });
        assertTrue(found, "Use @WebMvcTest to test your controller in isolation — it's faster and more focused than @SpringBootTest");
    }

    @Test
    void testTestsUseMockMvc() throws IOException {
        List<Path> testFiles = findFiles(TEST, ".java");
        boolean found = testFiles.stream().anyMatch(f -> {
            try { return Files.readString(f).contains("MockMvc"); }
            catch (IOException e) { return false; }
        });
        assertTrue(found, "Inject MockMvc in your test and use mockMvc.perform() to simulate HTTP requests");
    }

    private String readFile(String fileName) throws IOException {
        List<Path> result = new ArrayList<>();
        if (!Files.exists(SRC)) return "";
        Files.walkFileTree(SRC, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (file.getFileName().toString().equals(fileName)) result.add(file);
                return FileVisitResult.CONTINUE;
            }
        });
        return result.isEmpty() ? "" : Files.readString(result.get(0));
    }

    private List<Path> findFiles(Path dir, String extension) throws IOException {
        List<Path> result = new ArrayList<>();
        if (!Files.exists(dir)) return result;
        Files.walkFileTree(dir, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (file.toString().endsWith(extension)) result.add(file);
                return FileVisitResult.CONTINUE;
            }
        });
        return result;
    }
}