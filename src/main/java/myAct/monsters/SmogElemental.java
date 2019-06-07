package myAct.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import myAct.MyAct;
import myAct.powers.CoughCoughPower;

public class SmogElemental extends AbstractMonster {
    public static final String ID = MyAct.makeID("SmogElemental");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 195;
    private static final int HP_MAX = 199;
    private static final int A_8_HP_MIN = 200;
    private static final int A_8_HP_MAX = 210;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 324.0F;
    private static final float HB_H = 341.0F;
    private static final int ATTACK_WEAK_DAMAGE = 15;
    private static final int ATTACK_DAMAGE = 22;
    private int attackWeakDamage;
    private int attackDamage;
    private int turnNum;

    public SmogElemental(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, "superResources/images/monsters/SmogElemental.png", x, y);

        this.type = EnemyType.ELITE;

        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(A_8_HP_MIN, A_8_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }

        if (AbstractDungeon.ascensionLevel >= 3) {
            this.attackDamage = ATTACK_DAMAGE;
            this.attackWeakDamage = ATTACK_WEAK_DAMAGE;
        } else {
            this.attackDamage = ATTACK_DAMAGE;
            this.attackWeakDamage = ATTACK_WEAK_DAMAGE;
        }

        this.damage.add(new DamageInfo(this, attackDamage));
        this.damage.add(new DamageInfo(this, attackWeakDamage));
    }

    @Override
    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("FACTORYELITE");
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new CoughCoughPower(AbstractDungeon.player, this, 1), 1));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.SLASH_DIAGONAL));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AttackEffect.SMASH));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (turnNum == 0) {
            this.setMove((byte) 1, Intent.STRONG_DEBUFF);
        } else if (turnNum == 1) {
            this.setMove((byte) 2, Intent.ATTACK, this.damage.get(0).base);
        } else if (turnNum == 2) {
            this.setMove((byte) 3, Intent.ATTACK_DEBUFF, this.damage.get(1).base);
        } else if (turnNum == 3) {
            this.setMove((byte) 2, Intent.ATTACK, this.damage.get(0).base);
        }
        turnNum++;
        if (turnNum == 4) {
            turnNum = 0;
        }
    }

    public void die() {
        super.die();
    }

}
