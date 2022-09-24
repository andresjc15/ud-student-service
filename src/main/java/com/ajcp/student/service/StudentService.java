package com.ajcp.student.service;

import com.ajcp.student.entity.Student;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface StudentService {

    public List<Student> findAll();

    public Page<Student> findAll(Pageable pageable);

    public Optional<Student> findById(Long id);

    public Optional<Resource> getProfilePicture(Long id);

    public Student save(Student student);

    public Student save(Student student, MultipartFile file) throws IOException;

    public Student update(Student student);

    public Student updateWithPicture(Student student, MultipartFile file) throws IOException;

    public void delete(Long id);

    public List<Student> findByNameOrLastName(String term);

}
