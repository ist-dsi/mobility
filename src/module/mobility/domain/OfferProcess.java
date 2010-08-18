package module.mobility.domain;

import java.util.List;

import module.mobility.domain.util.MobilityProcessStageView;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.WorkflowProcess;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;

public class OfferProcess extends OfferProcess_Base implements Comparable<OfferProcess> {

    public OfferProcess() {
	super();
    }

    @Override
    public <T extends WorkflowActivity<? extends WorkflowProcess, ? extends ActivityInformation>> List<T> getActivities() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public User getProcessCreator() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean isActive() {
	return getOffer().isActive();
    }

    @Override
    public void notifyUserDueToComment(User user, String comment) {
	// TODO Auto-generated method stub
    }

    @Override
    public boolean isAccessible(User user) {
	return getProcessCreator().equals(user) || (getOffer().isApproved() && isActive())
		|| (MobilitySystem.getInstance().isManagementMember(user) && !getOffer().isUnderConstruction());
    }

    @Override
    public int compareTo(OfferProcess otherOfferProcess) {
	return getOffer().compareTo(otherOfferProcess.getOffer());
    }

    public String getProcessIdentification() {
	return getMobilityYear().getYear() + "/" + getProcessNumber();
    }

    protected MobilityYear getMobilityYear() {
	return getOffer().getMobilityYear();
    }

    protected Offer getOffer() {
	return null;
    }

    public MobilityProcessStageView getMobilityProcessStageView() {
	return new MobilityProcessStageView(getOffer());
    }

    public boolean getCanManageProcess() {
	final User user = UserView.getCurrentUser();
	return getProcessCreator().equals(user) || (MobilitySystem.getInstance().isManagementMember(user));
    }

}
