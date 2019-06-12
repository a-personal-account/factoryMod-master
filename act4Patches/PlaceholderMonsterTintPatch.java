package myAct.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import myAct.monsters.AbstractPlaceholderMonster;

public class PlaceholderMonsterTintPatch {
    @SpirePatch(clz = AbstractMonster.class, method = "render")
    public static class RenderMonster {
        @SpirePrefixPatch
        public static void patch(AbstractMonster __instance, SpriteBatch sb) {
            if (__instance instanceof AbstractPlaceholderMonster) {
                __instance.tint.changeColor(((AbstractPlaceholderMonster) __instance).myColor);
            }
        }
    }
}
