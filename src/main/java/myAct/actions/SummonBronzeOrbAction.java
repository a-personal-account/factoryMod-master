package myAct.actions;

import basemod.BaseMod;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.BronzeOrb;
import myAct.monsters.ToyOrb;


public class SummonBronzeOrbAction extends AbstractGameAction {
    private static final float MAX_Y = 250.0F;
    private static final float MIN_Y = 100.0F;
    private static final float MIN_X = -200.0F;
    private static final float MAX_X = 200.0F;
    private static final float BORDER = 100.0F * Settings.scale;

    public SummonBronzeOrbAction() {
        this.actionType = ActionType.SPECIAL;
    }

    private static boolean overlap(Hitbox a, Hitbox b) {
        if (a.x > b.x + (b.width + BORDER) || b.x > a.x + (a.width + BORDER))
            return false;

        return !(a.y > b.y + (b.height + BORDER) || b.y > a.y + (a.height + BORDER));
    }

    @Override
    public void update() {
        //first, find a good position

        ToyOrb m = new ToyOrb(0, 0, 0);


        Hitbox hb = m.hb;
        hb.move(m.drawX + MathUtils.random(MIN_X, MAX_X), m.drawY + MathUtils.random(MIN_Y, MAX_Y));


        float startX = hb.x;
        float startY = hb.y;
        float adjustDistance = 0;
        float adjustAngle = 0;
        boolean success = false;

        //check if this is a fine position.
        do {
            success = true;
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!(monster.isDeadOrEscaped() && monster.id.equals(m.id))) //we don't care about sparks that died, but other enemies could be issues (like repto daggers which have same pos)
                {
                    if (hb.intersects(monster.hb)) {
                        success = false;

                        adjustAngle = (adjustAngle + 0.1f) % (MathUtils.PI2);
                        adjustDistance += 10.0f;


                        hb.x = startX + MathUtils.cos(adjustAngle) * adjustDistance;
                        hb.y = startY + MathUtils.sin(adjustAngle) * adjustDistance;

                        break;
                    }
                }
            }
        } while (!success);

        m.drawX = hb.x;
        m.drawY = hb.y;

        //m.hb_x = m.hb.cX - (m.drawX + m.animX);
        //m.hb_y = m.hb.cY - (m.drawY + m.animY);
        //m.healthHb.move(m.hb.cX, m.hb.cY - m.hb_h / 2.0F - m.healthHb.height / 2.0F);

        BaseMod.logger.error("Spawning orb: " + m.drawX + " / " + m.drawY);

        AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(m, true));

        this.isDone = true;
    }
}