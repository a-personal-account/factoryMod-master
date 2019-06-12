package myAct.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import javassist.CtBehavior;
import myAct.dungeons.Factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SpirePatch(
        clz = ProceedButton.class,
        method = "update"
)
public class ContinueOntoHeartPatch {
    @SpireInsertPatch(
            locator = Locator.class
    )

    public static void Insert(ProceedButton __instance) {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {
            if (AbstractDungeon.id.equals(Factory.ID)) {
                if (AbstractDungeon.ascensionLevel >= 20 && AbstractDungeon.bossList.size() == 2) {
                    try {
                        Method yuckyPrivateMethod = ProceedButton.class.getDeclaredMethod("goToDoubleBoss");
                        yuckyPrivateMethod.setAccessible(true);
                        yuckyPrivateMethod.invoke(__instance);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } else if (!Settings.isEndless) {
                    try {
                        Method yuckyPrivateMethod = ProceedButton.class.getDeclaredMethod("goToVictoryRoomOrTheDoor");
                        yuckyPrivateMethod.setAccessible(true);
                        yuckyPrivateMethod.invoke(__instance);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.InstanceOfMatcher(MonsterRoomBoss.class);
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
