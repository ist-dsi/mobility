/*
 * @(#)PersonalPortfolioProcess.java
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
import java.util.stream.Stream;

import module.mobility.domain.activity.CreateWorkerJobOffer;
import module.mobility.domain.activity.DefineNewPersonalPortfolioInfo;
import module.mobility.domain.activity.DisableNotificationServiceActivity;
import module.mobility.domain.activity.EnableNotificationServiceActivity;
import module.mobility.domain.activity.UpdatePersonalPortfolioInfo;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.ProcessFile;
import module.workflow.domain.WorkflowProcess;

import org.fenixedu.bennu.core.domain.User;

/**
 * 
 * @author João Antunes
 * @author Luis Cruz
 * @author Susana Fernandes
 * 
 */
public class PersonalPortfolioProcess extends PersonalPortfolioProcess_Base {

    private static final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activities;

    static {
        final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activitiesAux =
                new ArrayList<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>>();
        activitiesAux.add(new DefineNewPersonalPortfolioInfo());
        activitiesAux.add(new UpdatePersonalPortfolioInfo());
        activitiesAux.add(new CreateWorkerJobOffer());
        activitiesAux.add(new EnableNotificationServiceActivity());
        activitiesAux.add(new DisableNotificationServiceActivity());
        activities = Collections.unmodifiableList(activitiesAux);
    }

    public PersonalPortfolioProcess(final PersonalPortfolio personalPortfolio) {
        super();
        setPersonalPortfolio(personalPortfolio);
    }

    @Override
    public <T extends WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> List<T> getActivities() {
        return (List) activities;
    }

    @Override
    public <T extends WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> Stream<T> getActivityStream() {
        return ((List) activities).stream();
    }
    
    @Override
    public User getProcessCreator() {
        return getPersonalPortfolio().getPerson().getUser();
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void notifyUserDueToComment(User user, String comment) {
        // do nothing.
    }

    @Override
    public boolean isCommentsSupportAvailable() {
        return false;
    }

    @Override
    public List<Class<? extends ProcessFile>> getAvailableFileTypes() {
        final List<Class<? extends ProcessFile>> list = super.getAvailableFileTypes();
        list.add(0, PersonalPortfolioCurriculum.class);
        return list;
    }

    @Override
    public boolean isTicketSupportAvailable() {
        return false;
    }
    @Deprecated
    public boolean hasPersonalPortfolio() {
        return getPersonalPortfolio() != null;
    }

}
