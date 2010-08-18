package module.mobility.domain;

import module.organization.domain.Person;
import myorg.applicationTier.Authenticate.UserView;
import myorg.domain.User;

import org.joda.time.DateTime;

public class Offer extends Offer_Base implements Comparable<Offer> {

    public Offer() {
	super();
	setCanceled(Boolean.FALSE);
    }

    @Override
    public int compareTo(final Offer offer) {
	final DateTime creationDate = offer.getCreationDate();
	final int c = getCreationDate().compareTo(creationDate);
	return c == 0 ? hashCode() - offer.hashCode() : c;
    }

    public boolean isPendingApproval() {
	return !getCanceled() && getSubmittedForApprovalDate() != null && getApprovalDate() == null;
    }

    public boolean isUnderConstruction() {
	return !getCanceled() && getSubmittedForApprovalDate() == null;
    }

    public boolean isUnderConstruction(User user) {
	return !getCanceled() && getOwner().equals(user.getPerson()) && getSubmittedForApprovalDate() == null;
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

    public boolean isActive() {
	DateTime now = new DateTime();
	return !getCanceled() && (getEndDate() == null || getEndDate().isAfter(new DateTime())) && getBeginDate().isBefore(now);
    }

    public void approve() {
	setApprovalDate(new DateTime());
	setApprover(MobilitySystem.getInstance().getManagementAccountability(UserView.getCurrentUser()));
    }

    public Person getOwner() {
	return null;
    }

    public OfferProcess getProcess() {
	return null;
    }
}
