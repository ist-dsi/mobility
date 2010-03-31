package module.mobility.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.WorkflowProcess;
import myorg.domain.User;

public class PersonalPortfolioProcess extends PersonalPortfolioProcess_Base {

    private static final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activities;

    static {
	final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activitiesAux = new ArrayList<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>>();
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
    
}
