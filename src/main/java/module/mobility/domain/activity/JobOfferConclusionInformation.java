/*
 * @(#)JobOfferConclusionInformation.java
 *
 * Copyright 2011 Instituto Superior Tecnico
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
package module.mobility.domain.activity;

import java.io.IOException;
import java.io.InputStream;

import module.mobility.domain.JobOfferProcess;
import module.mobility.domain.MinutesFile;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.ProcessFile;

import com.google.common.io.ByteStreams;

/**
 * 
 * @author Susana Fernandes
 * 
 */
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
        try {
            return ByteStreams.toByteArray(getInputStream());
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
