package com.ajcp.student.web;

import com.ajcp.student.entity.Student;
import com.ajcp.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<?> getStudents() {
        return new ResponseEntity<List<Student>>(studentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable Long id) {
        return studentService.findById(id)
                .map(obj -> ResponseEntity.status(HttpStatus.OK).body(obj))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.save(student));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.update(student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter/{term}")
    public ResponseEntity<?> filterByName(@PathVariable String term) {
        List<Student> students = studentService.findByNameOrLastName(term);
        return (!students.isEmpty()) ? ResponseEntity.ok().body(students) : ResponseEntity.notFound().build();

    }

}
