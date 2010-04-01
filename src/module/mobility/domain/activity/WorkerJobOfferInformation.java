package module.mobility.domain.activity;

import java.util.Calendar;

import module.mobility.domain.PersonalPortfolioProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

import org.joda.time.DateTime;

public class WorkerJobOfferInformation extends ActivityInformation<PersonalPortfolioProcess> {

    private int year = Calendar.getInstance().get(Calendar.YEAR);
    private DateTime beginDate;
    private DateTime endDate;

    public WorkerJobOfferInformation(final PersonalPortfolioProcess process,
	    WorkflowActivity<PersonalPortfolioProcess, ? extends ActivityInformation<PersonalPortfolioProcess>> activity) {
	super(process, activity);
    }

    @Override
    public boolean hasAllneededInfo() {
	return isForwardedFromInput();
    }

    public DateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(DateTime beginDate) {
        this.beginDate = beginDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
