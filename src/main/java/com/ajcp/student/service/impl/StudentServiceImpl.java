package com.ajcp.student.service.impl;

import com.ajcp.student.entity.Student;
import com.ajcp.student.repository.StudentRepository;
import com.ajcp.student.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Student> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Optional<Resource> getProfilePicture(Long id) {
        Optional<Student> o = studentRepository.findById(id);

        if (o.isEmpty() || o.get().getProfilePicture() == null) return Optional.empty();

        Resource image = new ByteArrayResource(o.get().getProfilePicture());
        return Optional.of(image);
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student save(Student student, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            student.setProfilePicture(file.getBytes());
        }
        return studentRepository.save(student);
    }

    @Override
    public Student update(Student student) {
        return studentRepository.findById(student.getId()).map(st -> {
            st.setName(student.getName());
            st.setLastName(student.getLastName());
            st.setEmail(student.getEmail());
            return studentRepository.save(st);
        }).orElse(null);
    }

    @Override
    public Student updateWithPicture(Student student, MultipartFile file) throws IOException {
        return studentRepository.findById(student.getId()).map(st -> {
            st.setName(student.getName());
            st.setLastName(student.getLastName());
            st.setEmail(student.getEmail());

            try {
                st.setProfilePicture(file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return studentRepository.save(st);
        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findByNameOrLastName(String term) {
        return studentRepository.findByNameOrLastNameContaining(term, term);
    }

}
