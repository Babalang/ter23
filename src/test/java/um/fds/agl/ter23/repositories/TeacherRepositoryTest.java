package um.fds.agl.ter23.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import um.fds.agl.ter23.entities.TERManager;
import um.fds.agl.ter23.entities.Teacher;
import org.springframework.security.access.AccessDeniedException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class TeacherRepositoryTest {
    @Autowired
    private TeacherRepository teachers;
    @Autowired
    private TERManagerRepository managers;

    @Test
    void savingTeachersIsPossibleForManager() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("lechef", "peu importe", AuthorityUtils.createAuthorityList("ROLE_MANAGER")));
                        TERManager terM1Manager = new TERManager("Mathieu", "lechef", "mdp", "ROLE_MANAGER");
        this.managers.save(terM1Manager);
        this.teachers.save(new Teacher("Margaret", "Hamilton", "margaret",terM1Manager, "ROLE_TEACHER"));
        assertThat(teachers.findByLastName("Hamilton"),is(notNullValue()));
    }

    @Test
    void savingTeachersIsNotPossibleForTeacher() throws AccessDeniedException{
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("Turing", "peu importe", AuthorityUtils.createAuthorityList("ROLE_TEACHER")));
        TERManager terM1Manager = new TERManager("Mathieu", "lechef", "mdp", "ROLE_MANAGER");
        this.managers.save(terM1Manager);
        try{
            this.teachers.save(new Teacher("Margaret", "Hamilton", "margaret",terM1Manager, "ROLE_TEACHER"));
        }catch (AccessDeniedException e) {
            // L'exception AccessDeniedException est levée comme prévu
            return;
        }
        // Si aucune exception n'a été levée ou si l'utilisateur n'a pas le rôle "ROLE_TEACHER", le test échoue
        fail("L'exception AccessDeniedException n'a pas été levée ou l'utilisateur n'a pas le rôle 'ROLE_TEACHER'.");
    }
}