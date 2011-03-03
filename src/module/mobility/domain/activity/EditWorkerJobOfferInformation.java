package module.mobility.domain.activity;

import module.mobility.domain.WorkerOffer;
import module.mobility.domain.WorkerOfferProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

import org.joda.time.LocalDate;

public class EditWorkerJobOfferInformation extends ActivityInformation<WorkerOfferProcess> {

    private LocalDate beginDate;
    private LocalDate endDate;

    private Boolean displayPhoto;
    private Boolean displayName;
    private Boolean displayDateOfBirth;

    private Boolean displayCarrer;
    private Boolean displayCategory;

    private Boolean displayQualifications;
    private Boolean displayCurriculum;

    public EditWorkerJobOfferInformation(final WorkerOfferProcess process,
	    WorkflowActivity<WorkerOfferProcess, ? extends ActivityInformation<WorkerOfferProcess>> activity) {
	super(process, activity);
	final WorkerOffer workerOffer = process.getWorkerOffer();
	setBeginDate(workerOffer.getBeginDate().toLocalDate());
	setEndDate(workerOffer.getEndDate().toLocalDate());

	setDisplayPhoto(workerOffer.getDisplayPhoto());
	setDisplayName(workerOffer.getDisplayName());
	setDisplayDateOfBirth(workerOffer.getDisplayDateOfBirth());

	setDisplayCarrer(workerOffer.getDisplayCarrer());
	setDisplayCategory(workerOffer.getDisplayCategory());

	setDisplayQualifications(workerOffer.getDisplayQualifications());
	setDisplayCurriculum(workerOffer.getDisplayCurriculum());
    }

    @Override
    public boolean hasAllneededInfo() {
	return isForwardedFromInput() && beginDate != null;
    }

    public LocalDate getBeginDate() {
	return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
	this.beginDate = beginDate;
	setEndDate(beginDate.plusYears(1));
    }

    public LocalDate getEndDate() {
	return endDate;
    }

    public void setEndDate(LocalDate endDate) {
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

    public Boolean getDisplayPhoto() {
	return displayPhoto;
    }

    public void setDisplayPhoto(Boolean displayPhoto) {
	this.displayPhoto = displayPhoto;
    }

    public Boolean getDisplayQualifications() {
	return displayQualifications;
    }

    public void setDisplayQualifications(Boolean displayQualifications) {
	this.displayQualifications = displayQualifications;
    }

    public Boolean getDisplayCurriculum() {
	return displayCurriculum;
    }

    public void setDisplayCurriculum(Boolean displayCurriculum) {
	this.displayCurriculum = displayCurriculum;
    }

}
