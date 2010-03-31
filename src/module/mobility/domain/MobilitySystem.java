package module.mobility.domain;

import myorg.domain.MyOrg;
import pt.ist.fenixWebFramework.services.Service;

public class MobilitySystem extends MobilitySystem_Base {

    public static MobilitySystem getInstance() {
	final MyOrg myOrg = MyOrg.getInstance();
	if (!myOrg.hasMobilitySystem()) {
	    initialize();
	}
	return myOrg.getMobilitySystem();
    }

    @Service
    public synchronized static void initialize() {
	final MyOrg myOrg = MyOrg.getInstance();
	if (!myOrg.hasMobilitySystem()) {
	    new MobilitySystem(myOrg);
	}
    }

    private MobilitySystem(final MyOrg myOrg) {
	super();
	setMyOrg(myOrg);
    }

}
