package com.xavey.diego.api.model;

/**
 * Created by astk on 4/24/16.
 */
public class Meller extends User {
    private String marital;
    private String employment;
    private String career;
    private String job_function;
    private String industry;
    private String telco;
    private String religion;
    private String race;
    private String bank_account;
    private String smoker;
    private String drinker;
    private String referrer;
    private String status;

    public Meller() {
    }

    public String getMarital() { return this.marital; }
    public String getEmployment() { return this.employment; }
    public String getCareer() { return this.career; }
    public String getJobFunction() { return this.job_function; }
    public String getIndustry() { return this.industry; }
    public String getTelco() { return this.telco; }
    public String getReligion() { return this.religion; }
    public String getRace() { return this.race; }
    public String getBankAccount() { return this.bank_account; }
    public String getSmoker() { return this.smoker; }
    public String getDrinker() { return this.drinker; }
    public String getReferrer() { return this.referrer; }
    public String getStatus() { return this.status; }

    public void setMarital(String marital) { this.marital = marital; }
    public void setEmployment(String employment) { this.employment = employment; }
    public void setCareer(String career) { this.career = career; }
    public void setJobFunction(String job_function) { this.job_function = job_function; }
    public void setIndustry(String industry) { this.industry = industry; }
    public void setTelco(String telco) { this.telco = telco; }
    public void setReligion(String religion) { this.religion = religion; }
    public void setRace(String race) { this.race = race; }
    public void setBankAccount(String bank_account) { this.bank_account = bank_account; }
    public void setSmoker(String smoker) { this.smoker = smoker; }
    public void setDrinker(String drinker) { this.drinker = drinker; }
    public void setReferrer(String referrer) { this.referrer = referrer; }
    public void setStatus(String status) { this.status = status; }

}
