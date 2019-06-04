package myAct.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import myAct.MyAct;
import myAct.dungeons.Factory;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractRoom.class,
        method = "update"
)
public class AbstractRoomUpdateIncrementElitesPatch {

    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(AbstractRoom __instance) {
        if (CardCrawlGame.dungeon instanceof Factory) {
            ++CardCrawlGame.elites3Slain;
            MyAct.logger.info("Factory elites: " + CardCrawlGame.elites3Slain);
        }
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(CardCrawlGame.class, "dungeon");

            return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher)[1]};
        }
    }
}
