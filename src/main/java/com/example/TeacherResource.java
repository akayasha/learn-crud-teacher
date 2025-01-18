package com.example;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.logging.Logger;

@Path("/api/teacher")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeacherResource {

    private static final Logger LOGGER = Logger.getLogger(TeacherResource.class.getName());
    private final TeacherService teacherService;

    public TeacherResource(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GET
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Retrieve all Teacher",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = SchemaType.ARRAY, implementation = TeacherDTO.class))
            )
    })
    public Response GetAllTeacher() {
        LOGGER.info("Fetching all Teacher");
        return ResponseHandler.generateResponse(
                200, "Teacher retrieved successfully", teacherService.findAll());
    }


    @GET
    @Path("/{subjects}")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Retrieve Teacher by Classes",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = SchemaType.OBJECT, implementation = TeacherDTO.class))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Teacher not found for the provided Classes",
                    content = @Content(mediaType = "application/json")
            )
    })
    public Response GetTeacherBySubjects(@PathParam("subjects") String subjects) {
        LOGGER.info("Fetching Teacher by Classes" + subjects);
        return teacherService.findBysubjects(subjects)
                .map(teacherDTO -> ResponseHandler.generateResponse(200,"Teacher retrieved succesfully",teacherDTO))
                .orElseGet(() -> ResponseHandler.generateResponse(404,"Teacher not found","{}"));
    }


    @GET
    @Path("/{nip}")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Retrieve Teacher by NIM",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = SchemaType.OBJECT, implementation = TeacherDTO.class))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Teacher not found for the provided NIM",
                    content = @Content(mediaType = "application/json")
            )
    })
    public Response GetTeacherByNip(@PathParam("nip") String nip) {
        LOGGER.info("Fetching Teacher with NIP: " + nip);
        return teacherService.findByNip(nip)
                .map(teacher -> ResponseHandler.generateResponse(200, "Teacher retrieved successfully", teacher))
                .orElseGet(() -> ResponseHandler.generateResponse(404, "Teacher not found", "{}"));
    }

    @POST
    @APIResponses({
            @APIResponse(
                    responseCode = "201",
                    description = "Teacher created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = SchemaType.OBJECT, implementation = TeacherDTO.class))
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Invalid Teacher data",
                    content = @Content(mediaType = "application/json")
            )
    })
    public Response teacherCreate(TeacherDTO teacherDTO) {
        LOGGER.info("Creating Teacher: " + teacherDTO);
        try {
            TeacherDTO createdTeacher = teacherService.save(teacherDTO);
            return ResponseHandler.generateResponse(201, "Teacher created successfully", createdTeacher);
        } catch (ServiceException e) {
            LOGGER.severe("Error creating teacher : " + e.getMessage());
            return ResponseHandler.generateErrorResponse(e.getMessage());
        }
    }

    @PUT
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Teacher updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = SchemaType.OBJECT, implementation = TeacherDTO.class))
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Invalid Teacher data",
                    content = @Content(mediaType = "application/json")
            )
    })
    public Response updateTeacher(@Valid TeacherDTO teacherDTO) {
        LOGGER.info("Updating Teacher: " + teacherDTO);
        try {
            TeacherDTO updateTeacher = teacherService.update(teacherDTO);
            return ResponseHandler.generateResponse(200, "Teacher updated successfully", updateTeacher);
        } catch (ServiceException e) {
            LOGGER.severe("Error updating Teacher: " + e.getMessage());
            return ResponseHandler.generateErrorResponse(e.getMessage());
        }
    }

    @DELETE
    @Path("/{nim}")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Teacher deleted successfully",
                    content = @Content(mediaType = "application/json")
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Teacher not found",
                    content = @Content(mediaType = "application/json")
            )
    })
    public Response deleteTeacher(@PathParam("nim") String nip) {
        LOGGER.info("Deleting Teacher with NIM: " + nip);
        if (teacherService.deleteByNip(nip)) {
            return ResponseHandler.generateResponse(200, "Teacher deleted successfully", "{}");
        } else {
            return ResponseHandler.generateResponse(404, "Teacher not found", "{}");
        }
    }
}
