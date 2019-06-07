//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package myAct.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class SpawnMonsterAutoPositionAction extends AbstractGameAction {
    private boolean used;
    private static final float DURATION = 0.1F;
    private AbstractMonster m;
    private boolean minion;
    private float intendedPosX;

    public SpawnMonsterAutoPositionAction(AbstractMonster m, boolean isMinion, float x) {
        this.used = false;
        this.actionType = ActionType.SPECIAL;
        this.duration = 0.1F;
        this.m = m;
        this.minion = isMinion;
        this.intendedPosX = x;
        if (AbstractDungeon.player.hasRelic("Philosopher's Stone")) {
            m.addPower(new StrengthPower(m, 1));
            AbstractDungeon.onModifyPower();
        }

    }

    public void update() {
        if (!this.used) {
            this.m.init();
            this.m.applyPowers();
            int position = 0;
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (intendedPosX > mo.drawX) {
                    position++;
                }
            }
            AbstractDungeon.getCurrRoom().monsters.addMonster(position, m);


            this.m.showHealthBar();
            if (ModHelper.isModEnabled("Lethality")) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.m, this.m, new StrengthPower(this.m, 3), 3));
            }

            if (ModHelper.isModEnabled("Time Dilation")) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.m, this.m, new SlowPower(this.m, 0)));
            }

            if (this.minion) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.m, this.m, new MinionPower(this.m)));
            }

            this.used = true;
        }

        this.tickDuration();
    }
}
