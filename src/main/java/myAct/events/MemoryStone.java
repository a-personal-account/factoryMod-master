package myAct.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import myAct.MyAct;

import java.util.ArrayList;

public class MemoryStone extends AbstractImageEvent {

    public static final String ID = MyAct.makeID("MemoryStone");
    private static final String IMG = "superResources/images/events/placeholder.png";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private int screenNum = 0;

    public MemoryStone() {
        super(NAME, DESCRIPTIONS[0], IMG);
        this.noCardsInRewards = true;

        imageEventText.setDialogOption(OPTIONS[0]);
        imageEventText.setDialogOption(OPTIONS[1]);
        imageEventText.setDialogOption(OPTIONS[2]);
    }

    private AbstractCard getCard() {
        ArrayList<AbstractCard> allCards = new ArrayList<>();
        for (AbstractCard card : CardLibrary.getAllCards()) {
            if (card.rarity != AbstractCard.CardRarity.BASIC && card.rarity != AbstractCard.CardRarity.SPECIAL) {
                allCards.add(card);
            }
        }
        return allCards.get(AbstractDungeon.cardRandomRng.random(allCards.size() - 1)).makeCopy();
    }

    @Override
    protected void buttonEffect(int i) {
        switch (screenNum) {
            case 0:
                switch (i) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        if (1 == 1) {
                            AbstractCard card1 = getCard().makeCopy();
                            AbstractCard card2 = getCard().makeCopy();
                            AbstractCard card3 = getCard().makeCopy();
                            RewardItem reward = new RewardItem();
                            reward.cards.clear();
                            reward.cards.add(card1);
                            reward.cards.add(card2);
                            reward.cards.add(card3);
                            AbstractDungeon.getCurrRoom().addCardReward(reward);
                        }
                        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                        AbstractDungeon.combatRewardScreen.open();
                        break;
                    case 1:
                        AbstractDungeon.player.damage(new DamageInfo(null, 5));
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        for (int f = 0; f < 2; f++) {
                            AbstractCard card1 = getCard().makeCopy();
                            AbstractCard card2 = getCard().makeCopy();
                            AbstractCard card3 = getCard().makeCopy();
                            RewardItem reward = new RewardItem();
                            reward.cards.clear();
                            reward.cards.add(card1);
                            reward.cards.add(card2);
                            reward.cards.add(card3);
                            AbstractDungeon.getCurrRoom().addCardReward(reward);
                        }
                        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                        AbstractDungeon.combatRewardScreen.open();
                        break;
                    case 2:
                        AbstractDungeon.player.damage(new DamageInfo(null, 10));
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        for (int f = 0; f < 3; f++) {
                            AbstractCard card1 = getCard().makeCopy();
                            AbstractCard card2 = getCard().makeCopy();
                            AbstractCard card3 = getCard().makeCopy();
                            RewardItem reward = new RewardItem();
                            reward.cards.clear();
                            reward.cards.add(card1);
                            reward.cards.add(card2);
                            reward.cards.add(card3);
                            AbstractDungeon.getCurrRoom().addCardReward(reward);
                        }
                        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                        AbstractDungeon.combatRewardScreen.open();
                        break;
                }
                break;
            case 1:
                openMap();
                break;

        }
    }
}

