package myAct.blights;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import myAct.MyAct;

public class PainfulGloom extends Blight {
    public static final String ID = MyAct.makeID("PainfulGloom");

    public PainfulGloom() {
        super(ID, "PainfulGloom");
        this.counter = 0;
    }

    @Override
    public void onPlayerEndTurn() {
        this.counter++;
        if (this.counter == 3) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(CardLibrary.getCurse().makeCopy(), 1, true, true));
            this.flash();
            this.counter = 0;
        }
    }
}