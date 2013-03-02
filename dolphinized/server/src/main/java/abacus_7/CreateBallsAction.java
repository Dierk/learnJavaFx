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
                createBallPresentationModels();
            }
        });
    }

    private void createBallPresentationModels() {
        for (int scale = 0; scale < ROW_COUNT; scale++) { // scales go from 0 to 9 for factors 10**0 .. 10**9
            for (int digit = 1; digit <= COL_COUNT; digit++) { // digit balls go from 1 to 10
                presentationModel(ballId(scale, digit), null, new DTO(
                    new Slot(ATT_SCALE, scale),
                    new Slot(ATT_DIGIT, digit),
                    new Slot(ATT_ON, false, ballId(scale,digit))) // set qualifier for back-propagation of values
                );
            }
        }
    }

}
