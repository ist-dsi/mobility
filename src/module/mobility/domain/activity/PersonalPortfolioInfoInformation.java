package module.mobility.domain.activity;

import module.mobility.domain.PersonalPortfolio;
import module.mobility.domain.PersonalPortfolioInfo;
import module.mobility.domain.PersonalPortfolioProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import myorg.domain.util.Money;
import pt.utl.ist.fenix.tools.util.i18n.Language;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class PersonalPortfolioInfoInformation extends ActivityInformation<PersonalPortfolioProcess> {

    private MultiLanguageString carrer;
    private MultiLanguageString category;
    private Money salary;

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

}
