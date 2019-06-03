package myAct.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import myAct.MyAct;
import myAct.powers.GuardingPower;

public class ShrapnelTosser extends AbstractPlaceholderMonster {
    public static final String ID = MyAct.makeID("ShrapnelTosser");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 130;
    private static final int HP_MAX = 140;
    private static final int A_7_HP_MIN = 150;
    private static final int A_7_HP_MAX = 160;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 150.0F;
    private int turnNum;

    public ShrapnelTosser(float x, float y) {
        super(NAME, "ShrapnelTosser", 25, HB_X, HB_Y, HB_W, HB_H, "superResources/images/monsters/hex.png", x, y);

        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(A_7_HP_MIN, A_7_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }
        this.damage.add(new DamageInfo(this, 25));
    }

    public void takeTurn() {

        switch (this.nextMove) {
            case 1:
                if (this.hasPower(GuardingPower.POWER_ID)) {
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this, this, GuardingPower.POWER_ID));
                }
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!m.isDying && !m.isDead) {
                        if (m instanceof ShrapnelHeap) {
                            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                        }
                    }
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new GuardingPower(this, this), 1));
                break;
            case 3:
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!m.isDying && !m.isDead) {
                        if (m instanceof ShrapnelHeap) {
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new ThornsPower(m, 2), 2));
                        }
                    }
                }
                break;
            case 4:
                int lifeStealHealth = AbstractDungeon.cardRandomRng.random(15, 20);
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!m.isDying && !m.isDead) {
                        if (m instanceof ShrapnelHeap) {
                            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(m, this, lifeStealHealth));
                        }
                    }
                }
                AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, lifeStealHealth));
                break;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (this.turnNum == 0) {
            this.setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
        } else if (this.turnNum == 1) {
            this.setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
        } else if (this.turnNum == 2) {
            this.setMove((byte) 2, Intent.BUFF);
        } else if (this.turnNum == 3) {
            this.setMove((byte) 3, Intent.BUFF);
        } else if (this.turnNum == 4) {
            this.setMove((byte) 4, Intent.BUFF);
        } else if (this.turnNum == 5) {
            this.setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
        }
        turnNum++;
        if (turnNum == 6) {
            turnNum = 0;
        }
    }

    public void die() {
        super.die();
    }

}
