package um.fds.agl.ter23.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import um.fds.agl.ter23.entities.Sujet;

public interface SujetRepository <T extends Sujet> extends CrudRepository<T, Long> {

    public T findByTitre(String titre);

    @Override
    @PreAuthorize("hasRole('ROLE_MANAGER') or (#sujet?.teacher == null or #sujet?.teacher == authentication?.name)")
    Sujet save(@Param("sujet") Sujet sujet);

    @Override
    @PreAuthorize("@sujetRepository.findById(#id).get()?.teacher == authentication?.name or hasRole('ROLE_MANAGER')")
    void deleteById(@Param("id") Long id);

    @Override
    @PreAuthorize("#sujet?.teacher == authentication?.name")
    void delete(@Param("sujet") Sujet sujet);
}