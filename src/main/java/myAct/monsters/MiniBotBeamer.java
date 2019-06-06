package myAct.monsters;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import myAct.MyAct;
import myAct.powers.EndOfTurnDamagePower;

public class MiniBotBeamer extends AbstractMonster {
    public static final String ID = MyAct.makeID("MiniBotBeamer");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 30;
    private static final int HP_MAX = 40;
    private static final int A_7_HP_MIN = 34;
    private static final int A_7_HP_MAX = 45;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 137.0F;
    private static final float HB_H = 155.0F;

    public MiniBotBeamer(float x, float y) {
        super(NAME, "MiniBotBeamer", 25, HB_X, HB_Y, HB_W, HB_H, "superResources/images/monsters/miniBotBeamer.png", x, y);

        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(A_7_HP_MIN, A_7_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }

    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 5), 5));
    }

    public void takeTurn() {

        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new EndOfTurnDamagePower(AbstractDungeon.player, this, 10), 10));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        this.setMove((byte) 1, Intent.DEBUFF);
    }

    public void die() {
        super.die();
    }

}
