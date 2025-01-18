package com.example;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class TeacherRepository implements PanacheRepositoryBase<Teacher, String> {

    public Optional<Teacher> findByNipOptional(String nip) {
        return find("nip", nip).firstResultOptional();
    }

    public Optional<Teacher> findByNameOptional(String name) {
        return find("name", name).firstResultOptional();
    }

    public Optional<Teacher> findBySubjects(String subjects) {
        return find("subjects",subjects).firstResultOptional();
    }

}
