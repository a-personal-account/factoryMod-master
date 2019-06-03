package myAct.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import myAct.MyAct;
import myAct.relics.ChargeChargeCharge;

import java.util.ArrayList;

public class WindingCorridors extends AbstractImageEvent {

    public static final String ID = MyAct.makeID("WindingCorridors");
    private static final String IMG = "superResources/images/events/placeholder.png";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;

    public WindingCorridors() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;

        imageEventText.setDialogOption(OPTIONS[0]);
        imageEventText.setDialogOption(OPTIONS[1]);
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0:
                        for (int q = 0; q < 2; q++) {
                            ArrayList<AbstractCard> cardList = new ArrayList<>();
                            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                                if (c.upgraded) {
                                    cardList.add(c);
                                }
                            }
                            AbstractCard downgradeCard = cardList.get(AbstractDungeon.cardRandomRng.random(cardList.size() - 1)).makeCopy();
                            AbstractCard downgradeCardCopy = downgradeCard.makeCopy();
                            for (int f = 0; f < downgradeCard.timesUpgraded - 1; f++) {
                                downgradeCardCopy.upgrade();
                            }
                            int index = AbstractDungeon.player.masterDeck.group.indexOf(downgradeCard);
                            AbstractDungeon.player.masterDeck.group.remove(index);
                            AbstractDungeon.player.masterDeck.group.add(index, downgradeCardCopy);
                            AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(downgradeCardCopy));
                        }
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                    case 1:
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, RelicLibrary.getRelic(ChargeChargeCharge.ID));
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
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

