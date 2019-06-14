package myAct.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GenericStrengthUpPower;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import myAct.MyAct;

public class SentinelSpawn extends AbstractMonster {
    public static final String ID = MyAct.makeID("SentinelSpawn");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 194.0F;
    private static final float HB_H = 137.0F;
    private int turnNum;

    public SentinelSpawn(float x, float y, int health) {
        super(NAME, ID, health, HB_X, HB_Y, HB_W, HB_H, "superResources/images/monsters/SentinelSpawn.png", x, y);

        this.setHp(health);
        this.damage.add(new DamageInfo(this, 10));
        this.damage.add(new DamageInfo(this, 5));
    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new GenericStrengthUpPower(this, "Improvements", 2)));
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!m.isDead && !m.isDying) {
                        if (m instanceof DecayingSentinel) {
                            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, this, 10));
                        }
                    }
                }
                break;
            case 3:
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!m.isDead && !m.isDying) {
                        if (m instanceof DecayingSentinel) {
                            AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(this.hb.cX, this.hb.cY), 0.1F));
                            AbstractDungeon.actionManager.addToBottom(new HealAction(m, this, this.currentHealth));
                        }
                    }
                }
                AbstractDungeon.actionManager.addToBottom(new SuicideAction(this));
                break;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (turnNum == 0) {
            this.setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
        } else if (turnNum == 1) {
            this.setMove((byte) 2, Intent.ATTACK_DEFEND, this.damage.get(1).base);
        } else if (turnNum == 2) {
            this.setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
        } else if (turnNum == 3) {
            this.setMove((byte) 3, Intent.BUFF);
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
