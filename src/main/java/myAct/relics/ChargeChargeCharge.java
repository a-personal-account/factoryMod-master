package myAct.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import myAct.MyAct;
import myAct.util.TextureLoader;

public class ChargeChargeCharge extends CustomRelic {

    public static final String ID = MyAct.makeID("ChargeChargeCharge");

    private static final Texture IMG = TextureLoader.getTexture("superResources/images/relics/angerman.png");
    private static final Texture OUTLINE = TextureLoader.getTexture("superResources/images/relics/outline/angerman.png");

    public ChargeChargeCharge() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.HEAVY);
        this.counter = -1;
    }

    @Override
    public void atBattleStart() {
        if (this.counter == -1) {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new StrengthPower(m, 3), 3));
            }
            this.setCounter(-2);
        }
    }


    public void setCounter(int counter) {
        this.counter = counter;
        if (counter == -2) {
            this.img = TextureLoader.getTexture("superResources/images/relics/angerman_used.png");
            this.usedUp();
            this.counter = -2;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
