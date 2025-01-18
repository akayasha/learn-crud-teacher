package com.example;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class TeacherService {


    private final TeacherRepository teacherRepository;


    @Inject
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<TeacherDTO> findAll() {
        return teacherRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    public Optional<TeacherDTO> findByNip(String nip) {
        return teacherRepository.findByNipOptional(nip)
                .map(this::toDomain);
    }

    public Optional<TeacherDTO> findByNameOptional(String name) {
        return teacherRepository.findByNameOptional(name)
                .map(this::toDomain);
    }

    public Optional<TeacherDTO> findBysubjects(String subjects) {
        return teacherRepository.findBySubjects(subjects)
                .map(this::toDomain);
    }


    @Transactional
    public TeacherDTO save(TeacherDTO teacherDTO) {
        validateTeacher(teacherDTO);

        if (isDuplicate(teacherDTO.getNip(), teacherDTO.getEmail(), teacherDTO.getPhoneNo())) {

            throw new ServiceException("Duplicate Nip, Email,or Phone Number is Duplicate");
        }

        Teacher entity = toEntity(teacherDTO);
        teacherRepository.persist(entity);
        return toDomain(entity);

    }

    @Transactional
    public TeacherDTO update(TeacherDTO teacherDTO) {
        if (teacherDTO.getNip() == null) {
            throw new ServiceException("NIP must not be null");
        }

        // Fetch the teacher using Panache's repository method
        Teacher teacher = teacherRepository.find("nip", teacherDTO.getNip()).firstResult();
        if (teacher == null) {
            throw new ServiceException("No teacher found with NIP: " + teacherDTO.getNip());
        }

        // Update the fields
        teacher.setName(teacherDTO.getName());
        teacher.setDob(teacherDTO.getDob());
        teacher.setClasses(teacherDTO.getClasses());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setPhoneNo(teacherDTO.getPhoneNo());
        teacher.setSubjects(teacherDTO.getSubjects());

        // No need to call save; Panache persists changes automatically
        return toDomain(teacher);
    }

    public boolean deleteByNip(String nip) {
        Optional<Teacher> teacher = teacherRepository.findByNipOptional(nip);
        if (teacher.isPresent()) {
            teacherRepository.delete(teacher.get());
            return true;
        }
        return false;
    }


    // Manual mapping from Student to StudentDto
    private TeacherDTO toDomain(Teacher teacher) {
        TeacherDTO dto = new TeacherDTO();
        dto.setNip(teacher.getNip());
        dto.setName(teacher.getName());
        dto.setClasses(teacher.getClasses());
        dto.setDob(teacher.getDob());
        dto.setPhoneNo(teacher.getPhoneNo());
        dto.setEmail(teacher.getEmail());
        dto.setSubjects(teacher.getSubjects());
        return dto;
    }

    // Manual mapping from StudentDto to Student
    private Teacher toEntity(TeacherDTO dto) {
        Teacher entity = new Teacher();
        entity.setNip(dto.getNip());
        entity.setName(dto.getName());
        entity.setClasses(dto.getClasses());
        entity.setDob(dto.getDob());
        entity.setPhoneNo(dto.getPhoneNo());
        entity.setEmail(dto.getEmail());
        entity.setSubjects(dto.getSubjects());
        return entity;
    }

    private void validateTeacher(TeacherDTO teacherDTO) {
        if (teacherDTO.getNip() == null || teacherDTO.getEmail() == null || teacherDTO.getPhoneNo() == null) {
            throw new ServiceException("NIP, Email, and Phone Number must not be null");
        }
    }

    private boolean isDuplicate(String nip, String email, String phoneNo) {
        return teacherRepository.find("nip = ?1 or email = ?2 or phoneNo = ?3", nip, email, phoneNo)
                .firstResultOptional()
                .isPresent();
    }

}
