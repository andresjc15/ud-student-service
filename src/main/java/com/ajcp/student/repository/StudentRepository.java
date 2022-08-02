package com.ajcp.student.repository;

import com.ajcp.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    public List<Student> findByNameOrLastNameContaining(String name, String lastName);

}
