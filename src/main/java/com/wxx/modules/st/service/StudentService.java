package com.wxx.modules.st.service;

import com.wxx.modules.st.domain.Student;
import com.wxx.modules.st.mapper.StudentMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StudentService {

    @Resource
    StudentMapper studentMapper;

    private Map<String, Student> StudentMap = new ConcurrentHashMap<>();

    public Mono<Student> addStudent(Mono<Student> Student) {
        Optional<String> max = StudentMap.keySet().stream().max(String::compareTo);
        return  Student.flatMap(StudentElement -> {
            StudentMap.put(String.valueOf(Integer.valueOf(max.get() + 1)), StudentElement);
            return Mono.just(StudentElement);
        });
    }

    public Mono<Student> findStudentById(Integer id) {
        return Mono.just(studentMapper.findStudentById(id));
    }

    public Flux<Student> findStudentList() {
        List<Student> StudentList = new ArrayList<>();
        StudentMap.entrySet().stream().forEach(entry -> StudentList.add(entry.getValue()));
        return Flux.fromStream(StudentList.stream());
    }

    public void setStudent(String StudentId, Student Student) {
        StudentMap.put(StudentId, Student);
    }

}

