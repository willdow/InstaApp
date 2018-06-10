package com.example.will.instaapp;

public class Patient {
    private String nom;
    private String prenom;
    private String date_naissance;
    private String GroupeSanguin;
    private String motif;
    private String nomService;
    private String chambreComplete;
    private int etage;
    private int chambre;
    private int poids;
    private int idPatient;
    private int taille;
    private int idHospi;

    public Patient(int idPatient, int idHospi, String nom, String prenom, String chambreComplete) {
        this.idPatient = idPatient;
        this.nom = nom;
        this.prenom = prenom;
        this.chambreComplete = chambreComplete;
        this.idHospi = idHospi;
    }

    public Patient(String nom, String prenom, String date_naissance, String groupeSanguin, String motif, String nomService, int etage, int chambre, int poids, int idPatient, int taille, int idHospi) {
        this.idPatient = idPatient;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
        this.GroupeSanguin = groupeSanguin;
        this.motif = motif;
        this.nomService = nomService;
        this.etage = etage;
        this.chambre = chambre;
        this.poids = poids;
        this.idPatient = idPatient;
        this.taille = taille;
        this.idHospi = idHospi;
    }
    public int getIdHospi() {
        return idHospi;
    }

    public void setIdHospi(int idHospi) {
        this.idHospi = idHospi;
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

    public String getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(String date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getGroupeSanguin() {
        return GroupeSanguin;
    }

    public void setGroupeSanguin(String groupeSanguin) {
        GroupeSanguin = groupeSanguin;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getNomService() {
        return nomService;
    }

    public void setNomService(String nomService) {
        this.nomService = nomService;
    }

    public String getChambreComplete() {
        return chambreComplete;
    }

    public void setChambreComplete(String chambreComplete) {
        this.chambreComplete = chambreComplete;
    }

    public int getEtage() {
        return etage;
    }

    public void setEtage(int etage) {
        this.etage = etage;
    }

    public int getChambre() {
        return chambre;
    }

    public void setChambre(int chambre) {
        this.chambre = chambre;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }
}
