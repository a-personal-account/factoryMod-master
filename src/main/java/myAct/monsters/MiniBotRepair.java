package myAct.monsters;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import myAct.MyAct;
import myAct.vfx.ColoredLaserEffect;

public class MiniBotRepair extends AbstractMonster {
    public static final String ID = MyAct.makeID("MiniBotRepair");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 28;
    private static final int HP_MAX = 32;
    private static final int A_7_HP_MIN = 34;
    private static final int A_7_HP_MAX = 45;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 126.0F;
    private static final float HB_H = 172.0F;
    private int turnNum = 0;

    public MiniBotRepair(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, "superResources/images/monsters/miniBotRepair.png", x, y);

        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(A_7_HP_MIN, A_7_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }
        this.damage.add(new DamageInfo(this, 8));
        this.turnNum = AbstractDungeon.cardRandomRng.random(1);
    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 5), 5));
    }

    public void takeTurn() {

        switch (this.nextMove) {
            case 1:
                AbstractMonster megaMonster = null;
                int lowestHP = 999;
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!m.isDead && !m.isDying && m.currentHealth < lowestHP) {
                        lowestHP = m.currentHealth;
                        megaMonster = m;
                    }
                }
                AbstractDungeon.actionManager.addToBottom(new HealAction(megaMonster, this, AbstractDungeon.cardRandomRng.random(8, 10)));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ColoredLaserEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.hb.cX, this.hb.cY, Color.PINK.cpy()), 0.3F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (turnNum == 0) {
            this.setMove((byte) 1, Intent.BUFF);
        } else if (turnNum == 1) {
            this.setMove((byte) 2, Intent.ATTACK, this.damage.get(0).base);
        }
    }

    public void die() {
        super.die();
    }

}
