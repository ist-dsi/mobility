package module.mobility.domain;

import module.mobility.domain.util.JobOfferBean;
import module.organization.domain.Person;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import myorg.domain.exceptions.DomainException;

import org.joda.time.DateTime;

import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class JobOffer extends JobOffer_Base {

    public JobOffer(int year, DateTime beginDate, DateTime endDate, MultiLanguageString title) {
	super();
	setMobilitySystem(MobilitySystem.getInstance());
	setMobilityYear(MobilityYear.findMobilityYear(year));
	setBeginDate(beginDate);
	setEndDate(endDate);
	setTitle(title);
	final Person person = UserView.getCurrentUser().getPerson();
	if (person == null) {
	    throw new DomainException("message.mobility.requestor.cannot.be.null");
	}
	setCreator(person);
	setCreationDate(new DateTime());
	setCanceled(Boolean.FALSE);
	new JobOfferProcess(this);
    }

    public JobOffer(JobOfferBean jobOfferBean) {
	super();
	setMobilitySystem(MobilitySystem.getInstance());
	setMobilityYear(MobilityYear.findMobilityYear(jobOfferBean.getYear()));
	setBeginDate(jobOfferBean.getBeginDate());
	setEndDate(jobOfferBean.getEndDate());
	final Person person = UserView.getCurrentUser().getPerson();
	if (person == null) {
	    throw new DomainException("message.mobility.requestor.cannot.be.null");
	}
	setCreator(person);
	setCreationDate(new DateTime());

	setForm(jobOfferBean);
	new JobOfferProcess(this);
    }

    private void setForm(JobOfferBean jobOfferBean) {
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

    public boolean getIsUnderConstruction(User user) {
	return !getCanceled() && (getCreator().equals(user.getPerson()) || MobilitySystem.getInstance().isManagementMember(user))
		&& getSubmittedForApprovalDate() == null;
    }

    public boolean getIsPendingApproval(User user) {
	return (MobilitySystem.getInstance().isManagementMember(user)) && getIsPendingApproval();
    }

    public boolean getIsPendingApproval() {
	return !getCanceled() && getSubmittedForApprovalDate() != null && getApprovalDate() == null;
    }

    public void edit(JobOfferBean jobOfferBean) {
	setForm(jobOfferBean);
    }

    public boolean getIsApproved(User user) {
	return getIsApproved() && (MobilitySystem.getInstance().isManagementMember(user));
    }

    public boolean getIsApproved() {
	return !getCanceled() && getApprovalDate() != null;
    }

    public void approve() {
	setApprovalDate(new DateTime());
	setApprover(MobilitySystem.getInstance().getManagementAccountability(UserView.getCurrentUser()));
    }

    public boolean getHasAllNeededInfoForSubmitCancidacy() {
	Person person = UserView.getCurrentUser().getPerson();
	return getIsApproved() && person.hasPersonalPortfolio() && person.getPersonalPortfolio().hasAnyPersonalPortfolioInfo();
    }

    public boolean hasCandidacy(User user) {
	return getCandidatePortfolioSet().contains(user.getPerson().getPersonalPortfolio());
    }

}
