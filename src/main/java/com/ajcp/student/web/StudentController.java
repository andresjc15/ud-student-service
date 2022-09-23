package com.ajcp.student.web;

import com.ajcp.student.entity.Student;
import com.ajcp.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<?> getStudents() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/page")
    public ResponseEntity<?> getStudents(Pageable pageable) {
        return ResponseEntity.ok(studentService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable Long id) {
        return studentService.findById(id)
                .map(obj -> ResponseEntity.status(HttpStatus.OK).body(obj))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/uploads/img/{id}")
    public ResponseEntity<?> watchPicture(@PathVariable Long id) {

        Optional<Student> o = studentService.findById(id);

        if (o.isEmpty() || o.get().getProfilePicture() == null) {
            return ResponseEntity.notFound().build();
        }

        Resource image = new ByteArrayResource(o.get().getProfilePicture());

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody Student student, BindingResult result) {
        if (result.hasErrors()) {
            return this.validate(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.save(student));
    }

    @PostMapping("/complete")
    public ResponseEntity<?> registerComplete(@Valid Student student, BindingResult result
            , @RequestParam MultipartFile file) throws IOException {
        if (result.hasErrors()) {
            return this.validate(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.save(student, file));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.update(student));
    }

    @PutMapping("/complete")
    public ResponseEntity<?> updateWithPicture(@RequestBody Student student, @RequestParam MultipartFile file)
            throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.updateWithPicture(student, file));
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

    protected ResponseEntity<?> validate(BindingResult result) {
        Map<String, Object> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
