package myAct.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import myAct.MyAct;
import myAct.actions.SpawnMiniBotAction;

public class ExpPersonnel extends AbstractPlaceholderMonster {
    public static final String ID = MyAct.makeID("ExpPersonnel");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 87;
    private static final int HP_MAX = 99;
    private static final int A_7_HP_MIN = 100;
    private static final int A_7_HP_MAX = 115;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 150.0F;
    private static final int REGULAR_OLD_ATTACK_DMG = 8;
    private int regularOldAttackDmg;
    private int turnNum;

    private boolean right;

    public ExpPersonnel(float x, float y, boolean isRight) {
        super(NAME, "ExpPersonnel", 25, HB_X, HB_Y, HB_W, HB_H, "superResources/images/monsters/hex.png", x, y);

        this.type = EnemyType.NORMAL;

        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(A_7_HP_MIN, A_7_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }
        this.right = isRight;
        if (isRight) {
            this.turnNum++;
        }
        this.regularOldAttackDmg = REGULAR_OLD_ATTACK_DMG;

        this.damage.add(new DamageInfo(this, regularOldAttackDmg));
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                break;
            case 2:
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!m.isDying && !m.isDead && m != this && m instanceof ExpPersonnel) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, 2), 2));
                    }
                }
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, 20, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 3, true), 3));
                break;
            case 5:
                AbstractDungeon.actionManager.addToBottom(new SpawnMiniBotAction());
                break;
            case 6:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 3, true), 3));
                break;
            case 7:
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!m.isDying && !m.isDead && m != this && m instanceof ExpPersonnel) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new ArtifactPower(m, 1), 1));
                    }
                }
                break;
            case 8:
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!m.isDying && !m.isDead && m != this && m instanceof ExpPersonnel) {
                        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, this, 12));
                    }
                }
                break;
            case 9:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, new DamageInfo(this, 10, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.1F));
                break;
            case 10:
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!m.isDying && !m.isDead && m != this && m instanceof ExpPersonnel) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new ThornsPower(m, 3), 3));
                    }
                }
                break;
            case 11:
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!m.isDying && !m.isDead && m != this && m instanceof ExpPersonnel) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new RegenPower(m, 5), 5));
                    }
                }
                break;
            case 12:
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!m.isDying && !m.isDead && m != this && m instanceof ExpPersonnel) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new IntangiblePower(m, 1), 1));
                    }
                }
                break;
            case 13:
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!m.isDying && !m.isDead && m != this && m instanceof ExpPersonnel) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new PlatedArmorPower(m, 4), 4));
                    }
                }
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (this.turnNum == 0) {
            if (AbstractDungeon.cardRandomRng.random(100) > 34) {
                this.setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base, 2, true);
            } else {
                this.setMove((byte) 5, Intent.UNKNOWN);
            }
        } else if (this.turnNum == 1) {
            int whatchaGonnaDo = AbstractDungeon.cardRandomRng.random(11);
            if (whatchaGonnaDo == 0) {
                this.setMove((byte) 2, Intent.BUFF);
            } else if (whatchaGonnaDo == 1) {
                this.setMove((byte) 3, Intent.ATTACK, 20);
            } else if (whatchaGonnaDo == 2) {
                this.setMove((byte) 4, Intent.STRONG_DEBUFF);
            } else if (whatchaGonnaDo == 3) {
                this.setMove((byte) 6, Intent.STRONG_DEBUFF);
            } else if (whatchaGonnaDo == 4) {
                this.setMove((byte) 7, Intent.BUFF);
            } else if (whatchaGonnaDo == 5) {
                this.setMove((byte) 8, Intent.DEFEND);
            } else if (whatchaGonnaDo == 6) {
                this.setMove((byte) 9, Intent.ATTACK, 10);
            } else if (whatchaGonnaDo == 7) {
                this.setMove((byte) 10, Intent.BUFF);
            } else if (whatchaGonnaDo == 8) {
                this.setMove((byte) 11, Intent.BUFF);
            } else if (whatchaGonnaDo == 9) {
                this.setMove((byte) 12, Intent.BUFF);
            } else if (whatchaGonnaDo == 10) {
                this.setMove((byte) 13, Intent.BUFF);
            }
        }
        this.turnNum++;
        if (this.turnNum == 2) {
            this.turnNum = 0;
        }
    }

    public void die() {
        super.die();
    }

}