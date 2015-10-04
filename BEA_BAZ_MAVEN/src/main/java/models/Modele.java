package models;

import org.jongo.marshall.jackson.oid.MongoObjectId;

public class Modele {
	
	@MongoObjectId
    private String _id;
	
	private String n_d_origine,
	               cote_archives_6s,
	               ville,
	               quartier,
	               titre_de_l_oeuvre,
	               date, 
	               dimensions,
	               etat_a___excellent_etat_b___bon_etat_c___etat_moyen_d___mauvais_etat,
	               insolation_du_papier_oui_non,
	               decoloration_des_couleurs_oui_non,
	               gondolement_oui_non,
	               eraflure__a_preciser,
	               dechirure__a_preciser,
	               cassure_ou_pliure_a_preciser,
	               lacune_a_preciser,
	               trous_de_punaise__a_preciser,
	               enfoncement_a_preciser,
	               trace_de_montage_a_preciser,
	               empoussierement_oui_non,
	               moisissure_oui_non,
	               traces_d_humidite_a_preciser,
	               tache_a_preciser,
	               autres_a_preciser, 
	               _observations,
	               field_25,
	               inscriptions_au_verso,
	               format_de_conditionnement;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getN_d_origine() {
		return n_d_origine;
	}

	public void setN_d_origine(String n_d_origine) {
		this.n_d_origine = n_d_origine;
	}

	public String getCote_archives_6s() {
		return cote_archives_6s;
	}

	public void setCote_archives_6s(String cote_archives_6s) {
		this.cote_archives_6s = cote_archives_6s;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getQuartier() {
		return quartier;
	}

	public void setQuartier(String quartier) {
		this.quartier = quartier;
	}

	public String getTitre_de_l_oeuvre() {
		return titre_de_l_oeuvre;
	}

	public void setTitre_de_l_oeuvre(String titre_de_l_oeuvre) {
		this.titre_de_l_oeuvre = titre_de_l_oeuvre;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDimensions() {
		return dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public String getEtat_a___excellent_etat_b___bon_etat_c___etat_moyen_d___mauvais_etat() {
		return etat_a___excellent_etat_b___bon_etat_c___etat_moyen_d___mauvais_etat;
	}

	public void setEtat_a___excellent_etat_b___bon_etat_c___etat_moyen_d___mauvais_etat(
			String etat_a___excellent_etat_b___bon_etat_c___etat_moyen_d___mauvais_etat) {
		this.etat_a___excellent_etat_b___bon_etat_c___etat_moyen_d___mauvais_etat = etat_a___excellent_etat_b___bon_etat_c___etat_moyen_d___mauvais_etat;
	}

	public String getInsolation_du_papier_oui_non() {
		return insolation_du_papier_oui_non;
	}

	public void setInsolation_du_papier_oui_non(String insolation_du_papier_oui_non) {
		this.insolation_du_papier_oui_non = insolation_du_papier_oui_non;
	}

	public String getDecoloration_des_couleurs_oui_non() {
		return decoloration_des_couleurs_oui_non;
	}

	public void setDecoloration_des_couleurs_oui_non(
			String decoloration_des_couleurs_oui_non) {
		this.decoloration_des_couleurs_oui_non = decoloration_des_couleurs_oui_non;
	}

	public String getGondolement_oui_non() {
		return gondolement_oui_non;
	}

	public void setGondolement_oui_non(String gondolement_oui_non) {
		this.gondolement_oui_non = gondolement_oui_non;
	}

	public String getEraflure__a_preciser() {
		return eraflure__a_preciser;
	}

	public void setEraflure__a_preciser(String eraflure__a_preciser) {
		this.eraflure__a_preciser = eraflure__a_preciser;
	}

	public String getDechirure__a_preciser() {
		return dechirure__a_preciser;
	}

	public void setDechirure__a_preciser(String dechirure__a_preciser) {
		this.dechirure__a_preciser = dechirure__a_preciser;
	}

	public String getCassure_ou_pliure_a_preciser() {
		return cassure_ou_pliure_a_preciser;
	}

	public void setCassure_ou_pliure_a_preciser(String cassure_ou_pliure_a_preciser) {
		this.cassure_ou_pliure_a_preciser = cassure_ou_pliure_a_preciser;
	}

	public String getLacune_a_preciser() {
		return lacune_a_preciser;
	}

	public void setLacune_a_preciser(String lacune_a_preciser) {
		this.lacune_a_preciser = lacune_a_preciser;
	}

	public String getTrous_de_punaise__a_preciser() {
		return trous_de_punaise__a_preciser;
	}

	public void setTrous_de_punaise__a_preciser(String trous_de_punaise__a_preciser) {
		this.trous_de_punaise__a_preciser = trous_de_punaise__a_preciser;
	}

	public String getEnfoncement_a_preciser() {
		return enfoncement_a_preciser;
	}

	public void setEnfoncement_a_preciser(String enfoncement_a_preciser) {
		this.enfoncement_a_preciser = enfoncement_a_preciser;
	}

	public String getTrace_de_montage_a_preciser() {
		return trace_de_montage_a_preciser;
	}

	public void setTrace_de_montage_a_preciser(String trace_de_montage_a_preciser) {
		this.trace_de_montage_a_preciser = trace_de_montage_a_preciser;
	}

	public String getEmpoussierement_oui_non() {
		return empoussierement_oui_non;
	}

	public void setEmpoussierement_oui_non(String empoussierement_oui_non) {
		this.empoussierement_oui_non = empoussierement_oui_non;
	}

	public String getMoisissure_oui_non() {
		return moisissure_oui_non;
	}

	public void setMoisissure_oui_non(String moisissure_oui_non) {
		this.moisissure_oui_non = moisissure_oui_non;
	}

	public String getTraces_d_humidite_a_preciser() {
		return traces_d_humidite_a_preciser;
	}

	public void setTraces_d_humidite_a_preciser(String traces_d_humidite_a_preciser) {
		this.traces_d_humidite_a_preciser = traces_d_humidite_a_preciser;
	}

	public String getTache_a_preciser() {
		return tache_a_preciser;
	}

	public void setTache_a_preciser(String tache_a_preciser) {
		this.tache_a_preciser = tache_a_preciser;
	}

	public String getAutres_a_preciser() {
		return autres_a_preciser;
	}

	public void setAutres_a_preciser(String autres_a_preciser) {
		this.autres_a_preciser = autres_a_preciser;
	}

	public String get_observations() {
		return _observations;
	}

	public void set_observations(String _observations) {
		this._observations = _observations;
	}

	public String getField_25() {
		return field_25;
	}

	public void setField_25(String field_25) {
		this.field_25 = field_25;
	}

	public String getInscriptions_au_verso() {
		return inscriptions_au_verso;
	}

	public void setInscriptions_au_verso(String inscriptions_au_verso) {
		this.inscriptions_au_verso = inscriptions_au_verso;
	}

	public String getFormat_de_conditionnement() {
		return format_de_conditionnement;
	}

	public void setFormat_de_conditionnement(String format_de_conditionnement) {
		this.format_de_conditionnement = format_de_conditionnement;
	}
	
	


}
