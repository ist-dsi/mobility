package module.mobility.domain;

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
	new JobOfferProcess(this);
    }

    public boolean getIsPendingConfirmation(User user) {
	return getCreator().equals(user.getPerson()) && getConfirmationDate() == null;
    }

}
