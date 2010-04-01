package module.mobility.domain.activity;

import module.mobility.domain.WorkerOffer;
import module.mobility.domain.WorkerOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

import org.joda.time.DateTime;

public class EditWorkerJobOfferInformation extends ActivityInformation<WorkerOfferProcess> {

    private DateTime beginDate;
    private DateTime endDate;

    private Boolean displayName = Boolean.FALSE;
    private Boolean displayDateOfBirth = Boolean.FALSE;
    private Boolean displayCarrer = Boolean.FALSE;
    private Boolean displayCategory = Boolean.FALSE;
    private Boolean displaySalary = Boolean.FALSE;

    public EditWorkerJobOfferInformation(final WorkerOfferProcess process,
	    WorkflowActivity<WorkerOfferProcess, ? extends ActivityInformation<WorkerOfferProcess>> activity) {
	super(process, activity);
	final WorkerOffer workerOffer = process.getWorkerOffer();
        setBeginDate(workerOffer.getBeginDate());
        setEndDate(workerOffer.getEndDate());

        setDisplayCarrer(workerOffer.getDisplayName());
        setDisplayDateOfBirth(workerOffer.getDisplayDateOfBirth());
        setDisplayCarrer(workerOffer.getDisplayCarrer());
        setDisplayCategory(workerOffer.getDisplayCategory());
        setDisplaySalary(workerOffer.getDisplaySalary());
    }

    @Override
    public boolean hasAllneededInfo() {
        return isForwardedFromInput() && beginDate != null;
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

    public Boolean getDisplayName() {
        return displayName;
    }

    public void setDisplayName(Boolean displayName) {
        this.displayName = displayName;
    }

    public Boolean getDisplayDateOfBirth() {
        return displayDateOfBirth;
    }

    public void setDisplayDateOfBirth(Boolean displayDateOfBirth) {
        this.displayDateOfBirth = displayDateOfBirth;
    }

    public Boolean getDisplayCarrer() {
        return displayCarrer;
    }

    public void setDisplayCarrer(Boolean displayCarrer) {
        this.displayCarrer = displayCarrer;
    }

    public Boolean getDisplayCategory() {
        return displayCategory;
    }

    public void setDisplayCategory(Boolean displayCategory) {
        this.displayCategory = displayCategory;
    }

    public Boolean getDisplaySalary() {
        return displaySalary;
    }

    public void setDisplaySalary(Boolean displaySalary) {
        this.displaySalary = displaySalary;
    }

}
