package module.mobility.domain.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import module.mobility.domain.CareerType;
import module.mobility.domain.JobOffer;
import pt.ist.fenixWebFramework.services.Service;

public class JobOfferBean implements Serializable {

    private int year = Calendar.getInstance().get(Calendar.YEAR);
    private String title;
    private String jobProfile;
    private String knowledgeRequirements;
    private String skillRequirements;
    private List<CareerType> careerRequirements;
    private String categoryRequirements;
    private String qualificationRequirements;
    private String formationRequirements;
    private String professionalExperienceRequirements;
    private Integer vacanciesNumber;

    public JobOfferBean() {
    }

    public JobOfferBean(JobOffer jobOffer) {
	setYear(jobOffer.getMobilityYear().getYear());
	setTitle(jobOffer.getTitle());
	setJobProfile(jobOffer.getJobProfile());
	setKnowledgeRequirements(jobOffer.getKnowledgeRequirements());
	setSkillRequirements(jobOffer.getSkillRequirements());
	List<CareerType> careerList = new ArrayList<CareerType>();
	if (jobOffer.getCareerRequirements() != null) {
	    careerList = Arrays.asList(jobOffer.getCareerRequirements().toArray(new CareerType[] {}));
	}
	setCareerRequirements(careerList);
	setCategoryRequirements(jobOffer.getCategoryRequirements());
	setQualificationRequirements(jobOffer.getQualificationRequirements());
	setFormationRequirements(jobOffer.getFormationRequirements());
	setProfessionalExperienceRequirements(jobOffer.getProfessionalExperienceRequirements());
	setVacanciesNumber(jobOffer.getVacanciesNumber());
    }

    @Service
    public JobOffer create() {
	return new JobOffer(this);
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public int getYear() {
	return year;
    }

    public void setYear(int year) {
	this.year = year;
    }

    public String getJobProfile() {
	return jobProfile;
    }

    public void setJobProfile(String jobProfile) {
	this.jobProfile = jobProfile;
    }

    public String getKnowledgeRequirements() {
	return knowledgeRequirements;
    }

    public void setKnowledgeRequirements(String knowledgeRequirements) {
	this.knowledgeRequirements = knowledgeRequirements;
    }

    public String getSkillRequirements() {
	return skillRequirements;
    }

    public void setSkillRequirements(String skillRequirements) {
	this.skillRequirements = skillRequirements;
    }

    public List<CareerType> getCareerRequirements() {
	return careerRequirements;
    }

    public void setCareerRequirements(List<CareerType> careerRequirements) {
	this.careerRequirements = careerRequirements;
    }

    public String getCategoryRequirements() {
	return categoryRequirements;
    }

    public void setCategoryRequirements(String categoryRequirements) {
	this.categoryRequirements = categoryRequirements;
    }

    public String getQualificationRequirements() {
	return qualificationRequirements;
    }

    public void setQualificationRequirements(String qualificationRequirements) {
	this.qualificationRequirements = qualificationRequirements;
    }

    public String getFormationRequirements() {
	return formationRequirements;
    }

    public void setFormationRequirements(String formationRequirements) {
	this.formationRequirements = formationRequirements;
    }

    public String getProfessionalExperienceRequirements() {
	return professionalExperienceRequirements;
    }

    public void setProfessionalExperienceRequirements(String professionalExperienceRequirements) {
	this.professionalExperienceRequirements = professionalExperienceRequirements;
    }

    public Integer getVacanciesNumber() {
	return vacanciesNumber;
    }

    public void setVacanciesNumber(Integer vacanciesNumber) {
	this.vacanciesNumber = vacanciesNumber;
    }

}
