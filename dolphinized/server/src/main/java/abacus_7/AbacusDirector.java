package abacus_7;

import org.opendolphin.core.server.action.DolphinServerAction;
import org.opendolphin.core.server.comm.ActionRegistry;

public class AbacusDirector extends DolphinServerAction {

    public void registerIn(ActionRegistry registry) {
        getServerDolphin().register(new CreateBallsAction());
        getServerDolphin().register(new ToggleAction());
    }
}
