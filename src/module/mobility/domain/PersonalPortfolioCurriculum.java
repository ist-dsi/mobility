package module.mobility.domain;

import module.workflow.domain.ProcessFile;
import module.workflow.domain.WorkflowProcess;
import myorg.util.ClassNameBundle;

@ClassNameBundle(bundle = "resources/MobilityResources")
public class PersonalPortfolioCurriculum extends PersonalPortfolioCurriculum_Base {

    public PersonalPortfolioCurriculum(String displayName, String filename, byte[] content) {
	super();
	if (content != null) {
	    init(displayName, filename, content);
	}
    }

    @Override
    public void setProcess(final WorkflowProcess process) {
        super.setProcess(process);
        if (process != null) {
            final PersonalPortfolioProcess personalPortfolioProcess = (PersonalPortfolioProcess) process;
            final PersonalPortfolio personalPortfolio = personalPortfolioProcess.getPersonalPortfolio();
            personalPortfolio.addCurriculum(this);
            for (final ProcessFile processFile : personalPortfolioProcess.getFilesSet()) {
        	if (processFile instanceof PersonalPortfolioCurriculum && processFile != this) {
        	    personalPortfolioProcess.removeFiles(processFile);
        	}
            }
        }
    }

}
