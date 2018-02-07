package org.sjimenez.chatapp.model;



import javax.persistence.Convert;
import java.time.LocalDate;
import java.util.Objects;

public class User {

    private int iduser;
    private String name;
    private String lastName;
    private String mail;


    private LocalDate birthdate;
    private String nickname;

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getIduser() == user.getIduser() &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getLastName(), user.getLastName()) &&
                Objects.equals(getMail(), user.getMail()) &&
                Objects.equals(getBirthdate(), user.getBirthdate()) &&
                Objects.equals(getNickname(), user.getNickname());
    }

}
