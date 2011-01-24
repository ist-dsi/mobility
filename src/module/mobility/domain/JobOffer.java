package module.mobility.domain;

import java.util.Collections;
import java.util.EnumSet;
import java.util.ResourceBundle;

import module.mobility.domain.util.JobOfferBean;
import module.organization.domain.Person;
import module.organization.domain.Unit;
import module.organizationIst.domain.IstAccountabilityType;
import module.workflow.domain.LabelLog;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;
import myorg.domain.exceptions.DomainException;
import myorg.util.BundleUtil;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import pt.ist.emailNotifier.domain.Email;
import pt.utl.ist.fenix.tools.util.i18n.Language;

public class JobOffer extends JobOffer_Base implements Comparable<JobOffer> {

    private static final int MINIMUM_JURY_ELEMENTS = 3;
    private static final String MOBILITY_RESOURCES = "resources.MobilityResources";

    @Override
    public int compareTo(final JobOffer offer) {
	final DateTime creationDate = offer.getCreationDate();
	final int c = getCreationDate().compareTo(creationDate);
	return c == 0 ? hashCode() - offer.hashCode() : c;
    }

    public JobOffer(JobOfferBean jobOfferBean) {
	super();
	setCanceled(Boolean.FALSE);
	final User currentUser = UserView.getCurrentUser();
	final Person person = currentUser.getPerson();
	if (person == null) {
	    throw new DomainException("message.mobility.requestor.cannot.be.null", ResourceBundle.getBundle(MOBILITY_RESOURCES,
		    Language.getLocale()));
	}

	setForm(jobOfferBean);
	setMobilitySystem(MobilitySystem.getInstance());
	setMobilityYear(MobilityYear.findMobilityYear(jobOfferBean.getYear()));
	setVacanciesNumber(jobOfferBean.getVacanciesNumber());
	setCreator(person);
	setCreationDate(new DateTime());
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
	// checkDates(jobOfferBean.getBeginDate(), jobOfferBean.getEndDate());
	setWorkplace(jobOfferBean.getWorkplace());
	setJobProfile(jobOfferBean.getJobProfile());
	setKnowledgeRequirements(jobOfferBean.getKnowledgeRequirements());
	setSkillRequirements(jobOfferBean.getSkillRequirements());
	setCareerRequirements(EnumSet.copyOf(jobOfferBean.getCareerRequirements()));
	setCategoryRequirements(jobOfferBean.getCategoryRequirements());
	setQualificationRequirements(jobOfferBean.getQualificationRequirements());
	setFormationRequirements(jobOfferBean.getFormationRequirements());
	setProfessionalExperienceRequirements(jobOfferBean.getProfessionalExperienceRequirements());
	setRequiredDocumentsForCandidacy(jobOfferBean.getRequiredDocumentsForCandidacy());
    }

    public Person getOwner() {
	return super.getCreator();
    }

    public void edit(JobOfferBean jobOfferBean) {
	setForm(jobOfferBean);
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

    public DateTime getPublicationDate() {
	return isApproved() ? getApprovalDate() : null;
    }

    public void approve(DateTime publicationBeginDate, DateTime publicationEndDate) {
	setApprovalDate(new DateTime());
	setPublicationBeginDate(publicationBeginDate);
	setPublicationEndDate(publicationEndDate);
	setJobOfferApprover(MobilitySystem.getInstance().getManagementAccountability(UserView.getCurrentUser()));
	String fromName = BundleUtil.getStringFromResourceBundle(MOBILITY_RESOURCES, "message.mobility.jobOffer.emailFromName");
	String emailSubject = BundleUtil
		.getStringFromResourceBundle(MOBILITY_RESOURCES, "message.mobility.jobOffer.emailSubject");
	String messageBody = BundleUtil.getFormattedStringFromResourceBundle(MOBILITY_RESOURCES,
		"message.mobility.jobOffer.emailBody", getWorkplacePath(), getJobOfferProcess().getProcessIdentification());
	new Email(fromName, "noreply@ist.utl.pt", new String[] {}, Collections.EMPTY_LIST, Collections.EMPTY_LIST, MobilitySystem
		.getInstance().getServiceNotificationEmails(), emailSubject, messageBody);
    }

    public String getWorkplacePath() {
	StringBuilder workplacePath = new StringBuilder();
	if (getWorkplace() != null) {
	    workplacePath.append(getWorkplace().getPartyName().getContent());
	    for (Unit unit : getWorkplace().getParentUnits(IstAccountabilityType.ORGANIZATIONAL.readAccountabilityType())) {
		workplacePath.append(" (").append(unit.getPartyName().getContent()).append(")");
	    }
	}
	return workplacePath.toString();
    }

    public boolean isUnderConstruction() {
	return !getCanceled() && getSubmittedForSelectionDate() == null;
    }

    public boolean isUnderConstruction(User user) {
	return getOwner().equals(user.getPerson()) && isUnderConstruction();
    }

    public boolean isPendingSelection() {
	return !getCanceled() && getSubmittedForSelectionDate() != null && getSubmittedForEvaluationDate() == null
		&& !isInInternalRecruitment();
    }

    public boolean isUnderSelectionEvaluation() {
	return !getCanceled() && getSubmittedForEvaluationDate() != null && !isConcluded() && !isInInternalRecruitment();
    }

    public boolean isPendingConclusion() {
	return !getCanceled()
		&& getConclusionDate() == null
		&& (isInInternalRecruitment() ? (isApproved() && isUnderCandidacyEvaluation())
			: !getSelectedWorkerPortfolioInfo().isEmpty());
    }

    public boolean isConcluded() {
	return !getCanceled()
		&& (getConclusionDate() != null || (isCandidacyPeriodFinish() && getCandidatePortfolioInfoCount() == 0));
    }

    public boolean isInInternalRecruitment() {
	return !getCanceled() && getSubmittedForJuryDefinitionDate() != null;
    }

    public boolean isPendingJuryDefinition() {
	return !getCanceled() && isInInternalRecruitment() && getSubmittedForApprovalDate() == null;
    }

    public boolean hasJuryDefined() {
	return !getCanceled() && getJuryMemberCount() >= MINIMUM_JURY_ELEMENTS && getJuryMemberCount() % 2 == 1
		&& hasJuryPresident();
    }

    private boolean hasJuryPresident() {
	for (JuryMember juryMember : getJuryMember()) {
	    if (juryMember.getJuryPresident()) {
		return true;
	    }
	}
	return false;
    }

    public boolean isPendingApproval() {
	return !getCanceled() && getSubmittedForApprovalDate() != null && getApprovalDate() == null;
    }

    public boolean isPendingApproval(User user) {
	return (MobilitySystem.getInstance().isManagementMember(user) || getOwner().equals(user.getPerson()))
		&& isPendingApproval();
    }

    public boolean isApproved(User user) {
	return isApproved() && (MobilitySystem.getInstance().isManagementMember(user));
    }

    public boolean isApproved() {
	return !getCanceled() && getApprovalDate() != null;
    }

    public boolean isUnderCandidacyEvaluation() {
	return !getCanceled() && getConclusionDate() == null && isCandidacyPeriodFinish()
		&& getCandidatePortfolioInfoCount() != 0;
    }

    public boolean isInCandidacyEvaluationPeriod() {
	Interval candidacyPeriod = getCandidacyPeriod();
	return !getCanceled() && candidacyPeriod != null && candidacyPeriod.containsNow();
    }

    private Interval getCandidacyPeriod() {
	return getPublicationBeginDate() != null && getPublicationEndDate() != null ? new Interval(getPublicationBeginDate(),
		getPublicationEndDate()) : null;
    }

    public boolean isCandidacyPeriodFinish() {
	return !getCanceled() && isApproved() && getPublicationEndDate() != null && getPublicationEndDate().isBeforeNow();
    }

    public boolean isArchived() {
	return !getCanceled() && getArquivedDate() != null;
    }

    public boolean isActive() {
	DateTime now = new DateTime();
	return !getCanceled() && (getPublicationEndDate() == null || getPublicationEndDate().isAfter(new DateTime()))
		&& (getPublicationBeginDate() == null || getPublicationBeginDate().isBefore(now));
    }

}
