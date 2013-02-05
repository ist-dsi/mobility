/*
 * @(#)PersonalPortfolioInfoInformation.java
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import module.mobility.domain.CareerType;
import module.mobility.domain.PersonalPortfolio;
import module.mobility.domain.PersonalPortfolioInfo;
import module.mobility.domain.PersonalPortfolioInfoQualification;
import module.mobility.domain.PersonalPortfolioProcess;
import module.workflow.activities.ActivityInformation;
import module.workflow.activities.WorkflowActivity;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author Luis Cruz
 * @author Susana Fernandes
 * 
 */
public class PersonalPortfolioInfoInformation extends ActivityInformation<PersonalPortfolioProcess> {

    public static class QualificationHolder implements Serializable {

        private String qualificationType;
        private String name;
        private String institution;
        private String date;
        private String classification;

        public QualificationHolder() {
        }

        public QualificationHolder(final PersonalPortfolioInfoQualification qualification) {
            qualificationType = qualification.getQualificationType();
            name = qualification.getName();
            institution = qualification.getInstitution();
            date = qualification.getDate();
            classification = qualification.getClassification();
        }

        public String getQualificationType() {
            return qualificationType;
        }

        public void setQualificationType(String qualificationType) {
            this.qualificationType = qualificationType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInstitution() {
            return institution;
        }

        public void setInstitution(String institution) {
            this.institution = institution;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getClassification() {
            return classification;
        }

        public void setClassification(String classification) {
            this.classification = classification;
        }
    }

    private CareerType carrer;
    private String category;

    private List<QualificationHolder> qualificationHolders = new ArrayList<QualificationHolder>();

    public PersonalPortfolioInfoInformation(final PersonalPortfolioProcess process,
            WorkflowActivity<PersonalPortfolioProcess, ? extends ActivityInformation<PersonalPortfolioProcess>> activity) {
        super(process, activity);
        final PersonalPortfolio personalPortfolio = process.getPersonalPortfolio();
        final PersonalPortfolioInfo personalPortfolioInfo = personalPortfolio.getLastPersonalPortfolioInfo();
        if (personalPortfolioInfo != null) {
            carrer = personalPortfolioInfo.getCarrer();
            category = personalPortfolioInfo.getCategory();
            for (final PersonalPortfolioInfoQualification qualification : personalPortfolioInfo
                    .getPersonalPortfolioInfoQualificationSet()) {
                final QualificationHolder qualificationHolder = new QualificationHolder(qualification);
                qualificationHolders.add(qualificationHolder);
            }
        }
    }

    @Override
    public boolean hasAllneededInfo() {
        return (isForwardedFromInput() && carrer != null && !StringUtils.isEmpty(category)) ? areQualificationsValid() : false;
    }

    private boolean areQualificationsValid() {
        for (QualificationHolder qualificationHolder : qualificationHolders) {
            if (!isValid(qualificationHolder)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValid(QualificationHolder qualificationHolder) {
        return !StringUtils.isEmpty(qualificationHolder.getQualificationType())
                && !StringUtils.isEmpty(qualificationHolder.getName())
                && !StringUtils.isEmpty(qualificationHolder.getInstitution())
                && !StringUtils.isEmpty(qualificationHolder.getDate());
    }

    public CareerType getCarrer() {
        return carrer;
    }

    public void setCarrer(CareerType carrer) {
        this.carrer = carrer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<QualificationHolder> getQualificationHolders() {
        return qualificationHolders;
    }

    public void setQualificationHolders(List<QualificationHolder> qualificationHolders) {
        this.qualificationHolders = qualificationHolders;
    }

    public void updateQualifications(final PersonalPortfolioInfo personalPortfolioInfo) {
        final int currentCount = personalPortfolioInfo.getPersonalPortfolioInfoQualificationCount();
        final int newCount = qualificationHolders.size();
        if (currentCount > newCount) {
            for (int i = newCount; i++ < currentCount; personalPortfolioInfo.getPersonalPortfolioInfoQualificationIterator()
                    .next().delete()) {
                ;
            }
        } else if (newCount > currentCount) {
            for (int i = currentCount; i++ < newCount; new PersonalPortfolioInfoQualification(personalPortfolioInfo)) {
                ;
            }
        }

        final Iterator<PersonalPortfolioInfoQualification> iterator =
                personalPortfolioInfo.getPersonalPortfolioInfoQualificationIterator();
        for (final QualificationHolder qualificationHolder : qualificationHolders) {
            final PersonalPortfolioInfoQualification qualification = iterator.next();
            qualification.setQualificationType(qualificationHolder.getQualificationType());
            qualification.setName(qualificationHolder.getName());
            qualification.setInstitution(qualificationHolder.getInstitution());
            qualification.setDate(qualificationHolder.getDate());
            qualification.setClassification(qualificationHolder.getClassification());
        }
    }

}
