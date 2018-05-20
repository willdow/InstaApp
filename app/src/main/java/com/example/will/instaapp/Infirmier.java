package com.example.will.instaapp;

/**
 * Created by etien on 30/04/2018.
 */

public class Infirmier {
    String nom, prenom, email, login, password, nomService;
    int idService;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Infirmier(String nom, String prenom, String email, String login, String password, String nomService, int idService)
    {
        this.nom = nom;
        this.prenom = prenom;

        this.email = email;
        this.login = login;
        this.password = password;
        this.nomService = nomService;
        this.idService = idService;
    }

    public Infirmier()
    {
        this.nom = this.prenom = this.email = this.nomService = "";
        this.idService = 0;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomService() {
        return nomService;
    }

    public void setNomService(String nomService) {
        this.nomService = nomService;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getIdService() {

        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }
}
