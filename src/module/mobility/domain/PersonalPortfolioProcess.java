package module.mobility.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import module.mobility.domain.activity.CreateWorkerJobOffer;
import module.mobility.domain.activity.DefineNewPersonalPortfolioInfo;
import module.mobility.domain.activity.UpdatePersonalPortfolioInfo;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.WorkflowProcess;
import myorg.domain.User;

public class PersonalPortfolioProcess extends PersonalPortfolioProcess_Base {

    private static final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activities;

    static {
	final List<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> activitiesAux = new ArrayList<WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>>();
	activitiesAux.add(new DefineNewPersonalPortfolioInfo());
	activitiesAux.add(new UpdatePersonalPortfolioInfo());
	activitiesAux.add(new CreateWorkerJobOffer());
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

    @Override
    public boolean isCommentsSupportAvailable() {
	return false;
    }

}
