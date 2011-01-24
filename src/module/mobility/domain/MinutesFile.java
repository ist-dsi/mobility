package module.mobility.domain;

import module.workflow.domain.WorkflowProcess;
import myorg.util.ClassNameBundle;

@ClassNameBundle(bundle = "resources/MobilityResources")
public class MinutesFile extends MinutesFile_Base {

    public MinutesFile(JobOffer jobOffer, String displayName, String filename, byte[] content) {
	super();
	init(displayName, filename, content);
	setProcess(jobOffer.getJobOfferProcess());
    }

    public MinutesFile(String displayName, String filename, byte[] content) {
	super();
	if (content != null) {
	    init(displayName, filename, content);
	}
    }

    @Override
    public void setProcess(final WorkflowProcess process) {
	super.setProcess(process);
    }

}
