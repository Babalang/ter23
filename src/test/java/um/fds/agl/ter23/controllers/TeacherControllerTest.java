package um.fds.agl.ter23.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import um.fds.agl.ter23.entities.TERManager;
import um.fds.agl.ter23.entities.Teacher;
import um.fds.agl.ter23.repositories.TeacherRepository;
import um.fds.agl.ter23.services.TERManagerService;
import um.fds.agl.ter23.services.TeacherService;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;
import static org.springframework.security.config.http.MatcherType.mvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TeacherControllerTest {
    @Autowired
    private MockMvc mvc1;
    @Test
    @WithMockUser(username = "Chef", roles = "MANAGER")
    void addTeacherGet() throws Exception {
        MvcResult result = mvc1.perform(get("/addTeacher"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                        .andExpect(view().name("addTeacher"))
                        .andReturn();
    }
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TERManagerService TERService;
    @Autowired
    private TeacherRepository teacherRepository;
    @Test
    @WithMockUser(username = "Chef", roles = "MANAGER")
    void addTeacherPostNonExistingTeacher() throws Exception {
        assertTrue(teacherService.getTeacher(9L).isEmpty());
        assumingThat(teacherService.getTeacher(9L).isEmpty(), ()->{;
        MvcResult result = mvc1.perform(post("/addTeacher")
                        .param("firstName", "Anne-Marie")
                        .param("lastName", "Kermarrec")
                        .param("id", "9"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        // il faut ici vérifier que le nouvel enseignant a bien été ajouté
            assertFalse(teacherService.getTeacher(9L).isEmpty());
    });
    }

    @Test
    @WithMockUser(username = "Chef", roles = "MANAGER")
    void addTeacherExistingTeacher() throws Exception {
        assertTrue(teacherService.getTeacher(2L).isPresent());
            MvcResult result = mvc1.perform(post("/addTeacher")
                            .param("firstName", "Anne-Marie")
                            .param("lastName", "Kermarrec")
                            .param("id", "2"))
                    .andExpect(status().is3xxRedirection())
                    .andReturn();
            // il faut ici vérifier que le nouvel enseignant a bien été ajouté
        assertTrue((teacherService.getTeacher(2L).get()).getLastName().equals("Kermarrec"));
    }
}