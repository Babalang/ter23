package um.fds.agl.ter23.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import um.fds.agl.ter23.entities.Sujet;
import um.fds.agl.ter23.repositories.SujetRepository;

import java.util.Optional;
@Service
public class SujetService {

    @Autowired
    private SujetRepository<Sujet> sujetRepository;

    public Optional<Sujet> getSujet(final Long id) {
        return sujetRepository.findById(id);
    }

    public Iterable<Sujet> getSujets() {
        return sujetRepository.findAll();
    }

    public void deleteSujet(Long id) {
        sujetRepository.deleteById(id);
    }

    public Sujet saveSujet(Sujet sujet) {
        Sujet savedSujet = sujetRepository.save(sujet);
        return savedSujet;
    }

    public Optional<Sujet> findById(long id) {
        return sujetRepository.findById(id);
    }
}
