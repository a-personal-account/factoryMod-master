package myAct.monsters;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import myAct.MyAct;
import myAct.actions.SpawnMiniBotAction;
import myAct.powers.EndOfTurnDamagePower;

public class BigBot extends AbstractPlaceholderMonster {
    public static final String ID = MyAct.makeID("BigBot");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 170;
    private static final int HP_MAX = 180;
    private static final int A_8_HP_MIN = 180;
    private static final int A_8_HP_MAX = 190;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 150.0F;
    private int turnNum;

    public BigBot(float x, float y) {
        super(NAME, "BigBot", 213, HB_X, HB_Y, HB_W, HB_H, "superResources/images/monsters/hex.png", x, y);

        this.type = EnemyType.NORMAL;

        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(A_8_HP_MIN, A_8_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }
    }

    @Override
    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 2), 2));
    }

    public void takeTurn() {
        AbstractDungeon.actionManager.addToBottom(new SpawnMiniBotAction());
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new EndOfTurnDamagePower(AbstractDungeon.player, this, AbstractDungeon.cardRandomRng.random(8, 12))));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 5)));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (turnNum == 0) {
            this.setMove((byte) 1, Intent.DEBUFF);
        } else if (turnNum == 1) {
            this.setMove((byte) 2, Intent.BUFF);
        }
        turnNum++;
        if (turnNum == 2) {
            turnNum = 0;
        }
    }

    public void die() {
        super.die();

    }

}
