package Model;

public class Administrador {

    private String id;
    private String password;

    public Administrador() {
        this.id = "administrador";
        this.password = "administrador";
    }

    public Administrador(String id, String password){
        this.id=id;
        this.password=password;
    }

    public Administrador(Administrador administrador){
        this.id=administrador.getId();
        this.password=administrador.getPassword();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {return "ID: " + this.id;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Administrador a = (Administrador) o;
        return this.id.equals(a.getId()) && this.password.equals(a.getPassword());
    }

    @Override
    public Administrador clone() {
        return new Administrador(this);
    }
}
