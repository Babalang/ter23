package um.fds.agl.ter23.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import um.fds.agl.ter23.entities.Sujet;
import um.fds.agl.ter23.entities.Teacher;
import um.fds.agl.ter23.services.SujetService;
import um.fds.agl.ter23.forms.SujetForm;
import um.fds.agl.ter23.services.TeacherService;


@Controller
public class SujetControleur {
    @Autowired
    private TeacherService TeacherService;
    @Autowired
    private SujetService sujetService;

    @GetMapping("/listSujets")
    public Iterable<Sujet> getSujets(Model model) {
        Iterable<Sujet> sujets=sujetService.getSujets();
        model.addAttribute("sujets", sujets);
        return sujets;
    }
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole(('ROLE_TEACHER'))")
    @GetMapping(value = { "/addSujet" })
    public String showAddSujetPage(Model model) {

        SujetForm sujetForm = new SujetForm();
        model.addAttribute("sujetForm", sujetForm);

        return "addSujet";
    }

    @PostMapping(value = { "/addSujet"})
    public String addSujet(Model model, @ModelAttribute("SujetForm") SujetForm sujetForm) {
        Sujet t;
        Long te = null;
        if(sujetService.findById(sujetForm.getId()).isPresent()){
            System.out.println("hello");
            // subject already existing : update
            t = sujetService.findById(sujetForm.getId()).get();
            t.setTitre(sujetForm.getTitre());
            sujetService.saveSujet(t);
        } else {
            for (Teacher teach : TeacherService.getTeachers()) {
                if (teach.getLastName().equals(sujetForm.getTeacher())){
                   te = teach.getId();
                   System.out.println(te);
                }
            }
            if (te != null) {
                t=new Sujet(sujetForm.getTitre(),TeacherService.findById(te).get(), sujetForm.getEncadrant());
                sujetService.saveSujet(t);
            }
            // subject not existing : create
        }
        return "redirect:/listSujets";

    }

    @GetMapping(value = {"/showSujetUpdateForm/{id}"})
    public String showSujetUpdateForm(Model model, @PathVariable(value = "id") long id){
        SujetForm sujetForm = new SujetForm(id, sujetService.findById(id).get().getTitre(), sujetService.findById(id).get().getTeacher(),sujetService.findById(id).get().getEncadrant());
        model.addAttribute("sujetForm", sujetForm);
        return "updateSujets";
    }

    @GetMapping(value = {"/deleteSujet/{id}"})
    public String deleteSujet(Model model, @PathVariable(value = "id") long id){
        sujetService.deleteSujet(id);
        return "redirect:/listSujets";
    }
}
