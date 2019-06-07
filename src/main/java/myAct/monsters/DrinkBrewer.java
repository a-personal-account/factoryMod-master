package myAct.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import myAct.MyAct;
import myAct.powers.SickDrinkPower;

public class DrinkBrewer extends AbstractMonster {
    public static final String ID = MyAct.makeID("DrinkBrewer");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 222;
    private static final int HP_MAX = 222;
    private static final int A_8_HP_MIN = 234;
    private static final int A_8_HP_MAX = 246;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 326.0F;
    private static final float HB_H = 267.0F;
    private static final int ATTACK_DAMAGE = 20;
    private int attackDamage;
    private int turnNum;

    public DrinkBrewer(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, "superResources/images/monsters/DrinkBrewer.png", x, y);

        this.type = EnemyType.ELITE;

        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(A_8_HP_MIN, A_8_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }
        this.attackDamage = ATTACK_DAMAGE;
        this.damage.add(new DamageInfo(this, attackDamage));
    }

    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("FACTORYELITE");
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.POISON));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new SickDrinkPower(AbstractDungeon.player, this, 1), 1));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (turnNum == 0) {
            this.setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
        } else if (turnNum == 1) {
            this.setMove((byte) 2, Intent.STRONG_DEBUFF);
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
