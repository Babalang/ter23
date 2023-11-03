package um.fds.agl.ter23.entities;

import javax.persistence.*;

@Entity
public class Sujet{
    private @ManyToOne Teacher teacher;
    private String titre;

    private @Id     @GeneratedValue Long id;



    private String encadrant;

    public Sujet(String title, Teacher teacher, String encadrant) {
        this.teacher = teacher;
        this.titre = title;
        this.encadrant = encadrant;
    }

    public Sujet() {
    }
    public String getEncadrant() {
        return encadrant;
    }

    public void setEncadrant(String encadrant) {
        this.encadrant = encadrant;
    }

    public String getTeacher() {return teacher.getLastName();}

    public String getTitre() {
        return titre;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setTitre(String title) {
        this.titre = title;
    }

    @Override
    public String toString() {
        return "Sujet{" +
                "teacher=" + teacher +
                ", title='" + titre + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sujet)) return false;
        if (!super.equals(o)) return false;

        Sujet sujet = (Sujet) o;

        return getTeacher() != null ? getTeacher().equals(sujet.getTeacher()) : sujet.getTeacher() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getTeacher() != null ? getTeacher().hashCode() : 0);
        return result;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
