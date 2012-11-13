/*
 * @(#)PersonalPortfolioCurriculum.java
 *
 * Copyright 2010 Instituto Superior Tecnico
 * Founding Authors: Susana Fernandes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Internal Mobility Module.
 *
 *   The Internal Mobility Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Internal Mobility  Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Internal Mobility  Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.mobility.domain;

import pt.ist.bennu.core.util.ClassNameBundle;

import module.workflow.domain.ProcessFile;
import module.workflow.domain.WorkflowProcess;

@ClassNameBundle(bundle = "resources/MobilityResources")
/**
 * 
 * @author Luis Cruz
 * 
 */
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
