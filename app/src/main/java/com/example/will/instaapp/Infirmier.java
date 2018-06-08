package com.example.will.instaapp;

import java.io.Serializable;
/**
 * Created by etien on 30/04/2018.
 */

public class Infirmier implements Serializable{
    String etat_civil, nom, prenom, date_naissance, adresse, adresesecomp, code_postal, Ville, telephone, login, email, urlphoto, nomService, password;
    int idService;


    public Infirmier(String etat_civil, String nom, String prenom, String date_naissance, String adresse, String adresesecomp,
                     String code_postal, String Ville, String telephone, String login, String email, String urlphoto,
                     String nomService, int idService) {
        this.etat_civil = etat_civil;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
        this.adresse = adresse;
        this.adresesecomp = adresesecomp;
        this.code_postal = code_postal;
        this.Ville = Ville;
        this.telephone = telephone;
        this.login = login;
        this.email = email;
        this.urlphoto = urlphoto;
        this.nomService = nomService;
        this.idService = idService;
    }

    public Infirmier (String login, String password)
    {
        this.login = login;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEtat_civil() {
        return etat_civil;
    }

    public String getVille() {
        return Ville;
    }

    public void setVille(String ville) {
        Ville = ville;
    }

    public void setEtat_civil(String etat_civil) {
        this.etat_civil = etat_civil;
    }

    public String getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(String date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getAdresesecomp() {
        return adresesecomp;
    }

    public void setAdresesecomp(String adresesecomp) {
        this.adresesecomp = adresesecomp;
    }

    public String getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    public String getVIlle() {
        return Ville;
    }

    public void setVIlle(String VIlle) {
        this.Ville = VIlle;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUrlphoto() {
        return urlphoto;
    }

    public void setUrlphoto(String urlphoto) {
        this.urlphoto = urlphoto;
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
