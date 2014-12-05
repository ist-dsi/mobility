/*
 * @(#)MobilityAction.java
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
package module.mobility.presentationTier.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.mobility.domain.JobOffer;
import module.mobility.domain.JobOfferProcess;
import module.mobility.domain.JuryMember;
import module.mobility.domain.MobilitySystem;
import module.mobility.domain.PersonalPortfolio;
import module.mobility.domain.PersonalPortfolioProcess;
import module.mobility.domain.WorkerOffer;
import module.mobility.domain.WorkerOfferProcess;
import module.mobility.domain.activity.JobOfferJuryDefinitionActivity;
import module.mobility.domain.activity.JobOfferJuryInformation;
import module.mobility.domain.activity.PersonalPortfolioInfoInformation;
import module.mobility.domain.activity.PersonalPortfolioInfoInformation.QualificationHolder;
import module.mobility.domain.activity.SubmitCandidacyActivity;
import module.mobility.domain.activity.UnSubmitCandidacyActivity;
import module.mobility.domain.activity.WorkerJobOfferInformation;
import module.mobility.domain.util.JobOfferBean;
import module.mobility.domain.util.OfferSearch;
import module.mobility.domain.util.OfferSearch.OfferSearchOwner;
import module.mobility.domain.util.OfferSearch.OfferSearchState;
import module.organization.domain.OrganizationalModel;
import module.organization.domain.Person;
import module.organization.presentationTier.actions.OrganizationModelAction;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.presentationTier.WorkflowLayoutContext;
import module.workflow.presentationTier.actions.ProcessManagement;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.core.security.Authenticate;
import org.fenixedu.bennu.struts.annotations.Mapping;
import org.fenixedu.bennu.struts.base.BaseAction;

import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;

@Mapping(path = "/mobility")
/**
 * 
 * @author Luis Cruz
 * @author Susana Fernandes
 * 
 */
public class MobilityAction extends BaseAction {

    public ActionForward frontPage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) {
        OfferSearch offerSearch = getRenderedObject("offerSearch");
        if (offerSearch == null) {
            offerSearch = new OfferSearch();
        }
        JobOfferProcess jobOfferProcess = offerSearch.getJobOfferProcess(Authenticate.getUser());
        if (jobOfferProcess != null) {
            return ProcessManagement.forwardToProcess(jobOfferProcess);
        }
        WorkerOfferProcess workerOfferProcess = offerSearch.getWorkerOfferProcess(Authenticate.getUser());
        if (workerOfferProcess != null) {
            return ProcessManagement.forwardToProcess(workerOfferProcess);
        }
        request.setAttribute("offerSearch", offerSearch);
        return forward("/mobility/frontPage.jsp");
    }

    public ActionForward jobOffers(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) {
        OfferSearch offerSearch = getRenderedObject("offerSearch");
        if (offerSearch == null) {
            offerSearch = new OfferSearch();
        }
        request.setAttribute("offerSearch", offerSearch);
        request.setAttribute("processes", offerSearch.doJobOfferSearch());
        return forward("/mobility/jobOffers.jsp");
    }

    public ActionForward employeeOffers(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) {
        OfferSearch offerSearch = getRenderedObject("offerSearch");
        if (offerSearch == null) {
            offerSearch = new OfferSearch(OfferSearchOwner.ALL, OfferSearchState.ACTIVE);
        }
        request.setAttribute("offerSearch", offerSearch);
        request.setAttribute("processes", offerSearch.doWorkerOfferSearch());
        return forward("/mobility/employeeOffers.jsp");
    }

    public ActionForward prepareToCreateJobOffer(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        JobOfferBean jobOfferBean = new JobOfferBean();
        request.setAttribute("jobOfferBean", jobOfferBean);
        return forward("/mobility/createJobOffer.jsp");
    }

    public ActionForward createJobOffer(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) {
        JobOfferBean jobOfferBean = getRenderedObject();
        JobOffer jobOffer = jobOfferBean.create();
        return ProcessManagement.forwardToProcess(jobOffer.getJobOfferProcess());
    }

    public ActionForward viewJobOfferProcessToManage(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
        return ProcessManagement.forwardToProcess(jobOfferProcess);
    }

    public ActionForward viewWorkerOfferProcessToManage(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        WorkerOfferProcess workerOfferProcess = getDomainObject(request, "OID");
        return ProcessManagement.forwardToProcess(workerOfferProcess);
    }

    public ActionForward viewJobOfferProcess(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
        request.setAttribute("process", jobOfferProcess);
        return forward("/mobility/showJobOfferProcess.jsp");
    }

    public ActionForward viewWorkerOfferProcess(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        WorkerOfferProcess workerOfferProcess = getDomainObject(request, "OID");
        request.setAttribute("process", workerOfferProcess);
        return forward("/mobility/showWorkerOfferProcess.jsp");
    }

    public ActionForward prepareSubmitCandidacy(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
        SubmitCandidacyActivity submitCandidacyActivity = new SubmitCandidacyActivity();
        if (submitCandidacyActivity.isActive(jobOfferProcess)) {
            ActivityInformation<JobOfferProcess> activityInformation =
                    submitCandidacyActivity.getActivityInformation(jobOfferProcess);
            return new ProcessManagement().performActivityPostback(activityInformation, request);

        }
        request.setAttribute("process", jobOfferProcess);
        return forward("/mobility/showJobOfferProcess.jsp");
    }

    public ActionForward submitCandidacy(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) {
        ActivityInformation<JobOfferProcess> activityInformation = getRenderedObject();
        JobOfferProcess jobOfferProcess = activityInformation.getProcess();
        if (activityInformation.getActivity().isActive(jobOfferProcess)) {
            activityInformation.markHasForwardedFromInput();
            if (activityInformation.hasAllneededInfo()) {
                activityInformation.execute();
            } else {
                final WorkflowLayoutContext workflowLayoutContext = activityInformation.getProcess().getLayout();
                return new ProcessManagement().performActivityPostback(activityInformation, request);
            }
        }
        return returnToJobOfferProcess(mapping, form, request, response);
    }

    public ActionForward returnToJobOfferProcess(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        ActivityInformation<JobOfferProcess> activityInformation = getRenderedObject();
        request.setAttribute("OID", activityInformation.getProcess().getExternalId());
        if (activityInformation.getProcess().getCanManageProcess()) {
            return viewJobOfferProcessToManage(mapping, form, request, response);
        }
        return viewJobOfferProcess(mapping, form, request, response);
    }

    public ActionForward unSubmitCandidacy(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) {
        JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
        UnSubmitCandidacyActivity unSubmitCandidacyActivity = new UnSubmitCandidacyActivity();
        if (unSubmitCandidacyActivity.isActive(jobOfferProcess)) {
            ActivityInformation<JobOfferProcess> activityInformation =
                    unSubmitCandidacyActivity.getActivityInformation(jobOfferProcess);
            activityInformation.execute();
        }
        request.setAttribute("process", jobOfferProcess);
        return forward("/mobility/showJobOfferProcess.jsp");
    }

    public ActionForward viewJobOfferCandidacies(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        JobOfferProcess jobOfferProcess = getDomainObject(request, "OID");
        request.setAttribute("process", jobOfferProcess);
        return forward("/mobility/showJobOfferCandidacies.jsp");
    }

    public ActionForward portfolio(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) {
        final User user = Authenticate.getUser();
        if (user == null || user.getPerson() == null) {
            return frontPage(mapping, form, request, response);
        }
        final Person person = user.getPerson();
        if (person.getPersonalPortfolio() == null) {
            PersonalPortfolio.create(person);
        }
        final PersonalPortfolio personalPortfolio = person.getPersonalPortfolio();
        final PersonalPortfolioProcess personalPortfolioProcess = personalPortfolio.getPersonalPortfolioProcess();
        return ProcessManagement.forwardToProcess(personalPortfolioProcess);
    }

    public ActionForward configuration(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) {
        final MobilitySystem mobilitySystem = MobilitySystem.getInstance();
        request.setAttribute("mobilitySystem", mobilitySystem);
        return forward("/mobility/configuration.jsp");
    }

    public ActionForward prepareSelectOrganizationalModel(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        OrganizationModelAction.viewModels(request);
        return forward("/mobility/selectOrganizationalModel.jsp");
    }

    public ActionForward selectOrganizationalModel(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        final MobilitySystem mobilitySystem = MobilitySystem.getInstance();
        final OrganizationalModel organizationalModel = getDomainObject(request, "organizationalModelOid");
        mobilitySystem.setOrganizationalModel(organizationalModel);
        request.setAttribute("mobilitySystem", mobilitySystem);
        return forward("/mobility/configuration.jsp");
    }

    public ActionForward configureManagers(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) {
        final MobilitySystem mobilitySystem = MobilitySystem.getInstance();
        request.setAttribute("mobilitySystem", mobilitySystem);
        return forward("/mobility/configureManagers.jsp");
    }

    private ActionForward editProfessionalInfoPostback(final HttpServletRequest request,
            final PersonalPortfolioInfoInformation activityInformation, final PersonalPortfolioProcess personalPortfolioProcess) {
        return new ProcessManagement().performActivityPostback(activityInformation, request);
    }

    public ActionForward addNewQualification(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        final PersonalPortfolioInfoInformation activityInformation = getRenderedObject("activityBean");
        final PersonalPortfolioProcess personalPortfolioProcess = activityInformation.getProcess();

        RenderUtils.invalidateViewState();

        activityInformation.getQualificationHolders().add(new QualificationHolder());

        return editProfessionalInfoPostback(request, activityInformation, personalPortfolioProcess);
    }

    public ActionForward removeQualification(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        final PersonalPortfolioInfoInformation activityInformation = getRenderedObject("activityBean");
        final PersonalPortfolioProcess personalPortfolioProcess = activityInformation.getProcess();
        final WorkflowActivity<PersonalPortfolioProcess, ActivityInformation<PersonalPortfolioProcess>> activity =
                activityInformation.getActivity();
        String indexToRemove = (String) request.getAttribute("qualificationIndex");
        if (indexToRemove == null) {
            indexToRemove = request.getParameter("qualificationIndex");
        }

        RenderUtils.invalidateViewState();

        activityInformation.getQualificationHolders().remove(Integer.parseInt(indexToRemove));

        return editProfessionalInfoPostback(request, activityInformation, personalPortfolioProcess);
    }

    public ActionForward saveProfessionalInformation(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        final ProcessManagement processManagement = new ProcessManagement();
        return processManagement.process(mapping, form, request, response);
    }

    public ActionForward updatePersonalPortfolioInfo(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        final WorkerJobOfferInformation workerJobOfferInformation = getRenderedObject("CreateWorkerJobOffer");
        if (workerJobOfferInformation != null) {
            workerJobOfferInformation.getActivity().execute(workerJobOfferInformation);
            WorkerOffer workerOffer = workerJobOfferInformation.getWorkerOffer();
            return ProcessManagement.forwardToProcess(workerOffer.getWorkerOfferProcess());
        }
        return portfolio(mapping, form, request, response);
    }

    public ActionForward addPersonToJobOfferJury(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        final JobOfferJuryInformation jobOfferJuryInformation = getRenderedObject();
        jobOfferJuryInformation.addJuryMember();
        RenderUtils.invalidateViewState();
        return jobOfferJuryInfoPostback(request, jobOfferJuryInformation);
    }

    public ActionForward removePersonToJobOfferJury(final ActionMapping mapping, final ActionForm form,
            final HttpServletRequest request, final HttpServletResponse response) {
        final JuryMember juryMember = getDomainObject(request, "OID");
        JobOfferJuryDefinitionActivity jobOfferJuryDefinitionActivity = new JobOfferJuryDefinitionActivity();
        JobOfferProcess jobOfferProcess = juryMember.getJobOffer().getJobOfferProcess();
        if (jobOfferJuryDefinitionActivity.isActive(jobOfferProcess)) {
            JobOfferJuryInformation jobOfferJuryInformation =
                    (JobOfferJuryInformation) jobOfferJuryDefinitionActivity.getActivityInformation(jobOfferProcess);
            jobOfferJuryInformation.removeJuryMember(juryMember);
            return jobOfferJuryInfoPostback(request, jobOfferJuryInformation);
        }
        return ProcessManagement.forwardToProcess(jobOfferProcess);
    }

    public ActionForward setJuryPresident(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
            final HttpServletResponse response) {
        final JuryMember juryMember = getDomainObject(request, "OID");
        JobOfferJuryDefinitionActivity jobOfferJuryDefinitionActivity = new JobOfferJuryDefinitionActivity();
        JobOfferProcess jobOfferProcess = juryMember.getJobOffer().getJobOfferProcess();
        if (jobOfferJuryDefinitionActivity.isActive(jobOfferProcess)) {
            JobOfferJuryInformation jobOfferJuryInformation =
                    (JobOfferJuryInformation) jobOfferJuryDefinitionActivity.getActivityInformation(jobOfferProcess);
            jobOfferJuryInformation.setJuryPresident(juryMember);
            return jobOfferJuryInfoPostback(request, jobOfferJuryInformation);
        }
        return ProcessManagement.forwardToProcess(jobOfferProcess);
    }

    private ActionForward jobOfferJuryInfoPostback(final HttpServletRequest request,
            final JobOfferJuryInformation jobOfferJuryInformation) {
        return new ProcessManagement().performActivityPostback(jobOfferJuryInformation, request);
    }
}
