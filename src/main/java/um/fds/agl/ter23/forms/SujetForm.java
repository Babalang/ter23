package um.fds.agl.ter23.forms;

public class SujetForm {
    private String titre;
    private String teacher;



    private String encadrant;
    private long id;
    
    public SujetForm(long id, String titre, String teacher,String encadrant) {
        this.titre = titre;
        this.teacher = teacher;
        this.id = id;
        this.encadrant = encadrant;
    }
    public String getEncadrant() {
        return encadrant;
    }
    public void setEncadrant(String encadrant) {
        this.encadrant = encadrant;
    }

    public SujetForm() {}
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
