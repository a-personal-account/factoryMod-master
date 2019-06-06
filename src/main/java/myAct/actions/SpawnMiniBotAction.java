package myAct.actions;

import basemod.BaseMod;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import myAct.monsters.*;

import java.util.ArrayList;


public class SpawnMiniBotAction extends AbstractGameAction {
    private static final float MAX_Y = 450.0F;
    private static final float MIN_Y = 25.0F;
    private static final float MIN_X = -600.0F;
    private static final float MAX_X = 350.0F;
    private static final float BORDER = 25.0F * Settings.scale;

    public SpawnMiniBotAction() {
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

        ArrayList<AbstractMonster> cobblestone = new ArrayList<>();
        cobblestone.add(new MiniBotBeamer(0, 0));
        cobblestone.add(new MiniBotDebuff(0, 0));
        cobblestone.add(new MiniBotRepair(0, 0));
        cobblestone.add(new MiniBotVirus(0, 0));
        cobblestone.add(new MiniBotBuilderBuilder(0, 0));

        AbstractMonster m = cobblestone.get(AbstractDungeon.cardRandomRng.random(cobblestone.size() - 1));

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
                if (!(monster.isDeadOrEscaped())) //we don't care about sparks that died, but other enemies could be issues (like repto daggers which have same pos)
                {
                    if (overlap(hb, monster.hb)) {
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

        BaseMod.logger.error("Spawning bot: " + m.drawX + " / " + m.drawY);

        ArrayList<AbstractMonster> bundlea = new ArrayList<>();
        bundlea.add(new MiniBotBeamer(-1000, 1000));
        bundlea.add(new MiniBotDebuff(-1000, 1000));
        bundlea.add(new MiniBotRepair(-1000, 1000));
        bundlea.add(new MiniBotVirus(-1000, 1000));
        bundlea.add(new MiniBotBuilderBuilder(-1000, 1000));

        AbstractMonster q = bundlea.get(AbstractDungeon.cardRandomRng.random(bundlea.size() - 1));
        AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(q, false));
        AbstractDungeon.actionManager.addToBottom(new MoveCreatureAction(q, hb.x, hb.y));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(q, q, new PlatedArmorPower(q, 5), 5));
        q.rollMove();
        q.createIntent();

        this.isDone = true;
    }
}