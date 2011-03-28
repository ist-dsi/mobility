package module.mobility.domain.activity;

import java.util.List;

import module.mobility.domain.JobOfferCandidacy;
import module.mobility.domain.JobOfferProcess;
import module.organization.domain.Person;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.ProcessFile;
import myorg.applicationTier.Authenticate.UserView;

public class SubmitCandidacyInformation extends ActivityInformation<JobOfferProcess> {
    private List<ProcessFile> files;

    public SubmitCandidacyInformation(final JobOfferProcess jobOfferProcess,
	    WorkflowActivity<JobOfferProcess, ? extends ActivityInformation<JobOfferProcess>> activity) {
	super(jobOfferProcess, activity);
	Person person = UserView.getCurrentUser().getPerson();
	JobOfferCandidacy jobOfferCandidacy = getProcess().getJobOffer().getCandidacy(person);
	if (jobOfferCandidacy != null) {
	    setFiles(jobOfferCandidacy.getCandidacyFile());
	}
    }

    @Override
    public boolean hasAllneededInfo() {
	return isForwardedFromInput() && (getProcess().getJobOffer().getOptionalDocuments() || !getFiles().isEmpty());
    }

    public List<ProcessFile> getFiles() {
	return files;
    }

    public void setFiles(List<ProcessFile> files) {
	this.files = files;
    }

}
