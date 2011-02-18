package module.mobility.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import module.organization.domain.Accountability;
import module.organization.domain.AccountabilityType;
import module.organization.domain.Party;
import module.organization.domain.Person;
import module.organizationIst.domain.IstAccountabilityType;
import pt.ist.fenixWebFramework.services.Service;

public class PersonalPortfolio extends PersonalPortfolio_Base {

	public PersonalPortfolio(final Person person) {
		super();
		setMobilitySystem(MobilitySystem.getInstance());
		setPerson(person);
		setNotificationService(isEmployee(person));
		new PersonalPortfolioProcess(this);
	}

	private Boolean isEmployee(Person person) {
		for (Accountability accountability : person
				.getParentAccountabilities(IstAccountabilityType.PERSONNEL
						.readAccountabilityType())) {
			if (accountability.isActiveNow()) {
				return true;
			}
		}
		return false;
	}

	@Service
	public static PersonalPortfolio create(final Person person) {
		return new PersonalPortfolio(person);
	}

	public PersonalPortfolioInfo getLastPersonalPortfolioInfo() {
		return hasAnyPersonalPortfolioInfo() ? Collections
				.max(getPersonalPortfolioInfoSet()) : null;
	}

	public Set<WorkerOffer> getWorkerOffer() {
		final Set<WorkerOffer> workerOffers = new TreeSet<WorkerOffer>();
		for (final PersonalPortfolioInfo personalPortfolioInfo : getPersonalPortfolioInfoSet()) {
			workerOffers.addAll(personalPortfolioInfo.getWorkerOfferSet());
		}
		return workerOffers;
	}

	public void addCurriculum(
			final PersonalPortfolioCurriculum personalPortfolioCurriculum) {
		PersonalPortfolioInfo personalPortfolioInfo = getLastPersonalPortfolioInfo();
		if (personalPortfolioInfo == null) {
			throw new NullPointerException("no.personalPortfolioCurriculum");
		}
		if (!personalPortfolioInfo.canBeUpdated()) {
			personalPortfolioInfo = personalPortfolioInfo.duplicate();
		}
		personalPortfolioInfo
				.setPersonalPortfolioCurriculum(personalPortfolioCurriculum);
	}

	public String getEmail() {
		final RemotePerson remotePerson = getPerson().getRemotePerson();
		return remotePerson == null ? "" : remotePerson
				.getEmailForSendingEmails();
	}

	public Collection<Party> getWorkingPlaces() {
		List<AccountabilityType> accountabilityTypes = new ArrayList<AccountabilityType>();
		accountabilityTypes.add(IstAccountabilityType.PERSONNEL
				.readAccountabilityType());
		accountabilityTypes.add(IstAccountabilityType.TEACHING_PERSONNEL
				.readAccountabilityType());
		accountabilityTypes.add(IstAccountabilityType.RESEARCH_PERSONNEL
				.readAccountabilityType());
		return getPerson().getParents(accountabilityTypes);
	}

	public boolean hasAnyActiveWorkerOfferOrPendingApproval() {
		for (WorkerOffer workerOffer : getWorkerOffer()) {
			if (workerOffer.isActiveOrPendingApproval()) {
				return true;
			}
		}
		return false;
	}
}
