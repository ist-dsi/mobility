package module.mobility.domain.util;

import java.io.Serializable;
import java.util.Calendar;

import module.mobility.domain.JobOffer;

import org.joda.time.DateTime;

import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class JobOfferBean implements Serializable {

    private int year = Calendar.getInstance().get(Calendar.YEAR);
    private DateTime beginDate;
    private DateTime endDate;
    private MultiLanguageString title;
    private MultiLanguageString jobProfile;
    private MultiLanguageString knowledgeRequirements;
    private MultiLanguageString skillRequirements;
    private MultiLanguageString careerRequirements;
    private MultiLanguageString categoryRequirements;
    private MultiLanguageString salaryPositionRequirements;
    private MultiLanguageString qualificationRequirements;
    private MultiLanguageString formationRequirements;
    private MultiLanguageString professionalExperienceRequirements;

    public JobOfferBean() {
    }

    public JobOfferBean(JobOffer jobOffer) {
	setYear(jobOffer.getMobilityYear().getYear());
	setBeginDate(jobOffer.getBeginDate());
	setEndDate(jobOffer.getEndDate());
	setTitle(jobOffer.getTitle());
	setJobProfile(jobOffer.getJobProfile());
	setKnowledgeRequirements(jobOffer.getKnowledgeRequirements());
	setSkillRequirements(jobOffer.getSkillRequirements());
	setCareerRequirements(jobOffer.getCareerRequirements());
	setCategoryRequirements(jobOffer.getCategoryRequirements());
	setSalaryPositionRequirements(jobOffer.getSalaryPositionRequirements());
	setQualificationRequirements(jobOffer.getQualificationRequirements());
	setFormationRequirements(jobOffer.getFormationRequirements());
	setProfessionalExperienceRequirements(jobOffer.getProfessionalExperienceRequirements());
    }

    @Service
    public JobOffer create() {
	return new JobOffer(this);
    }

    public DateTime getBeginDate() {
	return beginDate;
    }

    public void setBeginDate(DateTime beginDate) {
	this.beginDate = beginDate;
    }

    public DateTime getEndDate() {
	return endDate;
    }

    public void setEndDate(DateTime endDate) {
	this.endDate = endDate;
    }

    public MultiLanguageString getTitle() {
	return title;
    }

    public void setTitle(MultiLanguageString title) {
	this.title = title;
    }

    public int getYear() {
	return year;
    }

    public void setYear(int year) {
	this.year = year;
    }

    public MultiLanguageString getJobProfile() {
	return jobProfile;
    }

    public void setJobProfile(MultiLanguageString jobProfile) {
	this.jobProfile = jobProfile;
    }

    public MultiLanguageString getKnowledgeRequirements() {
	return knowledgeRequirements;
    }

    public void setKnowledgeRequirements(MultiLanguageString knowledgeRequirements) {
	this.knowledgeRequirements = knowledgeRequirements;
    }

    public MultiLanguageString getSkillRequirements() {
	return skillRequirements;
    }

    public void setSkillRequirements(MultiLanguageString skillRequirements) {
	this.skillRequirements = skillRequirements;
    }

    public MultiLanguageString getCareerRequirements() {
	return careerRequirements;
    }

    public void setCareerRequirements(MultiLanguageString careerRequirements) {
	this.careerRequirements = careerRequirements;
    }

    public MultiLanguageString getCategoryRequirements() {
	return categoryRequirements;
    }

    public void setCategoryRequirements(MultiLanguageString categoryRequirements) {
	this.categoryRequirements = categoryRequirements;
    }

    public MultiLanguageString getSalaryPositionRequirements() {
	return salaryPositionRequirements;
    }

    public void setSalaryPositionRequirements(MultiLanguageString salaryPositionRequirements) {
	this.salaryPositionRequirements = salaryPositionRequirements;
    }

    public MultiLanguageString getQualificationRequirements() {
	return qualificationRequirements;
    }

    public void setQualificationRequirements(MultiLanguageString qualificationRequirements) {
	this.qualificationRequirements = qualificationRequirements;
    }

    public MultiLanguageString getFormationRequirements() {
	return formationRequirements;
    }

    public void setFormationRequirements(MultiLanguageString formationRequirements) {
	this.formationRequirements = formationRequirements;
    }

    public MultiLanguageString getProfessionalExperienceRequirements() {
	return professionalExperienceRequirements;
    }

    public void setProfessionalExperienceRequirements(MultiLanguageString professionalExperienceRequirements) {
	this.professionalExperienceRequirements = professionalExperienceRequirements;
    }

}
