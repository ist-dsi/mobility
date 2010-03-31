package module.mobility.domain.util;

import java.io.Serializable;
import java.util.Calendar;

import module.mobility.domain.JobOffer;

import org.joda.time.DateTime;

import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class JobOfferBean implements Serializable {

    private int year = Calendar.getInstance().get(Calendar.YEAR);
    private DateTime beginDate;
    private DateTime endDate;
    private MultiLanguageString title;

    public JobOfferBean() {
    }

    public JobOfferBean(JobOffer jobOffer) {
	setBeginDate(jobOffer.getBeginDate());
	setEndDate(jobOffer.getEndDate());
	setTitle(jobOffer.getTitle());
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

    public MultiLanguageString getTitle() {
	return title;
    }

    public void setTitle(MultiLanguageString title) {
	this.title = title;
    }

    public int getYear() {
	return year;
    }

    public void setYear(int year) {
	this.year = year;
    }

    @Service
    public JobOffer create() {
	return new JobOffer(year, beginDate, endDate, title);
    }

}
