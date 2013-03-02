package abacus_7;

import org.opendolphin.core.Attribute;
import org.opendolphin.core.PresentationModel;
import org.opendolphin.core.comm.Command;
import org.opendolphin.core.comm.ValueChangedCommand;
import org.opendolphin.core.server.ServerPresentationModel;
import org.opendolphin.core.server.action.DolphinServerAction;
import org.opendolphin.core.server.comm.ActionRegistry;
import org.opendolphin.core.server.comm.CommandHandler;

import java.util.List;
import java.util.StringTokenizer;

import static abacus_7.AbacusConstants.*;

public class ToggleAction extends DolphinServerAction {

    public void registerIn(ActionRegistry actionRegistry) {
        actionRegistry.register(CMD_TOGGLE, new CommandHandler<Command>() {
            public void handleCommand(Command command, List<Command> response) {

                ServerPresentationModel touchedBall = getServerDolphin().getAt(PM_TOUCHED);

                int digit = Integer.parseInt(touchedBall.getAt(ATT_DIGIT).getValue().toString());
                boolean on = Boolean.parseBoolean(touchedBall.getAt(ATT_ON).getValue().toString());
                String railId = touchedBall.getAt(ATT_RAIL_ID).getValue().toString();
                int scale = Integer.parseInt(getServerDolphin().getAt(railId).getAt(ATT_SCALE).getValue().toString());

                ckeckPushingRule(digit, on, scale);

                changeValue(touchedBall.getAt(ATT_ON), !on);
            }
        });

        actionRegistry.register(ValueChangedCommand.class, new CommandHandler<ValueChangedCommand>() {
            public void handleCommand(ValueChangedCommand command, List<Command> response) {

                Attribute attribute = getServerDolphin().getModelStore().findAttributeById(command.getAttributeId());

                if (!ATT_ON.equals(attribute.getPropertyName())) return;
                if (! (Boolean) command.getNewValue()) return;
                if (null == attribute.getQualifier()) return;
                if (! attribute.getQualifier().startsWith(POSITION_PREFIX)) return;

                StringTokenizer tok = new StringTokenizer(attribute.getQualifier().substring(POSITION_PREFIX.length()), "-");
                int scale = Integer.parseInt(tok.nextToken());
                int digit = Integer.parseInt(tok.nextToken());

                if (digit < COL_COUNT) return;

                overflow(scale);

            }
        });
    }

    private void ckeckPushingRule(int digit, boolean on, int scale) {
        if (on) { // switching to off - all higher digits must be set to off as well
            for(int i = digit + 1; i <= COL_COUNT; i++ ) {
                changeValue(ballPm(scale, i).getAt(ATT_ON), Boolean.FALSE);
            }
        } else { // switching to on - all lower digits must be set to on as well
            for(int i = digit -1 ; i >= 1; i-- ) {
                changeValue(ballPm(scale,i).getAt(ATT_ON), Boolean.TRUE);
            }
        }
    }

    // overflow rule - leftmost digit has been switched on
    private void overflow(int scale) {
        // .. find the lowest digit in the next scale that is switched off and switch it on
        for (int foundIndex = 1; foundIndex <= COL_COUNT; foundIndex++) {
            ServerPresentationModel ball = ballPm((scale + 1), foundIndex);
            if (null == ball) return; // there is no upper scale - do nothing
            if (! (Boolean) ball.getAt(ATT_ON).getValue() ) {
                changeValue(ball.getAt(ATT_ON), Boolean.TRUE);
                break;
            }
        }
        // unset all digits in current scale
        for(int i = 1; i <= COL_COUNT; i++ ) {
            changeValue(ballPm(scale, i).getAt(ATT_ON), Boolean.FALSE);
        }
    }

    private  ServerPresentationModel ballPm(int scale, int digit) {
        return getServerDolphin().getAt(AbacusConstants.ballId(scale, digit));
    }

}
