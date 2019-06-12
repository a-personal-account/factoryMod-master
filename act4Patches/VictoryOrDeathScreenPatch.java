package myAct.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.GameOverStat;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import myAct.MyAct;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class VictoryOrDeathScreenPatch {
    @SpirePatch(clz = VictoryScreen.class, method = "createGameOverStats")
    public static class VictoryScreenPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(VictoryScreen __instance) {
            if (CardCrawlGame.dungeon instanceof com.megacrit.cardcrawl.dungeons.Exordium || CardCrawlGame.dungeon instanceof com.megacrit.cardcrawl.dungeons.TheCity || CardCrawlGame.dungeon instanceof com.megacrit.cardcrawl.dungeons.TheBeyond) {
                return;
            }
            if (CardCrawlGame.dungeon instanceof myAct.dungeons.Factory) {
                try {
                    String localizedString = (CardCrawlGame.languagePack.getScoreString("City Elites Killed")).NAME;
                    Field elite2PointsField = VictoryScreen.class.getDeclaredField("elite2Points");
                    elite2PointsField.setAccessible(true);
                    String elite2Points = Integer.toString(((Integer) elite2PointsField.get(null)).intValue());
                    __instance.stats.add(new GameOverStat(localizedString + " (" + CardCrawlGame.elites2Slain + ")", null, elite2Points));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        public static class Locator
                extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "add");

                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList(), methodCallMatcher)[5]};
            }
        }
    }


    @SpirePatch(clz = DeathScreen.class, method = "createGameOverStats")
    public static class DeathScreenPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(DeathScreen __instance) {
            if (CardCrawlGame.dungeon instanceof com.megacrit.cardcrawl.dungeons.Exordium || CardCrawlGame.dungeon instanceof com.megacrit.cardcrawl.dungeons.TheCity || CardCrawlGame.dungeon instanceof com.megacrit.cardcrawl.dungeons.TheBeyond || (CardCrawlGame.dungeon instanceof com.megacrit.cardcrawl.dungeons.TheEnding && !MyAct.wentToTheFactory)) {
                return;
            }
            if (CardCrawlGame.dungeon instanceof myAct.dungeons.Factory) {
                try {
                    String localizedString = (CardCrawlGame.languagePack.getScoreString("City Elites Killed")).NAME;
                    Field elite2PointsField = DeathScreen.class.getDeclaredField("elite2Points");
                    elite2PointsField.setAccessible(true);
                    String elite2Points = Integer.toString(((Integer) elite2PointsField.get(null)).intValue());
                    __instance.stats.add(new GameOverStat(localizedString + " (" + CardCrawlGame.elites2Slain + ")", null, elite2Points));
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        public static class Locator
                extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "add");

                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList(), methodCallMatcher)[5]};
            }
        }
    }
}
