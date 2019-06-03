package myAct.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.DungeonTransitionScreen;
import javassist.CtBehavior;
import myAct.dungeons.Factory;

@SpirePatch(
        clz = DungeonTransitionScreen.class,
        method = "setAreaName"
)
public class SetAreaNamePatch {
    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(DungeonTransitionScreen __instance, String key) {
        if (Factory.ID.equals(key)) {
            __instance.levelName = Factory.NAME;
            __instance.levelNum = Factory.NUM;
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "name");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}