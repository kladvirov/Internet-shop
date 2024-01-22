package org.example.model;

import java.time.LocalDate;

public class User {
    private Long id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String login;
    private String password;
    private Boolean isBlocked;

    public User(Long id, String name, String surname, LocalDate birthDate, String login, String password, Boolean isBlocked) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.login = login;
        this.password = password;
        this.isBlocked = isBlocked;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Boolean isBlocked() {
        return isBlocked;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBlocked(Boolean blocked) {
        this.isBlocked = blocked;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birth_date=" + birthDate +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", is_blocked=" + isBlocked +
                '}';
    }
}
