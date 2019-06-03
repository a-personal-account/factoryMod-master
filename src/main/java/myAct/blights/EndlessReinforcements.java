
package myAct.blights;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import myAct.MyAct;
import myAct.actions.SpawnMiniBotAction;

public class EndlessReinforcements extends Blight {
    public static final String ID = MyAct.makeID("EndlessReinforcements");

    public EndlessReinforcements() {
        super(ID, "EndlessReinforcements");
        this.counter = 0;
    }

    @Override
    public void atTurnStart() {
        this.counter++;
        if (this.counter == 4) {
            AbstractDungeon.actionManager.addToBottom(new SpawnMiniBotAction());
            this.flash();
            this.counter = 0;
        }
    }
}