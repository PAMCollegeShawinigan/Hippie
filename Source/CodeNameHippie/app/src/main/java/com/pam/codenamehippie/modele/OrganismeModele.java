package com.pam.codenamehippie.modele;

import com.google.gson.annotations.SerializedName;

/**
 * Créé par Carl St-Louis le 23-11-2015.
 */
public class OrganismeModele extends BaseModele<OrganismeModele> {

    @SerializedName("nom")
    protected String nom;
    @SerializedName("adresse")
    protected AdresseModele adresse;
    @SerializedName("telephone")
    protected String telephone;
    @SerializedName("poste")
    protected Integer poste;
    @SerializedName("contact")
    protected UtilisateurModele contact;
    @SerializedName("no_entreprise")
    protected String noEntreprise;
    @SerializedName("no_osbl")
    protected String noOsbl;
    @SerializedName("statistique")
    protected StatsModele statistique;

    protected transient volatile String formattedTelephone;

    public StatsModele getStatistique() {
        return statistique;
    }

    public OrganismeModele setStatistique(StatsModele statistique) {
        this.statistique = statistique;
        return this;
    }

    public String getNom() {
        return this.nom;
    }

    public OrganismeModele setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public AdresseModele getAdresse() {
        return this.adresse;
    }

    public OrganismeModele setAdresse(AdresseModele adresse) {
        this.adresse = adresse;
        return this;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public OrganismeModele setTelephone(String telephone) {
        if (telephone == null) {
            this.telephone = null;
        } else {
            this.telephone = telephone.replaceAll("[\\s()-\\.]+", "");
        }
        this.formattedTelephone = null;
        return this;
    }

    public String getFormattedTelephone() {
        if (this.telephone == null) {
            return null;
        }
        if (this.formattedTelephone == null) {
            StringBuilder stringBuilder = new StringBuilder(this.telephone);
            stringBuilder.insert(6, "-").insert(0, "(").insert(4, ") ");
            if (this.poste != null) {
                stringBuilder.append(" ").append("poste: ").append(this.poste);
            }
            this.formattedTelephone = stringBuilder.toString();
        }
        return this.formattedTelephone;
    }

    public Integer getPoste() {
        return this.poste;
    }

    public OrganismeModele setPoste(Integer poste) {
        this.poste = poste;
        this.formattedTelephone = null;
        return this;
    }

    public UtilisateurModele getContact() {
        return this.contact;
    }

    public OrganismeModele setContact(UtilisateurModele contact) {
        this.contact = contact;
        return this;
    }

    public String getNoEntreprise() {
        return this.noEntreprise;
    }

    public OrganismeModele setNoEntreprise(String noEntreprise) {
        this.noEntreprise = noEntreprise;
        return this;
    }

    public String getNoOsbl() {
        return this.noOsbl;
    }

    public OrganismeModele setNoOsbl(String noOsbl) {
        this.noOsbl = noOsbl;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganismeModele)) {
            return false;
        }
        OrganismeModele rhs = ((OrganismeModele) o);
        return (((this.id == null) ? (rhs.id == null) : this.id.equals(rhs.id)) &&
                ((this.nom == null) ? (rhs.nom == null) : this.nom.equals(rhs.nom)) &&
                ((this.adresse == null)
                 ? (rhs.adresse == null)
                 : this.adresse.equals(rhs.adresse)) &&
                ((this.poste == null) ? (rhs.poste == null) : this.poste.equals(rhs.poste)) &&
                ((this.contact == null)
                 ? (rhs.contact == null)
                 : this.contact.equals(rhs.contact)) &&
                ((this.noEntreprise == null)
                 ? (rhs.noEntreprise == null)
                 : this.noEntreprise.equals(rhs.noEntreprise)) &&
                ((this.noOsbl == null)
                 ? (rhs.noOsbl == null)
                 : this.noOsbl.equals(rhs.noOsbl)) &&
                ((this.telephone == null)
                 ? (rhs.telephone == null)
                 : this.telephone.equals(rhs.telephone)));
    }

    @Override
    public int hashCode() {
        int hash = 128;
        hash = (this.id != null) ? 64 * hash + this.id.hashCode() : hash;
        hash = (this.nom != null) ? 64 * hash + this.nom.hashCode() : hash;
        hash = (this.adresse != null) ? 64 * hash + this.adresse.hashCode() : hash;
        hash = (this.poste != null) ? 64 * hash + this.poste.hashCode() : hash;
        hash = (this.contact != null) ? 64 * hash + this.contact.hashCode() : hash;
        hash = (this.noEntreprise != null) ? 64 * hash + this.noEntreprise.hashCode() : hash;
        hash = (this.noOsbl != null) ? 64 * hash + this.noOsbl.hashCode() : hash;
        hash = (this.telephone != null) ? 64 * hash + this.telephone.hashCode() : hash;
        return hash;
    }
}
