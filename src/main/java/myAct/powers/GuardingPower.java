package myAct.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import myAct.MyAct;
import myAct.monsters.ShrapnelHeap;
import myAct.util.TextureLoader;

public class GuardingPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = MyAct.makeID("GuardingPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture("superResources/images/powers/Guarding_84.png");
    private static final Texture tex32 = TextureLoader.getTexture("superResources/images/powers/Guarding_32.png");
    public AbstractCreature source;

    public GuardingPower(final AbstractCreature owner, final AbstractCreature source) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = -1;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;
        canGoNegative = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (m instanceof ShrapnelHeap && !m.isDead && !m.isDying) {
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new LoseHPAction(m, this.owner, damageAmount));
                return 0;
            }
        }

        return damageAmount;
    }


    @Override
    public AbstractPower makeCopy() {
        return new GuardingPower(owner, source);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}