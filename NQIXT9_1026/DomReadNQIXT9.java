package domnqixt91026;

public class User {
    private int id;
    private String firstname;
    private String lastname;
    private String profession;

    public User(int id, String firstname, String lastname, String profession) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.profession = profession;
    }

    @Override
    public String toString() {
        return "firstname=" + firstname + ", profession=" + profession + ", lastname=" + lastname;
    }

    public User() {
    }

    public String getfirstname() {
        return firstname;
    }

    public void setfirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getlastname() {
        return lastname;
    }

    public void setlastname(String lastname) {
        this.lastname = lastname;
    }

    public String getprofession() {
        return profession;
    }

    public void setprofession(String profession) {
        this.profession = profession;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}