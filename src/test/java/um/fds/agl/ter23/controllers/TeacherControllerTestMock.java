package um.fds.agl.ter23.controllers;

import org.aspectj.lang.annotation.Before;
import org.hibernate.annotations.Any;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import um.fds.agl.ter23.entities.TERManager;
import um.fds.agl.ter23.entities.Teacher;
import um.fds.agl.ter23.repositories.TeacherRepository;
import um.fds.agl.ter23.services.TeacherService;


import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TeacherControllerTestMock {
    @Captor
    ArgumentCaptor<Teacher> captor;

    @Before("")
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @MockBean
    TeacherService teacherService;
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

    @Test
    @WithMockUser(username = "Chef", roles = "MANAGER")
    void addTeacherPostNonExistingTeacher() throws Exception {
        assertTrue(teacherService.getTeacher(9L).isEmpty());
        assumingThat(teacherService.getTeacher(9L).isEmpty(), () -> {
            ;
            MvcResult result = mvc1.perform(post("/addTeacher")
                            .param("firstName", "Anne-Marie")
                            .param("lastName", "Kermarrec")
                            .param("id", "9"))
                    .andExpect(status().is3xxRedirection())
                    .andReturn();
            // Vérifie si la méthode de sauvegarde a été appelée avec les bonnes valeurs
            verify(teacherService).saveTeacher(any(Teacher.class));

        });
    }
    @Test
    @WithMockUser(username = "Chef", roles = "MANAGER")
    void addTeacherPostExistingTeacher() throws Exception {
        Teacher teacherBase = new Teacher("Anne-Marie", "Kermarrec", null);
        Teacher teacherBase2 = new Teacher("Samuel", "CASAS DRAY", null);
        teacherBase.setId(9L);
        teacherBase2.setId(9L);
        when(teacherService.getTeacher(9L)).thenReturn(Optional.of(teacherBase));
        Optional<Teacher> teacher = teacherService.getTeacher(9L);
        assertEquals(teacher.get().getLastName(), teacherBase.getLastName());
        assertEquals(teacher.get().getFirstName(), teacherBase.getFirstName());
        mvc1.perform(post("/addTeacher")
                        .param("firstName", teacherBase2.getFirstName())
                        .param("lastName", teacherBase2.getLastName())
                        .param("id", String.valueOf(teacherBase2.getId()))
                )
                .andExpect(status().is3xxRedirection())
                .andReturn();
        verify(teacherService).saveTeacher(any(Teacher.class));
        when(teacherService.getTeacher(9L)).thenReturn(Optional.of(teacherBase2));
        teacher = teacherService.getTeacher(9L);
        assertEquals(teacher.get().getLastName(), teacherBase2.getLastName());
        assertEquals(teacher.get().getFirstName(), teacherBase2.getFirstName());
    }
}
