package myAct.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import myAct.MyAct;
import myAct.util.TextureLoader;

public class SickDrinkPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = MyAct.makeID("SickDrinkPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("superResources/images/powers/Drunk_84.png");
    private static final Texture tex32 = TextureLoader.getTexture("superResources/images/powers/Drunk_32.png");
    public AbstractCreature source;

    public SickDrinkPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void atStartOfTurnPostDraw() {
        AbstractDungeon.actionManager.addToBottom(new ExhaustAction(this.owner, this.owner, this.amount, true));
    }

    @Override
    public AbstractPower makeCopy() {
        return new SickDrinkPower(owner, source, amount);
    }

    @Override
    public void updateDescription() {
        {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }
    }

}