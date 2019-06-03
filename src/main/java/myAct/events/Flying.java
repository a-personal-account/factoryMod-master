package myAct.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Injury;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import myAct.MyAct;

import java.util.ArrayList;

public class Flying extends AbstractImageEvent {

    public static final String ID = MyAct.makeID("Flying");
    private static final String IMG = "superResources/images/events/placeholder.png";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;

    public Flying() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;

        boolean haveaRare = false;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.rarity == AbstractCard.CardRarity.RARE) {
                haveaRare = true;
            }
        }
        if (haveaRare) {
            imageEventText.setDialogOption(OPTIONS[0]);
        } else {
            imageEventText.setDialogOption(OPTIONS[4], true);
        }
        imageEventText.setDialogOption(OPTIONS[1]);
        imageEventText.setDialogOption(OPTIONS[2]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0:
                        ArrayList<AbstractCard> rarecardslist = new ArrayList<>();
                        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                            if (c.rarity == AbstractCard.CardRarity.RARE) {
                                rarecardslist.add(c);
                            }
                        }
                        AbstractCard c = rarecardslist.get(AbstractDungeon.cardRandomRng.random(rarecardslist.size() - 1));
                        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float) Settings.WIDTH / 2.0F - 30.0F * Settings.scale - AbstractCard.IMG_WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        AbstractDungeon.player.masterDeck.removeCard(c);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 1:
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Injury(), (float) Settings.WIDTH * 0.6F, (float) Settings.HEIGHT / 2.0F));
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Injury(), (float) Settings.WIDTH * 0.3F, (float) Settings.HEIGHT / 2.0F));
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 2:
                        AbstractDungeon.player.loseRelic(AbstractDungeon.player.relics.get(AbstractDungeon.player.relics.size() - 1).relicId);
                        AbstractDungeon.player.loseRelic(AbstractDungeon.player.relics.get(AbstractDungeon.player.relics.size() - 1).relicId);
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                }
                break;
            case 1:
                openMap();
                break;

        }
    }
}

