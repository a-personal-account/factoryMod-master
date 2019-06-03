package myAct.blights;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import myAct.MyAct;
import myAct.vfx.RelicYoinkEffect;

import java.util.ArrayList;

public class OppressiveHeat extends Blight {
    public static final String ID = MyAct.makeID("OppressiveHeat");

    public OppressiveHeat() {
        super(ID, "OppressiveHeat");
        this.counter = 0;
    }

    @Override
    public void atBattleStart() {
        this.counter++;
        if (this.counter == 2) {
            ArrayList<AbstractRelic> bigOlRelicList = new ArrayList<>();
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r.tier == AbstractRelic.RelicTier.COMMON || r.tier == AbstractRelic.RelicTier.UNCOMMON || r.tier == AbstractRelic.RelicTier.RARE) {
                    bigOlRelicList.add(r);
                }
            }
            if (bigOlRelicList.size() > 0) {
                AbstractRelic lossRelic = bigOlRelicList.get(AbstractDungeon.cardRandomRng.random(bigOlRelicList.size() - 1));
                lossRelic.flash();
                AbstractDungeon.player.loseRelic(lossRelic.relicId);
                AbstractDungeon.effectList.add(new RelicYoinkEffect(lossRelic, this.currentX, this.currentY));
            }
            this.flash();
            this.counter = 0;
        }
    }
}