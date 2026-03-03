package grader;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

class StructureTest {

    private static final Path ROOT = Path.of(System.getProperty("user.dir"));
    private static final Path SRC  = ROOT.resolve("src/main/java");

    @Test
    void testSrcHasJavaFiles() throws IOException {
        List<Path> files = findFiles(SRC, ".java");
        assertFalse(files.isEmpty(), "No .java files found in src/main/java/");
    }

    @Test
    void testHasSpringBootApplication() throws IOException {
        List<Path> files = findFiles(SRC, ".java");
        boolean found = files.stream().anyMatch(f -> {
            try { return Files.readString(f).contains("@SpringBootApplication"); }
            catch (IOException e) { return false; }
        });
        assertTrue(found, "No class with @SpringBootApplication found in src/main/java/");
    }

    @Test
    void testHasUserModel() throws IOException {
        List<Path> files = findFiles(SRC, ".java");
        boolean found = files.stream().anyMatch(f -> f.getFileName().toString().equals("User.java"));
        assertTrue(found, "Create a User.java model class in src/main/java/");
    }

    @Test
    void testHasUserDTO() throws IOException {
        List<Path> files = findFiles(SRC, ".java");
        boolean found = files.stream().anyMatch(f -> f.getFileName().toString().equals("UserDTO.java"));
        assertTrue(found, "Create a UserDTO.java class in src/main/java/ — controllers should return DTOs, not entities");
    }

    @Test
    void testHasUserServiceInterface() throws IOException {
        List<Path> files = findFiles(SRC, ".java");
        boolean found = files.stream().anyMatch(f -> f.getFileName().toString().equals("UserService.java"));
        assertTrue(found, "Create a UserService.java interface in src/main/java/");
    }

    @Test
    void testHasUserServiceImpl() throws IOException {
        List<Path> files = findFiles(SRC, ".java");
        boolean found = files.stream().anyMatch(f -> f.getFileName().toString().equals("UserServiceImpl.java"));
        assertTrue(found, "Create a UserServiceImpl.java that implements UserService");
    }

    @Test
    void testHasUserController() throws IOException {
        List<Path> files = findFiles(SRC, ".java");
        boolean found = files.stream().anyMatch(f -> f.getFileName().toString().equals("UserController.java"));
        assertTrue(found, "Create a UserController.java in src/main/java/");
    }

    @Test
    void testHasGlobalExceptionHandler() throws IOException {
        List<Path> files = findFiles(SRC, ".java");
        boolean found = files.stream().anyMatch(f -> {
            try { return Files.readString(f).contains("@ControllerAdvice"); }
            catch (IOException e) { return false; }
        });
        assertTrue(found, "Create a GlobalExceptionHandler class with @ControllerAdvice to handle 404 responses");
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