/*
 * @(#)JobOffer.java
 *
 * Copyright 2010 Instituto Superior Tecnico
 * Founding Authors: Susana Fernandes
 *
 *      https://fenix-ashes.ist.utl.pt/
 *
 *   This file is part of the Internal Mobility Module.
 *
 *   The Internal Mobility Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version
 *   3 of the License, or (at your option) any later version.
 *
 *   The Internal Mobility  Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Internal Mobility  Module. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package module.mobility.domain;

import java.util.EnumSet;
import java.util.stream.Stream;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.core.domain.exceptions.DomainException;
import org.fenixedu.bennu.core.groups.Group;
import org.fenixedu.bennu.core.security.Authenticate;
import org.fenixedu.bennu.core.util.CoreConfiguration;
import org.fenixedu.commons.i18n.LocalizedString;
import org.fenixedu.messaging.core.domain.Message;
import org.fenixedu.messaging.core.template.DeclareMessageTemplate;
import org.fenixedu.messaging.core.template.TemplateParameter;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import module.mobility.domain.util.JobOfferBean;
import module.organization.domain.AccountabilityType;
import module.organization.domain.Person;
import module.workflow.domain.LabelLog;

/**
 *
 * @author Luis Cruz
 * @author Susana Fernandes
 *
 */
@DeclareMessageTemplate(id = "mobility.opening", bundle = "resources.MobilityResources",
        description = "template.mobility.opening", subject = "template.mobility.opening.subject",
        text = "template.mobility.opening.text",
        parameters = { @TemplateParameter(id = "applicationUrl", description = "template.parameter.application.url"),
                @TemplateParameter(id = "career", description = "template.parameter.career"),
                @TemplateParameter(id = "process", description = "template.parameter.process"),
                @TemplateParameter(id = "vacancies", description = "template.parameter.vacancies"),
                @TemplateParameter(id = "workplace", description = "template.parameter.workplace"),
                @TemplateParameter(id = "workplacePath", description = "template.parameter.workplacePath") })
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
        final User currentUser = Authenticate.getUser();
        final Person person = currentUser.getPerson();
        if (person == null) {
            throw new DomainException(MOBILITY_RESOURCES, "message.mobility.requestor.cannot.be.null") {
                private static final long serialVersionUID = 1L;
            };
        }

        setForm(jobOfferBean);
        setMobilitySystem(MobilitySystem.getInstance());
        setMobilityYear(MobilityYear.findMobilityYear(jobOfferBean.getYear()));
        setVacanciesNumber(jobOfferBean.getVacanciesNumber());
        setCreator(person);
        setCreationDate(new DateTime());
        JobOfferProcess jobOfferProcess = new JobOfferProcess(this);
        new LabelLog(jobOfferProcess, "activity.CreateJobOfferActivity", MOBILITY_RESOURCES);
    }

    private void setForm(JobOfferBean jobOfferBean) {
        // checkDates(jobOfferBean.getBeginDate(), jobOfferBean.getEndDate());
        setVacanciesNumber(jobOfferBean.getVacanciesNumber());
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
        setAdditionalRemarks(jobOfferBean.getAdditionalRemarks());
        setOptionalDocuments(jobOfferBean.getOptionalDocuments());
    }

    public Person getOwner() {
        return super.getCreator();
    }

    public void edit(JobOfferBean jobOfferBean) {
        setForm(jobOfferBean);
    }

    public boolean getHasAllNeededInfoForSubmitCancidacy() {
        Person person = Authenticate.getUser().getPerson();
        return person.getPersonalPortfolio() != null && person.getPersonalPortfolio().hasAnyPersonalPortfolioInfo();
    }

    public boolean hasCandidacy(User user) {
        return getCandidacy(user.getPerson()) != null;
    }

    public void removeCandidacy(Person person) {
        JobOfferCandidacy jobOfferCandidacy = getCandidacy(person);
        if (jobOfferCandidacy != null) {
            jobOfferCandidacy.delete();
        }
    }

    public JobOfferCandidacy getCandidacy(Person person) {
        for (JobOfferCandidacy jobOfferCandidacy : getJobOfferCandidacySet()) {
            if (jobOfferCandidacy.getPersonalPortfolioInfo().getPersonalPortfolio().getPerson().equals(person)) {
                return jobOfferCandidacy;
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
        setJobOfferApproverPerson(Authenticate.getUser().getPerson());

        final Stream<User> usersToNotify = MobilitySystem.getInstance().getPersonalPortfolioSet().stream()
                .filter(pp -> pp.getNotificationService() != null && pp.getNotificationService().booleanValue())
                .map(pp -> pp.getPerson().getUser());
        LocalizedString career =
                getCareerRequirements().stream().map(CareerType::getName).reduce((a, ls) -> a.append(ls, ", ")).orElse(null);
        Message.fromSystem().bcc(Group.users(usersToNotify)).template("mobility.opening")
                .parameter("workplace", getWorkplace().getPartyName().getContent()).parameter("workplacePath", getWorkplacePath())
                .parameter("process", getJobOfferProcess().getProcessIdentification())
                .parameter("applicationUrl", CoreConfiguration.getConfiguration().applicationUrl())
                .parameter("vacancies", getVacanciesNumber()).parameter("career", career.getContent()).and().send();
    }

    public String getWorkplacePath() {
        StringBuilder workplacePath = new StringBuilder();
        if (getWorkplace() != null) {
            workplacePath.append(getWorkplace().getPartyName().getContent());
            final AccountabilityType type = MobilitySystem.getInstance().getOrganizationalAccountabilityType();
            getWorkplace().getParentAccountabilityStream().filter(a -> a.getAccountabilityType() == type).map(a -> a.getParent())
                    .filter(p -> p.isUnit()).forEach(p -> workplacePath.append(" / ").append(p.getPartyName().getContent()));
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
        return !getCanceled() && getConclusionDate() == null
                && (isInInternalRecruitment() ? (isApproved() && isUnderCandidacyEvaluation()) : hasAnyChosenCandidate());
    }

    public boolean isConcluded() {
        return !getCanceled()
                && (getConclusionDate() != null || (isCandidacyPeriodFinish() && getJobOfferCandidacySet().size() == 0));
    }

    public boolean isInInternalRecruitment() {
        return !getCanceled() && getSubmittedForJuryDefinitionDate() != null;
    }

    public boolean isPendingJuryDefinition() {
        return !getCanceled() && isInInternalRecruitment() && getSubmittedForApprovalDate() == null;
    }

    public boolean hasJuryDefined() {
        return !getCanceled() && getJuryMemberSet().size() >= MINIMUM_JURY_ELEMENTS && getJuryMemberSet().size() % 2 == 1
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
        return !getCanceled() && getConclusionDate() == null && isCandidacyPeriodFinish() && hasAnyJobOfferCandidacy();
    }

    public boolean isInCandidacyPeriod() {
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

    @Deprecated
    public boolean hasCreationDate() {
        return getCreationDate() != null;
    }

    @Deprecated
    public boolean hasCanceled() {
        return getCanceled() != null;
    }

    @Deprecated
    public boolean hasSubmittedForSelectionDate() {
        return getSubmittedForSelectionDate() != null;
    }

    @Deprecated
    public boolean hasSubmittedForEvaluationDate() {
        return getSubmittedForEvaluationDate() != null;
    }

    @Deprecated
    public boolean hasConclusionDate() {
        return getConclusionDate() != null;
    }

    @Deprecated
    public boolean hasArquivedDate() {
        return getArquivedDate() != null;
    }

    @Deprecated
    public boolean hasSubmittedForJuryDefinitionDate() {
        return getSubmittedForJuryDefinitionDate() != null;
    }

    @Deprecated
    public boolean hasSubmittedForApprovalDate() {
        return getSubmittedForApprovalDate() != null;
    }

    @Deprecated
    public boolean hasApprovalDate() {
        return getApprovalDate() != null;
    }

    @Deprecated
    public boolean hasPublicationBeginDate() {
        return getPublicationBeginDate() != null;
    }

    @Deprecated
    public boolean hasPublicationEndDate() {
        return getPublicationEndDate() != null;
    }

    @Deprecated
    public boolean hasJobProfile() {
        return getJobProfile() != null;
    }

    @Deprecated
    public boolean hasKnowledgeRequirements() {
        return getKnowledgeRequirements() != null;
    }

    @Deprecated
    public boolean hasSkillRequirements() {
        return getSkillRequirements() != null;
    }

    @Deprecated
    public boolean hasCareerRequirements() {
        return getCareerRequirements() != null;
    }

    @Deprecated
    public boolean hasCategoryRequirements() {
        return getCategoryRequirements() != null;
    }

    @Deprecated
    public boolean hasQualificationRequirements() {
        return getQualificationRequirements() != null;
    }

    @Deprecated
    public boolean hasFormationRequirements() {
        return getFormationRequirements() != null;
    }

    @Deprecated
    public boolean hasProfessionalExperienceRequirements() {
        return getProfessionalExperienceRequirements() != null;
    }

    @Deprecated
    public boolean hasVacanciesNumber() {
        return getVacanciesNumber() != null;
    }

    @Deprecated
    public boolean hasRequiredDocumentsForCandidacy() {
        return getRequiredDocumentsForCandidacy() != null;
    }

    @Deprecated
    public boolean hasAdditionalRemarks() {
        return getAdditionalRemarks() != null;
    }

    @Deprecated
    public boolean hasOptionalDocuments() {
        return getOptionalDocuments() != null;
    }

    @Deprecated
    public boolean hasCreator() {
        return getCreator() != null;
    }

    @Deprecated
    public boolean hasJobOfferApproverPerson() {
        return getJobOfferApproverPerson() != null;
    }

    @Deprecated
    public boolean hasMobilitySystem() {
        return getMobilitySystem() != null;
    }

    @Deprecated
    public java.util.Set<module.mobility.domain.JuryMember> getJuryMember() {
        return getJuryMemberSet();
    }

    @Deprecated
    public boolean hasAnyJuryMember() {
        return !getJuryMemberSet().isEmpty();
    }

    @Deprecated
    public java.util.Set<module.mobility.domain.JobOfferCandidacy> getJobOfferCandidacy() {
        return getJobOfferCandidacySet();
    }

    @Deprecated
    public boolean hasAnyJobOfferCandidacy() {
        return !getJobOfferCandidacySet().isEmpty();
    }

    @Deprecated
    public boolean hasMobilityYear() {
        return getMobilityYear() != null;
    }

    @Deprecated
    public boolean hasJobOfferProcess() {
        return getJobOfferProcess() != null;
    }

    @Deprecated
    public java.util.Set<module.mobility.domain.PersonalPortfolioInfo> getChosenCandidate() {
        return getChosenCandidateSet();
    }

    @Deprecated
    public boolean hasAnyChosenCandidate() {
        return !getChosenCandidateSet().isEmpty();
    }

    @Deprecated
    public java.util.Set<module.mobility.domain.WorkerOffer> getSelectedWorkerOfferCandidate() {
        return getSelectedWorkerOfferCandidateSet();
    }

    @Deprecated
    public boolean hasAnySelectedWorkerOfferCandidate() {
        return !getSelectedWorkerOfferCandidateSet().isEmpty();
    }

    @Deprecated
    public boolean hasWorkplace() {
        return getWorkplace() != null;
    }

}
