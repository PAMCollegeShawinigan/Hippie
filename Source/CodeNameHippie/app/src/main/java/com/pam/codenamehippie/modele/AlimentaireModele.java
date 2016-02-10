package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class AlimentaireModele extends MarchandiseModele<AlimentaireModele> {

    @SerializedName("type_alimentaire")
    protected String typeAlimentaire;
    @SerializedName("date_peremption")
    protected Date datePeremption;
    @SerializedName("date_reservation")
    protected Date dateReservation;

    public Date getDateReservation() {return this.dateReservation;}

    public AlimentaireModele setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
        return this;
    }

    public String getTypeAlimentaire() {
        return this.typeAlimentaire;
    }

    public AlimentaireModele setTypeAlimentaire(String typeAlimentaire) {
        this.typeAlimentaire = typeAlimentaire;
        return this;
    }

    public Date getDatePeremption() {
        return this.datePeremption;
    }

    public AlimentaireModele setDatePeremption(Date datePeremption) {
        this.datePeremption = datePeremption;
        return this;
    }
    
    public Calendar getCalendarDatePeremption() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.datePeremption);
        return calendar;
    }

    public Calendar getCalendarDateReservation() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.dateReservation);
        return calendar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlimentaireModele)) {
            return false;
        }
        AlimentaireModele rhs = ((AlimentaireModele) o);
        return (((this.id == null) ? (rhs.id == null) : this.id.equals(rhs.id)) &&
                ((this.nom == null) ? (rhs.nom == null) : this.nom.equals(rhs.nom)) &&
                ((this.description == null)
                 ? (rhs.description == null)
                 : this.description.equals(rhs.description)) &&
                ((this.quantite == null)
                 ? (rhs.quantite == null)
                 : this.quantite.equals(rhs.quantite)) &&
                ((this.uniteDeQuantite == null)
                 ? (rhs.uniteDeQuantite == null)
                 : this.uniteDeQuantite.equals(rhs.uniteDeQuantite)) &&
                ((this.etat == null) ? (rhs.etat == null) : this.etat.equals(rhs.etat)) &&
                ((this.valeur == null) ? (rhs.valeur == null) : this.valeur.equals(rhs.valeur)) &&
                ((this.statut == null) ? (rhs.statut == null) : this.statut.equals(rhs.statut)) &&
                ((this.organisme == null)
                 ? (rhs.organisme == null)
                 : this.organisme.equals(rhs.organisme)) &&
                ((this.typeAlimentaire == null)
                 ? (rhs.typeAlimentaire == null)
                 : this.typeAlimentaire.equals(rhs.typeAlimentaire)) &&
                ((this.datePeremption == null)
                 ? (rhs.datePeremption == null)
                 : this.datePeremption.equals(rhs.datePeremption)) &&
                ((this.dateReservation == null)
                 ? (rhs.dateReservation == null)
                 : this.dateReservation.equals(rhs.dateReservation)));
    }

    @Override
    public int hashCode() {
        int hash = 64;
        hash = (this.id != null) ? 32 * hash + this.id.hashCode() : hash;
        hash = (this.nom != null) ? 32 * hash + this.nom.hashCode() : hash;
        hash = (this.description != null) ? 32 * hash + this.description.hashCode() : hash;
        hash = (this.uniteDeQuantite != null) ? 32 * hash + this.uniteDeQuantite.hashCode() : hash;
        hash = (this.etat != null) ? 32 * hash + this.etat.hashCode() : hash;
        hash = (this.valeur != null) ? 32 * hash + this.valeur.hashCode() : hash;
        hash = (this.statut != null) ? 32 * hash + this.statut.hashCode() : hash;
        hash = (this.organisme != null) ? 32 * hash + this.organisme.hashCode() : hash;
        hash = (this.typeAlimentaire != null) ? 32 * hash + this.typeAlimentaire.hashCode() : hash;
        hash = (this.datePeremption != null) ? 32 * hash + this.datePeremption.hashCode() : hash;
        hash = (this.dateReservation != null) ? 32 * hash + this.dateReservation.hashCode() : hash;
        return hash;
    }
}
