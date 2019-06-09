//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package myAct.monsters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import myAct.MyAct;
import myAct.util.TextureLoader;

public class Guardian2 extends AbstractMonster {
    public static final String ID = MyAct.makeID("Guardian2");
    public static final int HP = 480;
    public static final int A_2_HP = 500;
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int DMG_THRESHOLD = 35;
    private static final int A_2_DMG_THRESHOLD = 40;
    private static final int A_19_DMG_THRESHOLD = 45;
    private static final int FIERCE_BASH_DMG = 500;
    private static final int ROLL_DMG = 10;
    private static final int WHIRLWIND_DMG = 7;
    private static final int TWINSLAM_DMG = 10;
    private static final byte CLOSE_UP = 1;
    private static final byte FIERCE_BASH = 2;
    private static final byte ROLL_ATTACK = 3;
    private static final byte TWIN_SLAM = 4;
    private static final byte WHIRLWIND = 5;
    private static final byte CHARGE_UP = 6;
    private static final byte VENT_STEAM = 7;
    private int dmgThreshold;
    private int dmgThresholdIncrease = 10;
    private int dmgTaken;
    private int fierceBashDamage;
    private int whirlwindDamage;
    private int twinSlamDamage;
    private int rollDamage;
    private boolean isOpen = true;
    private boolean closeUpTriggered = false;

    public Guardian2(float x, float y) {
        super(NAME, ID, HP, 0.0F, 0.0F, 581.0F, 481.0F, "superResources/images/monsters/Guardian2_attack.png", x, y);
        this.type = EnemyType.BOSS;

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.setHp(A_2_HP);
            this.dmgThreshold = A_19_DMG_THRESHOLD;
        } else if (AbstractDungeon.ascensionLevel >= 9) {
            this.setHp(A_2_HP);
            this.dmgThreshold = A_2_DMG_THRESHOLD;
        } else {
            this.setHp(HP);
            this.dmgThreshold = DMG_THRESHOLD;
        }

        if (AbstractDungeon.ascensionLevel >= 4) {
            this.fierceBashDamage = FIERCE_BASH_DMG;
            this.rollDamage = ROLL_DMG;
            this.whirlwindDamage = WHIRLWIND_DMG;
            this.twinSlamDamage = TWINSLAM_DMG;
        } else {
            this.fierceBashDamage = FIERCE_BASH_DMG;
            this.rollDamage = ROLL_DMG;
            this.whirlwindDamage = WHIRLWIND_DMG;
            this.twinSlamDamage = TWINSLAM_DMG;
        }

        this.damage.add(new DamageInfo(this, this.fierceBashDamage));
        this.damage.add(new DamageInfo(this, this.rollDamage));
        this.damage.add(new DamageInfo(this, this.whirlwindDamage));
        this.damage.add(new DamageInfo(this, this.twinSlamDamage));
    }

    public void usePreBattleAction() {
        AbstractDungeon.getCurrRoom().rewardAllowed = false;
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_FACTORY");
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new BarricadePower(this), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ModeShiftPower(this, this.dmgThreshold)));
        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Reset Threshold"));
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                this.useCloseUp();
                break;
            case 2:
                this.useFierceBash();
                break;
            case 3:
                this.useRollAttack();
                break;
            case 4:
                this.img = TextureLoader.getTexture("superResources/images/monsters/Guardian2_attack.png");
                this.updateHitbox(0.0F, 0.0F, 581.0F, 481.0F);
                this.useTwinSmash();
                break;
            case 5:
                this.useWhirlwind();
                break;
            case 6:
                this.useChargeUp();
                break;
            case 7:
                this.useVentSteam();
                break;
            case 8:
                this.useBlockBlock();
                break;
        }

    }

    private void useBlockBlock() {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 10));
        this.setMove((byte) 4, Intent.ATTACK_BUFF, this.twinSlamDamage, 2, true);
    }

    private void useFierceBash() {
        AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.BLUNT_HEAVY));
        this.setMove((byte) 5, Intent.ATTACK, this.whirlwindDamage, 4, true);
    }

    private void useVentSteam() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 3, true), 3));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 3, true), 3));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 3, true), 3));
        this.setMove((byte) 6, Intent.DEFEND);
    }

    private void useCloseUp() {
        if (AbstractDungeon.ascensionLevel >= 19) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SharpHidePower(this, 4), 4));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SharpHidePower(this, 3), 3));
        }

        this.setMove((byte) 3, Intent.ATTACK, this.damage.get(1).base);
    }

    private void useTwinSmash() {
        AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Offensive Mode"));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(3), AttackEffect.SLASH_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(3), AttackEffect.SLASH_HEAVY));
        this.setMove((byte) 5, Intent.ATTACK, this.whirlwindDamage, 4, true);
    }

    private void useRollAttack() {
        AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AttackEffect.BLUNT_HEAVY));
        this.setMove((byte) 8, Intent.BUFF);
    }

    private void useWhirlwind() {
        AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_WHIRLWIND"));

        for (int i = 0; i < 4; ++i) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new CleaveEffect(true), 0.15F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(2), AttackEffect.NONE, true));
        }

        this.setMove((byte) 7, Intent.STRONG_DEBUFF);
    }

    private void useChargeUp() {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 15));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_GUARDIAN_DESTROY"));
        this.setMove((byte) 2, Intent.ATTACK, this.damage.get(0).base);
    }

    protected void getMove(int num) {
        if (this.isOpen) {
            this.setMove((byte) 5, Intent.ATTACK, this.whirlwindDamage, 4, true);
        } else {
            this.setMove((byte) 3, Intent.ATTACK, this.damage.get(1).base);
        }

    }

    public void changeState(String stateName) {
        byte var3 = -1;
        switch (stateName.hashCode()) {
            case -927957434:
                if (stateName.equals("Offensive Mode")) {
                    var3 = 1;
                }
                break;
            case 631623152:
                if (stateName.equals("Defensive Mode")) {
                    var3 = 0;
                }
                break;
            case 786294426:
                if (stateName.equals("Reset Threshold")) {
                    var3 = 2;
                }
        }

        switch (var3) {
            case 0:
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this, this, "Mode Shift"));
                CardCrawlGame.sound.play("GUARDIAN_ROLL_UP");
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 20));
                this.dmgThreshold += this.dmgThresholdIncrease;
                this.setMove((byte) 1, Intent.BUFF);
                this.createIntent();
                this.isOpen = false;
                // this.updateHitbox(0.0F, 95.0F, 440.0F, 250.0F);
                this.healthBarUpdatedEvent();
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ModeShiftPower(this, this.dmgThreshold)));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Reset Threshold"));

                this.isOpen = true;
                this.closeUpTriggered = false;
                // this.updateHitbox(0.0F, 0.0F, 300.0F, 300.0F);
                this.healthBarUpdatedEvent();
                break;
            case 2:
                this.dmgTaken = 0;
        }

    }

    public void damage(DamageInfo info) {
        int tmpHealth = this.currentHealth;
        super.damage(info);
        if (this.isOpen && !this.closeUpTriggered && tmpHealth > this.currentHealth && !this.isDying) {
            this.dmgTaken += tmpHealth - this.currentHealth;
            if (this.getPower("Mode Shift") != null) {
                AbstractPower var10000 = this.getPower("Mode Shift");
                var10000.amount -= tmpHealth - this.currentHealth;
                this.getPower("Mode Shift").updateDescription();
            }

            if (this.dmgTaken >= this.dmgThreshold) {
                this.dmgTaken = 0;
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new IntenseZoomEffect(this.hb.cX, this.hb.cY, false), 0.05F, true));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Defensive Mode"));
                this.img = TextureLoader.getTexture("superResources/images/monsters/Guardian2_defend.png");
                this.updateHitbox(0.0F, 0.0F, 621.0F, 544.0F);
                this.closeUpTriggered = true;
            }
        }

    }

    public void render(SpriteBatch sb) {
        super.render(sb);
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
