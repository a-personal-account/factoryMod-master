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
import myAct.vfx.ColoredLaserEffect;

public class MiniBotBeamer extends AbstractMonster {
    public static final String ID = MyAct.makeID("MiniBotBeamer");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 34;
    private static final int HP_MAX = 38;
    private static final int A_7_HP_MIN = 34;
    private static final int A_7_HP_MAX = 45;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 126.0F;
    private static final float HB_H = 172.0F;

    public MiniBotBeamer(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, "superResources/images/monsters/miniBotBeamer.png", x, y);

        if (AbstractDungeon.ascensionLevel >= 7) {
            this.setHp(A_7_HP_MIN, A_7_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }
        this.damage.add(new DamageInfo(this, 10));
    }

    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 5), 5));
    }

    public void takeTurn() {

        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ColoredLaserEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, this.hb.cX, this.hb.cY, Color.RED.cpy()), 0.3F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE));
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        this.setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
    }

    public void die() {
        super.die();
    }

}
