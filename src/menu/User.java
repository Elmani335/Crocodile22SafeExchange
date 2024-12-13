package menu;

public class User {
    String name;
    public String salt;

    public User(String name, String salt){
        this.name = name;
        this.salt = salt;
    }
    public String getSalt(){
        return this.salt;
    }
}
