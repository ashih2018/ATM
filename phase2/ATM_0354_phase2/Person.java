package ATM_0354_phase2;

public abstract class Person {

    private String username;
    private String hash;
    private String salt;

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

    public void setPassword(String password) {
        salt = PasswordHash.getSalt().toString();
        hash = PasswordHash.hashPassword(password, this.salt);
    }

    public String getHash() {
        return hash;
    }

    String getSalt() {
        return salt;
    }

}
