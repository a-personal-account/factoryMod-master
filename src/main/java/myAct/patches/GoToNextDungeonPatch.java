package myAct.patches;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import javassist.CtBehavior;
import myAct.events.ForkInTheRoad;
import myAct.util.JungleChecks;

@SpirePatch(
        clz = ProceedButton.class,
        method = "goToNextDungeon"
)
public class GoToNextDungeonPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static SpireReturn<Void> Insert(ProceedButton __instance, AbstractRoom room) {
        if (Loader.isModLoaded("TheJungle")) {
            if (CardCrawlGame.dungeon instanceof TheCity || JungleChecks.ISJUNGLEFROM()) {
                AbstractDungeon.currMapNode.room = new ForkEventRoom(AbstractDungeon.currMapNode.room);
                AbstractDungeon.getCurrRoom().onPlayerEntry();
                AbstractDungeon.rs = AbstractDungeon.RenderScene.EVENT;

                AbstractDungeon.combatRewardScreen.clear();
                AbstractDungeon.previousScreen = null;
                AbstractDungeon.closeCurrentScreen();

                return SpireReturn.Return(null);
            }
        } else {
            if (CardCrawlGame.dungeon instanceof TheCity) {
                AbstractDungeon.currMapNode.room = new ForkEventRoom(AbstractDungeon.currMapNode.room);
                AbstractDungeon.getCurrRoom().onPlayerEntry();
                AbstractDungeon.rs = AbstractDungeon.RenderScene.EVENT;

                AbstractDungeon.combatRewardScreen.clear();
                AbstractDungeon.previousScreen = null;
                AbstractDungeon.closeCurrentScreen();

                return SpireReturn.Return(null);
            }
        }


        return SpireReturn.Continue();
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "fadeOut");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }

    public static class ForkEventRoom extends EventRoom {
        public AbstractRoom originalRoom;

        ForkEventRoom(AbstractRoom originalRoom) {
            this.originalRoom = originalRoom;
        }

        @Override
        public void onPlayerEntry() {
            AbstractDungeon.overlayMenu.proceedButton.hide();
            this.event = new ForkInTheRoad();
            this.event.onEnterRoom();
        }
    }
}
