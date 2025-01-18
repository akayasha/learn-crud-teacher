package com.example;

import org.mapstruct.Mapper;

@Mapper(componentModel = "cli")
public interface TeacherMapper {


    Teacher toEntity(TeacherDTO domain);

    TeacherDTO toDomain(Teacher entity);

}
