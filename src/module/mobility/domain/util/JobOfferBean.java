package module.mobility.domain.util;

import java.io.Serializable;

import module.mobility.domain.JobOffer;

import org.joda.time.DateTime;

import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class JobOfferBean implements Serializable {
    private DateTime beginDate;
    private DateTime endDate;
    private MultiLanguageString title;

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

    @Service
    public JobOffer create() {
	return new JobOffer(beginDate, endDate, title);
    }
}
