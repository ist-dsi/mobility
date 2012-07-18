/*
 * @(#)WorkerJobOfferInformation.java
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
package module.mobility.domain.activity;

import java.util.Calendar;
import java.util.List;

import module.mobility.domain.PersonalPortfolioProcess;
import module.mobility.domain.WorkerOffer;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;
import module.workflow.domain.ProcessFile;

import org.joda.time.LocalDate;

/**
 * 
 * @author Luis Cruz
 * @author Susana Fernandes
 * 
 */
public class WorkerJobOfferInformation extends ActivityInformation<PersonalPortfolioProcess> {

    private int year = Calendar.getInstance().get(Calendar.YEAR);
    private LocalDate beginDate = new LocalDate();
    private LocalDate endDate;

    private Boolean displayPhoto = Boolean.FALSE;
    private Boolean displayName = Boolean.FALSE;
    private Boolean displayDateOfBirth = Boolean.FALSE;

    private Boolean displayCarrer = Boolean.FALSE;
    private Boolean displayCategory = Boolean.FALSE;

    private Boolean displayQualifications = Boolean.FALSE;
    private Boolean displayCurriculum = Boolean.FALSE;

    private WorkerOffer workerOffer;

    private List<ProcessFile> files;

    public WorkerJobOfferInformation(final PersonalPortfolioProcess process,
	    WorkflowActivity<PersonalPortfolioProcess, ? extends ActivityInformation<PersonalPortfolioProcess>> activity) {
	super(process, activity);
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

    public int getYear() {
	return year;
    }

    public void setYear(int year) {
	this.year = year;
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

    public WorkerOffer getWorkerOffer() {
	return workerOffer;
    }

    public void setWorkerOffer(WorkerOffer workerOffer) {
	this.workerOffer = workerOffer;
    }

    public List<ProcessFile> getFiles() {
	return files;
    }

    public void setFiles(List<ProcessFile> files) {
	this.files = files;
    }

}
