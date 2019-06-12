package myAct.patches;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import java.util.ArrayList;
import javassist.CtBehavior;
import myAct.MyAct;
import myAct.blights.EndlessReinforcements;
import myAct.blights.OppressiveHeat;
import myAct.blights.PainfulGloom;
import myAct.dungeons.Factory;


@SpirePatch(clz = ProceedButton.class, method = "goToNextDungeon")
public class GoToNextDungeonPatch
{
    @SpireInsertPatch(locator = Locator.class)
    public static SpireReturn<Void> Insert(ProceedButton __instance, AbstractRoom room) {
        if (CardCrawlGame.dungeon instanceof com.megacrit.cardcrawl.dungeons.TheBeyond) {
            ArrayList<AbstractBlight> blightList = new ArrayList<AbstractBlight>();
            blightList.add(new EndlessReinforcements());
            blightList.add(new OppressiveHeat());
            blightList.add(new PainfulGloom());
            AbstractBlight blightToGet = (AbstractBlight)blightList.get(AbstractDungeon.cardRandomRng.random(blightList.size() - 1));
            AbstractDungeon.getCurrRoom().spawnBlightAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), blightToGet);
            CardCrawlGame.nextDungeon = Factory.ID;
            MyAct.wentToTheFactory = true;
        }

        return SpireReturn.Continue();
    }

    private static class Locator
            extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "fadeOut");
            return LineFinder.findInOrder(ctMethodToPatch, methodCallMatcher);
        }
    }
}
