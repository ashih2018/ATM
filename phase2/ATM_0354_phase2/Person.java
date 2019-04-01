package ATM_0354_phase2;

public abstract class Person {

    private String username;
    private String hash;
    private String salt;
    protected String email;

    public Person(String username, String password, String salt) {
        this.username = username;
        if (salt == null) {
            this.setPassword(password);
        }
        else {
            this.salt = salt;
            this.hash = password;
        }
    }

    public String getUsername() {
        return username;
    }
    public boolean hasEmail(){
        return !this.email.equals("");
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }
    public void setPassword(String password) {
        this.salt = PasswordHash.getSalt().toString();
        this.hash = PasswordHash.hashPassword(password, this.salt);
    }

    public String getHash() {
        return this.hash;
    }

    String getSalt() {
        return this.salt;
    }

}
