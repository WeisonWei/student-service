package com.wxx.modules.st.handler;

import com.wxx.modules.st.domain.Student;
import com.wxx.modules.st.openmq.TopicPublisherHandler;
import com.wxx.modules.st.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class StudentHandler {

    @Autowired
    StudentService studentService;

    @Autowired
    TopicPublisherHandler topicPublisherHandler;

    public Mono<ServerResponse> getStudentList(ServerRequest request) {
        Flux<Student> StudentFlux = studentService.findStudentList();
        StudentFlux.flatMap(Student -> {
            log.info(Student.toString());
            return Mono.just(Student);
        });
        return ServerResponse.ok().body(StudentFlux, Student.class);
    }

    public Mono<ServerResponse> getStudent(ServerRequest request) {
        Integer StudentId = Integer.valueOf(request.pathVariable("id"));
        Mono<Student> StudentMono = studentService.findStudentById(StudentId);
        StudentMono.flatMap(Student -> {
            log.info(Student.toString());
            return Mono.just(Student);
        });
        return ServerResponse.ok().body(StudentMono, Student.class);
    }

    public Mono<ServerResponse> addStudent(ServerRequest request) {
        Mono<Student> StudentMono = request.bodyToMono(Student.class);
        Mono<Student> StudentMonoResult = studentService.addStudent(StudentMono);
        return ServerResponse.ok().body(StudentMonoResult, Student.class);
    }

    public Mono<ServerResponse> syncStudentUpdate(ServerRequest request) {
        Mono<Student> StudentMono = request.bodyToMono(Student.class);
        Mono<String> resultMono = topicPublisherHandler.sendMessage(StudentMono);
        return ServerResponse.ok().body(resultMono, String.class);
    }
}
 
