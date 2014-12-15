/*
 * @(#)WorkerOfferProcess.java
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import module.mobility.domain.activity.CancelWorkerJobOfferApprovalActivity;
import module.mobility.domain.activity.CancelWorkerJobOfferSubmitionForApprovalActivity;
import module.mobility.domain.activity.CancelWorkerOfferActivity;
import module.mobility.domain.activity.EditWorkerJobOffer;
import module.mobility.domain.activity.SubmitWorkerJobOfferForApprovalActivity;
import module.mobility.domain.activity.UpdateWorkerJobOfferProfessionalInformation;
import module.mobility.domain.activity.WorkerJobOfferApprovalActivity;
import module.mobility.domain.util.MobilityWorkerOfferProcessStageView;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.WorkflowProcess;
import module.workflow.domain.utils.WorkflowCommentCounter;
import module.workflow.util.ClassNameBundle;
import module.workflow.widgets.UnreadCommentsWidget;

import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.core.security.Authenticate;

@ClassNameBundle(bundle = "MobilityResources")
/**
 * 
 * @author João Antunes
 * @author Luis Cruz
 * @author Susana Fernandes
 * 
 */
public class WorkerOfferProcess extends WorkerOfferProcess_Base implements Comparable<WorkerOfferProcess> {

    private static final String WORKER_OFFER_SIGLA = "PRO";
    private static final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activities;

    static {
        final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activitiesAux =
                new ArrayList<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>>();
        activitiesAux.add(new EditWorkerJobOffer());
        activitiesAux.add(new UpdateWorkerJobOfferProfessionalInformation());
        activitiesAux.add(new SubmitWorkerJobOfferForApprovalActivity());
        activitiesAux.add(new CancelWorkerJobOfferSubmitionForApprovalActivity());
        activitiesAux.add(new WorkerJobOfferApprovalActivity());
        activitiesAux.add(new CancelWorkerJobOfferApprovalActivity());
        activitiesAux.add(new CancelWorkerOfferActivity());
        activities = Collections.unmodifiableList(activitiesAux);

        UnreadCommentsWidget.register(new WorkflowCommentCounter(WorkerOfferProcess.class));
    }

    public WorkerOfferProcess(final WorkerOffer workerOffer) {
        super();
        setWorkerOffer(workerOffer);
        setProcessNumber(workerOffer.getMobilityYear().nextWorkerOfferNumber().toString());
    }

    protected MobilityYear getMobilityYear() {
        return getWorkerOffer().getMobilityYear();
    }

    @Override
    public <T extends WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> List<T> getActivities() {
        return (List) activities;
    }

    @Override
    public User getProcessCreator() {
        return getWorkerOffer().getPersonalPortfolioInfo().getPersonalPortfolio().getPerson().getUser();
    }

    public static Set<WorkerOfferProcess> getWorkerJobOfferProcessByUser(User user) {
        Set<WorkerOfferProcess> processes = new TreeSet<WorkerOfferProcess>();
        for (WorkerOffer workerOffer : MobilitySystem.getInstance().getWorkerOfferSet()) {
            if (workerOffer.getWorkerOfferProcess().isAccessible(user)) {
                processes.add(workerOffer.getWorkerOfferProcess());
            }
        }
        return processes;
    }

    @Override
    public boolean isActive() {
        return getWorkerOffer().isActive();
    }

    @Override
    public void notifyUserDueToComment(User user, String comment) {
        List<String> toAddress = new ArrayList<String>();
        final String email = user.getProfile() == null ? null : user.getProfile().getEmail();
        if (email != null) {
            toAddress.add(email);

            final User loggedUser = Authenticate.getUser();
            throw new Error("Reimplement");
//            new Email(virtualHost.getApplicationSubTitle().getContent(), virtualHost.getSystemEmailAddress(), new String[] {},
//                    toAddress, Collections.EMPTY_LIST, Collections.EMPTY_LIST, BundleUtil.getFormattedStringFromResourceBundle(
//                            "resources/MobilityResources", "label.email.commentCreated.subject", getProcessIdentification()),
//                    BundleUtil.getFormattedStringFromResourceBundle("resources/MobilityResources",
//                            "label.email.commentCreated.body", loggedUser.getPerson().getName(), getProcessIdentification(),
//                            comment));
        }
    }

    @Override
    public boolean isAccessible(User user) {
        return getProcessCreator().equals(user) || (getWorkerOffer().isApproved() && isActive())
                || (MobilitySystem.getInstance().isManagementMember(user) && !getWorkerOffer().isUnderConstruction());
    }

    @Override
    public int compareTo(WorkerOfferProcess otherOfferProcess) {
        return getWorkerOffer().compareTo(otherOfferProcess.getWorkerOffer());
    }

    public String getProcessIdentification() {
        return WORKER_OFFER_SIGLA + getMobilityYear().getYear() + "/" + getProcessNumber();
    }

    public MobilityWorkerOfferProcessStageView getMobilityProcessStageView() {
        return new MobilityWorkerOfferProcessStageView(getWorkerOffer());
    }

    public boolean getCanManageProcess() {
        final User user = Authenticate.getUser();
        return getProcessCreator().equals(user) || (MobilitySystem.getInstance().isManagementMember(user));
    }

    @Override
    public boolean isTicketSupportAvailable() {
        return false;
    }

    @Deprecated
    public boolean hasWorkerOffer() {
        return getWorkerOffer() != null;
    }

}
