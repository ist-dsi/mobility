package module.mobility.domain.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import module.mobility.domain.PersonalPortfolio;
import module.mobility.domain.PersonalPortfolioInfo;
import module.mobility.domain.PersonalPortfolioInfoQualification;
import module.mobility.domain.PersonalPortfolioProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.util.Money;
import pt.utl.ist.fenix.tools.util.i18n.Language;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class PersonalPortfolioInfoInformation extends ActivityInformation<PersonalPortfolioProcess> {

    public static class QualificationHolder implements Serializable {

	private String qualificationType;
	private String name;
	private String institution;
	private String date;
	private String classification;

	public String getQualificationType() {
	    return qualificationType;
	}
	public void setQualificationType(String qualificationType) {
	    this.qualificationType = qualificationType;
	}
	public String getName() {
	    return name;
	}
	public void setName(String name) {
	    this.name = name;
	}
	public String getInstitution() {
	    return institution;
	}
	public void setInstitution(String institution) {
	    this.institution = institution;
	}
	public String getDate() {
	    return date;
	}
	public void setDate(String date) {
	    this.date = date;
	}
	public String getClassification() {
	    return classification;
	}
	public void setClassification(String classification) {
	    this.classification = classification;
	}
    }

    private MultiLanguageString carrer;
    private MultiLanguageString category;
    private Money salary;

    private List<QualificationHolder> qualificationHolders = new ArrayList<QualificationHolder>();

    public PersonalPortfolioInfoInformation(final PersonalPortfolioProcess process,
	    WorkflowActivity<PersonalPortfolioProcess, ? extends ActivityInformation<PersonalPortfolioProcess>> activity) {
	super(process, activity);
	final PersonalPortfolio personalPortfolio = process.getPersonalPortfolio();
	final PersonalPortfolioInfo personalPortfolioInfo = personalPortfolio.getLastPersonalPortfolioInfo();
	if (personalPortfolioInfo != null) {
	    carrer = replicate(personalPortfolioInfo.getCarrer());
	    category = replicate(personalPortfolioInfo.getCategory());
	    salary = personalPortfolioInfo.getSalary();
	}
    }

    private MultiLanguageString replicate(final MultiLanguageString multiLanguageString) {
	if (multiLanguageString == null) {
	    return null;
	}
	final MultiLanguageString result = new MultiLanguageString();
	for (final Language language : multiLanguageString.getAllLanguages()) {
	    final String content = multiLanguageString.getContent(language);
	    result.setContent(language, content);
	}
	return result;
    }

    @Override
    public boolean hasAllneededInfo() {
	return isForwardedFromInput() && notEmpty(carrer) && notEmpty(category) && salary != null;
    }

    private boolean notEmpty(final MultiLanguageString multiLanguageString) {
	return multiLanguageString != null && !multiLanguageString.isEmpty();
    }

    public MultiLanguageString getCarrer() {
        return carrer;
    }

    public void setCarrer(MultiLanguageString carrer) {
        this.carrer = carrer;
    }

    public MultiLanguageString getCategory() {
        return category;
    }

    public void setCategory(MultiLanguageString category) {
        this.category = category;
    }

    public Money getSalary() {
        return salary;
    }

    public void setSalary(Money salary) {
        this.salary = salary;
    }

    public List<QualificationHolder> getQualificationHolders() {
        return qualificationHolders;
    }

    public void setQualificationHolders(List<QualificationHolder> qualificationHolders) {
        this.qualificationHolders = qualificationHolders;
    }

    public void updateQualifications(final PersonalPortfolioInfo personalPortfolioInfo) {
	final Iterator<PersonalPortfolioInfoQualification> iterator = personalPortfolioInfo.getPersonalPortfolioInfoQualificationIterator();
	for (final QualificationHolder qualificationHolder : qualificationHolders) {
	    final PersonalPortfolioInfoQualification qualification = iterator.hasNext() ? iterator.next() : new PersonalPortfolioInfoQualification(personalPortfolioInfo);
	    qualification.setQualificationType(qualificationHolder.getQualificationType());
	    qualification.setName(qualificationHolder.getName());
	    qualification.setInstitution(qualificationHolder.getInstitution());
	    qualification.setDate(qualificationHolder.getDate());
	    qualification.setClassification(qualificationHolder.getClassification());
	}
	int i = 0;
	for (final PersonalPortfolioInfoQualification qualification : personalPortfolioInfo.getPersonalPortfolioInfoQualificationSet()) {
	    if (++i > qualificationHolders.size()) {
//		qualification.delete();
	    }
	}
    }

}
