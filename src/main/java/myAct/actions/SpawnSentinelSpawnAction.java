package myAct.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GenericStrengthUpPower;
import myAct.monsters.SentinelSpawn;

import java.awt.*;
import java.util.ArrayList;


public class SpawnSentinelSpawnAction extends AbstractGameAction {
    private static final float MAX_Y = AbstractDungeon.floorY + 630.0F * Settings.scale;
    private static final float MIN_Y = AbstractDungeon.floorY + 70.0F * Settings.scale;
    private static final float MIN_X = (float) Settings.WIDTH * 0.75F - 600.0F * Settings.scale;
    private static final float MAX_X = (float) Settings.WIDTH * 0.75F + 400.0F * Settings.scale;
    private int hp;

    public SpawnSentinelSpawnAction(int spawnHP) {
        this.actionType = ActionType.SPECIAL;
        this.hp = spawnHP;
    }

    @Override
    public void update() {
        AbstractMonster m = new SentinelSpawn(0, 0, this.hp);

        ArrayList<Point> bigOlPointList = new ArrayList<>();
        for (int x = (int) MIN_X; x <= (int) MAX_X; x += 200 * Settings.scale) {
            int staggerAmt = 0;
            for (int y = (int) MIN_Y; y < (int) MAX_Y; y += 350 * Settings.scale) {
                bigOlPointList.add(new Point(x - staggerAmt, y));
                staggerAmt += 100 * Settings.scale;
            }
        }

        Hitbox hb = m.hb;
        boolean success;
        boolean failsafe = false;
        int i = 0;
        do {
            success = true;
            Point pointytime = bigOlPointList.get(AbstractDungeon.cardRandomRng.random(bigOlPointList.size() - 1));
            hb.move(pointytime.x, pointytime.y);
            for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!q.isDead && !q.isDying && !q.isDeadOrEscaped()) {
                    if (hb.intersects(q.hb)) {
                        success = false;
                    }
                }
            }
            i++;
            if (i > 100) {
                failsafe = true;
            }
        } while (!success && !failsafe);

        if (failsafe) {
            ArrayList<Point> bigOlPointList2 = new ArrayList<>();
            int staggeramt = 0;
            for (int y = (int) MIN_Y; y < (int) MAX_Y; y += 250 * Settings.scale) {
                bigOlPointList2.add(new Point((((int) ((float) Settings.WIDTH * 0.75F - 1100.0F * Settings.scale)) - staggeramt), y));
                staggeramt += 50.0F * Settings.scale;
            }
            do {
                success = true;
                Point pointytime2 = bigOlPointList2.get(AbstractDungeon.cardRandomRng.random(bigOlPointList2.size() - 1));
                hb.move(pointytime2.x, pointytime2.y);
                for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!q.isDead && !q.isDying && !q.isDeadOrEscaped()) {
                        if (hb.intersects(q.hb)) {
                            success = false;
                        }
                    }
                }
            } while (!success);
        }


        m.drawX = hb.x;
        m.drawY = hb.y;

        BaseMod.logger.error("Spawning bot: " + m.drawX + " / " + m.drawY);

        AbstractDungeon.actionManager.addToTop(new SpawnMonsterAutoPositionAction(m, false, m.drawX));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new GenericStrengthUpPower(m, "Improvements", 2), 2));

        this.isDone = true;
    }
}