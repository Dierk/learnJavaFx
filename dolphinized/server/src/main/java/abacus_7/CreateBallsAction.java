package abacus_7;

import org.opendolphin.core.comm.Command;
import org.opendolphin.core.server.DTO;
import org.opendolphin.core.server.Slot;
import org.opendolphin.core.server.action.DolphinServerAction;
import org.opendolphin.core.server.comm.ActionRegistry;
import org.opendolphin.core.server.comm.CommandHandler;

import java.util.List;

import static abacus_7.AbacusConstants.*;

public class CreateBallsAction extends DolphinServerAction {

    public void registerIn(ActionRegistry actionRegistry) {
        actionRegistry.register(CMD_CREATE_BALLS, new CommandHandler<Command>() {
            public void handleCommand(Command command, List<Command> response) {
                for (int rail = 0; rail < ROW_COUNT; rail++) { // scales go from 0 to 9 for factors 10**0 .. 10**9
                    String railId = "RAIL-" + rail;
                    presentationModel(railId, TYPE_RAIL, new DTO(new Slot(ATT_SCALE, rail)));
                    for (int ball = 1; ball <= COL_COUNT; ball++) { // digit balls go from 1 to 10
                        presentationModel(ballId(rail, ball), TYPE_BALL, new DTO(
                                new Slot(ATT_RAIL_ID, railId),
                                new Slot(ATT_DIGIT, ball),
                                new Slot(ATT_ON, false, ballId(rail,ball))) // set qualifier for back-propagation of values
                        );
                    }
                }
            }
        });
    }

}
