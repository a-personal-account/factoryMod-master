package myAct.monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import myAct.MyAct;

import java.util.ArrayList;

public class Experiment01 extends AbstractMonster {
    public static final String ID = MyAct.makeID("Experiment01");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 666;
    private static final int HP_MAX = 666;
    private static final int A_9_HP_MIN = 678;
    private static final int A_9_HP_MAX = 678;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 630.0F;
    private static final float HB_H = 618.0F;
    private static final int ATTACK_LIFESTEAL_DAMAGE = 20;
    private static final int ATTACK_DEBUFF_DAMAGE = 15;
    private boolean firstTurn = true;
    private int attackLifestealDamage;
    private int attackDebuffDamage;

    public Experiment01(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, "superResources/images/monsters/Experiment01.png", x, y);

        this.type = EnemyType.BOSS;

        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(A_9_HP_MIN, A_9_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }

        if (AbstractDungeon.ascensionLevel >= 3) {
            attackLifestealDamage = ATTACK_LIFESTEAL_DAMAGE;
            attackDebuffDamage = ATTACK_DEBUFF_DAMAGE;
        } else {
            attackLifestealDamage = ATTACK_LIFESTEAL_DAMAGE;
            attackDebuffDamage = ATTACK_DEBUFF_DAMAGE;
        }

        this.damage.add(new DamageInfo(this, attackLifestealDamage));
        this.damage.add(new DamageInfo(this, attackDebuffDamage));
    }

    public void usePreBattleAction() {
        AbstractDungeon.getCurrRoom().rewardAllowed = false;
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_FACTORY");
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 5), 5));
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                ArrayList<AttackEffect> possEffects = new ArrayList<>();
                possEffects.add(AttackEffect.FIRE);
                possEffects.add(AttackEffect.NONE);
                possEffects.add(AttackEffect.BLUNT_HEAVY);
                possEffects.add(AttackEffect.BLUNT_LIGHT);
                possEffects.add(AttackEffect.POISON);
                possEffects.add(AttackEffect.SHIELD);
                possEffects.add(AttackEffect.SLASH_DIAGONAL);
                possEffects.add(AttackEffect.SLASH_HEAVY);
                possEffects.add(AttackEffect.SLASH_HORIZONTAL);
                possEffects.add(AttackEffect.SLASH_VERTICAL);
                possEffects.add(AttackEffect.SMASH);
                AttackEffect effectToUse = possEffects.get(AbstractDungeon.miscRng.random(possEffects.size() - 1));
                if (effectToUse == AttackEffect.NONE) {
                    int whichNow = AbstractDungeon.miscRng.random(3);
                    if (whichNow == 0) {
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-25.0F, 25.0F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-25.0F, 25.0F) * Settings.scale, Color.LIGHT_GRAY.cpy()), 0.0F));
                    } else if (whichNow == 1) {
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(AbstractDungeon.player.hb.cX - 60.0F * Settings.scale, AbstractDungeon.player.hb.cY, false), 0.0F));
                    } else if (whichNow == 2) {
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new WeightyImpactEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
                    } else if (whichNow == 3) {
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), AbstractDungeon.miscRng.random(0.15F, 0.2F)));
                    }
                }
                AbstractDungeon.actionManager.addToBottom(new VampireDamageAction(AbstractDungeon.player, this.damage.get(0), effectToUse));
                break;
            case 2:
                ArrayList<AttackEffect> possEffects2 = new ArrayList<>();
                possEffects2.add(AttackEffect.FIRE);
                possEffects2.add(AttackEffect.NONE);
                possEffects2.add(AttackEffect.BLUNT_HEAVY);
                possEffects2.add(AttackEffect.BLUNT_LIGHT);
                possEffects2.add(AttackEffect.POISON);
                possEffects2.add(AttackEffect.SHIELD);
                possEffects2.add(AttackEffect.SLASH_DIAGONAL);
                possEffects2.add(AttackEffect.SLASH_HEAVY);
                possEffects2.add(AttackEffect.SLASH_HORIZONTAL);
                possEffects2.add(AttackEffect.SLASH_VERTICAL);
                possEffects2.add(AttackEffect.SMASH);
                AttackEffect effectToUse2 = possEffects2.get(AbstractDungeon.miscRng.random(possEffects2.size() - 1));
                if (effectToUse2 == AttackEffect.NONE) {
                    int whichNow = AbstractDungeon.miscRng.random(3);
                    if (whichNow == 0) {
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-25.0F, 25.0F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-25.0F, 25.0F) * Settings.scale, Color.LIGHT_GRAY.cpy()), 0.0F));
                    } else if (whichNow == 1) {
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(AbstractDungeon.player.hb.cX - 60.0F * Settings.scale, AbstractDungeon.player.hb.cY, false), 0.0F));
                    } else if (whichNow == 2) {
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new WeightyImpactEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
                    } else if (whichNow == 3) {
                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ViceCrushEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), AbstractDungeon.miscRng.random(0.15F, 0.2F)));
                    }
                }
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), effectToUse2));
                for (int i = 0; i < 2; i++) {
                    int letsSee = AbstractDungeon.monsterRng.random(4);
                    if (letsSee == 0) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
                    } else if (letsSee == 1) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 2));
                    } else if (letsSee == 2) {
                        ArrayList<String> superList = new ArrayList<>();
                        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
                            if (AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount > 0) {
                                superList.add("StrDown");
                            }
                        }
                        if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)) {
                            if (AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount > 0) {
                                superList.add("DexDown");
                            }
                        }
                        if (AbstractDungeon.player.hasPower(FocusPower.POWER_ID)) {
                            if (AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount > 0) {
                                superList.add("FocDown");
                            }
                        }
                        if (superList.size() > 0) {
                            String wahoo = superList.get(AbstractDungeon.cardRandomRng.random(superList.size() - 1));
                            if (wahoo.equals("StrDown")) {
                                if (AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount == 1) {
                                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new StrengthPower(AbstractDungeon.player, -1), -1));
                                } else {
                                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new StrengthPower(AbstractDungeon.player, -2), -2));
                                }
                            } else if (wahoo.equals("DexDown")) {
                                if (AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount == 1) {
                                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new DexterityPower(AbstractDungeon.player, -1), -1));
                                } else {
                                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new DexterityPower(AbstractDungeon.player, -2), -2));
                                }
                            } else if (wahoo.equals("FocDown")) {
                                if (AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount == 1) {
                                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FocusPower(AbstractDungeon.player, -1), -1));
                                } else {
                                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FocusPower(AbstractDungeon.player, -2), -2));
                                }
                            }
                        } else {
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new ConstrictedPower(AbstractDungeon.player, this, 2), 2));
                        }
                    } else if (letsSee == 3) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 2));
                    } else if (letsSee == 4) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new PoisonPower(AbstractDungeon.player, this, 2), 2));
                    }
                }
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PainfulStabsPower(this), 1));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new AngryPower(this, 1), 1));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (this.currentHealth <= 400 && !this.hasPower(PainfulStabsPower.POWER_ID)) {
            this.setMove((byte) 3, Intent.BUFF);
        } else if (this.currentHealth <= 200 && !this.hasPower(AngryPower.POWER_ID)) {
            this.setMove((byte) 4, Intent.BUFF);
        } else if (this.lastMove((byte) 2)) {
            this.setMove((byte) 1, Intent.ATTACK_BUFF, this.damage.get(0).base);
        } else {
            this.setMove((byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(1).base);
        }
    }

    public void die() {
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            this.useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            super.die();
            this.onBossVictoryLogic();
            this.onFinalBossVictoryLogic();
        }
    }

}
