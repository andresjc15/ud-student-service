package com.ajcp.student.service;

import com.ajcp.student.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    public List<Student> findAll();

    public Optional<Student> findById(Long id);

    public Student save(Student student);

    public Student update(Student student);

    public void delete(Long id);

    public List<Student> findByNameOrLastName(String term);

}
