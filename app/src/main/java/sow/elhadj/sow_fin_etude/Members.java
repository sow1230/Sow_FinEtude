package sow.elhadj.sow_fin_etude;

public class Members {
    private String NomEtPrenom;
    private String Email;
    private String Adresse;
    private String Tel;
    private String MotdePasse;


    Members() {

    }

    public String getNomEtPrenom() {
        return NomEtPrenom;
    }

    void setNomEtPrenom(String nomEtPrenom) {
        NomEtPrenom = nomEtPrenom;
    }

    public String getEmail() {
        return Email;
    }

    void setEmail(String email) {
        Email = email;
    }

    public String getAdresse() {
        return Adresse;
    }

    void setAdresse(String adresse) {
        Adresse = adresse;
    }

    public String getTel() {
        return Tel;
    }

    void setTel(String tel) {
        Tel = tel;
    }

    public String getMotdePasse() {
        return MotdePasse;
    }

    void setMotdePasse(String motdePasse) {
        MotdePasse = motdePasse;
    }
}
