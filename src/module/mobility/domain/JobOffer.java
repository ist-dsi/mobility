package module.mobility.domain;

import java.util.ResourceBundle;

import module.mobility.domain.util.JobOfferBean;
import module.organization.domain.Person;
import module.workflow.domain.LabelLog;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import myorg.domain.exceptions.DomainException;

import org.joda.time.DateTime;

import pt.utl.ist.fenix.tools.util.i18n.Language;

public class JobOffer extends JobOffer_Base {

    private static final String MOBILITY_RESOURCES = "resources.MobilityResources";

    public JobOffer(JobOfferBean jobOfferBean) {
	super();
	final User currentUser = UserView.getCurrentUser();
	final Person person = currentUser.getPerson();
	if (person == null) {
	    throw new DomainException("message.mobility.requestor.cannot.be.null", ResourceBundle.getBundle(MOBILITY_RESOURCES,
		    Language.getLocale()));
	}

	setForm(jobOfferBean);
	setMobilitySystem(MobilitySystem.getInstance());
	setMobilityYear(MobilityYear.findMobilityYear(jobOfferBean.getYear()));
	setBeginDate(jobOfferBean.getBeginDate());
	setEndDate(jobOfferBean.getEndDate());
	setCreator(person);
	setCreationDate(new DateTime());
	setCanceled(Boolean.FALSE);
	JobOfferProcess jobOfferProcess = new JobOfferProcess(this);
	new LabelLog(jobOfferProcess, currentUser, "activity.CreateJobOfferActivity", MOBILITY_RESOURCES);
    }

    private void checkDates(DateTime beginDate, DateTime endDate) {
	if (beginDate.isAfter(endDate)) {
	    throw new DomainException("message.mobility.beginDate.isAfter.endDate", ResourceBundle.getBundle(MOBILITY_RESOURCES,
		    Language.getLocale()));
	}
    }

    private void setForm(JobOfferBean jobOfferBean) {
	checkDates(jobOfferBean.getBeginDate(), jobOfferBean.getEndDate());
	setTitle(jobOfferBean.getTitle());
	setJobProfile(jobOfferBean.getJobProfile());
	setKnowledgeRequirements(jobOfferBean.getKnowledgeRequirements());
	setSkillRequirements(jobOfferBean.getSkillRequirements());
	setCareerRequirements(jobOfferBean.getCareerRequirements());
	setCategoryRequirements(jobOfferBean.getCategoryRequirements());
	setSalaryPositionRequirements(jobOfferBean.getSalaryPositionRequirements());
	setQualificationRequirements(jobOfferBean.getQualificationRequirements());
	setFormationRequirements(jobOfferBean.getFormationRequirements());
	setProfessionalExperienceRequirements(jobOfferBean.getProfessionalExperienceRequirements());
    }

    public boolean isUnderConstruction(User user) {
	return !getCanceled() && getCreator().equals(user.getPerson()) && getSubmittedForApprovalDate() == null;
    }

    public boolean isPendingApproval(User user) {
	return (MobilitySystem.getInstance().isManagementMember(user) || getCreator().equals(user.getPerson()))
		&& isPendingApproval();
    }

    public boolean isPendingApproval() {
	return !getCanceled() && getSubmittedForApprovalDate() != null && getApprovalDate() == null;
    }

    public void edit(JobOfferBean jobOfferBean) {
	setForm(jobOfferBean);
    }

    public boolean isApproved(User user) {
	return isApproved() && (MobilitySystem.getInstance().isManagementMember(user));
    }

    public boolean isApproved() {
	return !getCanceled() && getApprovalDate() != null;
    }

    public void approve() {
	setApprovalDate(new DateTime());
	setApprover(MobilitySystem.getInstance().getManagementAccountability(UserView.getCurrentUser()));
    }

    public boolean getHasAllNeededInfoForSubmitCancidacy() {
	Person person = UserView.getCurrentUser().getPerson();
	return person.hasPersonalPortfolio() && person.getPersonalPortfolio().hasAnyPersonalPortfolioInfo();
    }

    public boolean hasCandidacy(User user) {
	return getCandidacy(user.getPerson()) != null;
    }

    public void removeCandidacy(Person person) {
	PersonalPortfolioInfo candidatePortfolioInfo = getCandidacy(person);
	if (candidatePortfolioInfo != null) {
	    getCandidatePortfolioInfoSet().remove(candidatePortfolioInfo);
	}
    }

    public PersonalPortfolioInfo getCandidacy(Person person) {
	for (PersonalPortfolioInfo candidatePortfolioInfo : getCandidatePortfolioInfoSet()) {
	    if (candidatePortfolioInfo.getPersonalPortfolio().getPerson().equals(person)) {
		return candidatePortfolioInfo;
	    }
	}
	return null;
    }
}
