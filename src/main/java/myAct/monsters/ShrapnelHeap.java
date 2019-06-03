package myAct.monsters;

import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import myAct.MyAct;

public class ShrapnelHeap extends AbstractPlaceholderMonster {
    public static final String ID = MyAct.makeID("ShrapnelHeap");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 250;
    private static final int HP_MAX = 250;
    private static final int A_9_HP_MIN = 300;
    private static final int A_9_HP_MAX = 300;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 300.0F;
    private static final float HB_H = 300.0F;

    public ShrapnelHeap(float x, float y) {
        super(NAME, "ShrapnelHeap", 25, HB_X, HB_Y, HB_W, HB_H, "superResources/images/monsters/bigHex.png", x, y);

        if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(A_9_HP_MIN, A_9_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }

    }

    public void takeTurn() {
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        this.setMove((byte) 1, Intent.NONE);
    }

    public void die() {
        super.die();
    }

}
