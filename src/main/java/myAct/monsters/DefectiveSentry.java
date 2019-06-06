package myAct.monsters;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Clumsy;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;
import myAct.MyAct;
import myAct.powers.EndOfTurnDamagePower;

public class DefectiveSentry extends AbstractMonster {
    public static final String ID = MyAct.makeID("DefectiveSentry");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 145;
    private static final int HP_MAX = 146;
    private static final int A_8_HP_MIN = 155;
    private static final int A_8_HP_MAX = 156;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 240.0F;
    private static final float HB_H = 594.0F;
    private static final int ATTACK_DEBUFF_DAMAGE = 12;
    private static final int ATTACK_DEFEND_DAMAGE = 20;
    private static final int TRIPLE_ATK_DAMAGE = 8;
    private int attackDebuffDamage;
    private int attackDefendDamage;
    private int tripleAtkDamage;
    private int timertime = 0;
    private boolean usedMegaDebuff = false;

    public DefectiveSentry(float x, float y) {
        super(NAME, "DefectiveSentry", 25, HB_X, HB_Y, HB_W, HB_H, "superResources/images/monsters/DefectSentry.png", x, y);

        this.type = EnemyType.NORMAL;

        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(A_8_HP_MIN, A_8_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }

        if (AbstractDungeon.ascensionLevel >= 3) {
            this.attackDebuffDamage = ATTACK_DEBUFF_DAMAGE;
            this.attackDefendDamage = ATTACK_DEFEND_DAMAGE;
            this.tripleAtkDamage = TRIPLE_ATK_DAMAGE;
        } else {
            this.attackDebuffDamage = ATTACK_DEBUFF_DAMAGE;
            this.attackDefendDamage = ATTACK_DEFEND_DAMAGE;
            this.tripleAtkDamage = TRIPLE_ATK_DAMAGE;
        }

        this.damage.add(new DamageInfo(this, attackDebuffDamage));
        this.damage.add(new DamageInfo(this, attackDefendDamage));
        this.damage.add(new DamageInfo(this, tripleAtkDamage));
    }

    @Override
    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 5), 5));
    }

    @Override
    public void update() {
        super.update();
        timertime += 60 * Gdx.graphics.getDeltaTime();
        if (timertime >= 30) {
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            timertime = 0;
        }
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new EndOfTurnDamagePower(AbstractDungeon.player, this, 32), 32));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.FIRE));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 2));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AttackEffect.SMASH));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 15));
                break;
            case 4:
                for (int i = 0; i < 3; i++) {
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new FireballEffect(this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.5F));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(2), AttackEffect.FIRE));
                }
                break;
            case 5:
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Burn(), 2, true, true));
                break;
            case 6:
                AbstractDungeon.actionManager.addToBottom(new AddCardToDeckAction(new Clumsy()));
                usedMegaDebuff = true;
                break;
        }

    }

    protected void getMove(int num) {
        int whatwedoin;
        if (!usedMegaDebuff) {
            whatwedoin = AbstractDungeon.cardRandomRng.random(5);
        } else {
            whatwedoin = AbstractDungeon.cardRandomRng.random(4);
        }
        if (whatwedoin == 0) {
            this.setMove((byte) 1, Intent.STRONG_DEBUFF);
        } else if (whatwedoin == 1) {
            this.setMove((byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
        } else if (whatwedoin == 2) {
            this.setMove((byte) 3, Intent.ATTACK_DEFEND, this.damage.get(1).base);
        } else if (whatwedoin == 3) {
            this.setMove((byte) 4, Intent.ATTACK, this.damage.get(2).base, 3, true);
        } else if (whatwedoin == 4) {
            this.setMove((byte) 5, Intent.DEBUFF);
        } else if (whatwedoin == 5) {
            this.setMove((byte) 6, Intent.STRONG_DEBUFF);
        }

        this.createIntent();
    }

    public void die() {
        super.die();
    }

}
