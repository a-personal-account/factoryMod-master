package myAct.monsters;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import myAct.MyAct;
import myAct.actions.SummonMiniBotAction;
import myAct.intents.IntentEnums;
import myAct.vfx.ColoredLaserEffect;

public class MiniBotBuilderBuilder extends AbstractMonster {
    public static final String ID = MyAct.makeID("MiniBotBuilderBuilder");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 30;
    private static final int HP_MAX = 34;
    private static final int A_7_HP_MIN = 34;
    private static final int A_7_HP_MAX = 45;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 126.0F;
    private static final float HB_H = 172.0F;
    private static final int ATTACK_DAMAGE = 6;
    private int attackdamage;
    private int turnNum = 0;

    public MiniBotBuilderBuilder(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, "superResources/images/monsters/miniBotBuilder.png", x, y);

        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(A_7_HP_MIN, A_7_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }

        this.attackdamage = ATTACK_DAMAGE;
        this.damage.add(new DamageInfo(this, attackdamage));
    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 5), 5));
    }

    public void takeTurn() {

        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ColoredLaserEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.hb.cX, this.hb.cY, Color.BLUE.cpy()), 0.3F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new SummonMiniBotAction());
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (this.turnNum == 0) {
            this.setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
        } else if (this.turnNum == 1) {
            this.setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
        } else if (this.turnNum == 2) {
            this.setMove((byte) 2, IntentEnums.SUMMON_MINI_BOT_INTENT);
        }
        this.turnNum++;
        if (this.turnNum == 3) {
            this.turnNum = 0;
        }
    }

    public void die() {
        super.die();
    }

}
