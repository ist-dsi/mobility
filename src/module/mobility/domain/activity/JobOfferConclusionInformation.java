package module.mobility.domain.activity;

import java.io.InputStream;

import module.mobility.domain.JobOfferProcess;
import module.mobility.domain.MinutesFile;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.ProcessFile;
import myorg.util.InputStreamUtil;

public class JobOfferConclusionInformation extends ActivityInformation<JobOfferProcess> {

    private transient InputStream inputStream;
    private String filename;
    private String displayName;

    public JobOfferConclusionInformation(final JobOfferProcess process,
	    WorkflowActivity<JobOfferProcess, ? extends ActivityInformation<JobOfferProcess>> activity) {
	super(process, activity);
    }

    @Override
    public boolean hasAllneededInfo() {
	return (hasMinuteFile(getProcess())) || (!getProcess().getJobOffer().hasAnyChosenCandidate()) || getInputStream() != null;
    }

    private boolean hasMinuteFile(JobOfferProcess process) {
	for (ProcessFile processFile : process.getFiles()) {
	    if (processFile instanceof MinutesFile) {
		return true;
	    }
	}
	return false;
    }

    public InputStream getInputStream() {
	return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
	this.inputStream = inputStream;
    }

    public String getFilename() {
	return filename;
    }

    public void setFilename(String filename) {
	this.filename = filename;
    }

    public byte[] getBytes() {
	return InputStreamUtil.consumeInputStream(getInputStream());
    }

    public String getDisplayName() {
	return displayName;
    }

    public void setDisplayName(String displayName) {
	this.displayName = displayName;
    }
}
