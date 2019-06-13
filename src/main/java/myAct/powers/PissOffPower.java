package myAct.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import myAct.MyAct;
import myAct.util.TextureLoader;

public class PissOffPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = MyAct.makeID("PissOffPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("superResources/images/powers/PissOff_84.png");
    private static final Texture tex32 = TextureLoader.getTexture("superResources/images/powers/PissOff_32.png");
    public AbstractCreature source;
    private int timertime = 0;
    private boolean turn = true;

    public PissOffPower(final AbstractCreature owner) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = -1;

        type = PowerType.BUFF;
        isTurnBased = false;
        canGoNegative = false;


        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        if (turn) {
            timertime += 60 * Gdx.graphics.getDeltaTime();
            if (timertime >= 30) {
                AbstractDungeon.actionManager.addToTop(new RollMoveAction((AbstractMonster) this.owner));
                timertime = 0;
            }
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            turn = false;
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        {
            turn = true;
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new PissOffPower(owner);
    }

    @Override
    public void updateDescription() {
        {
            description = DESCRIPTIONS[0];
        }
    }

}