//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package myAct.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import myAct.intents.CustomIntent;

@SpirePatch(
        clz = AbstractMonster.class,
        method = "updateIntentVFX"
)
public class addCustomIntentVFX {
    public addCustomIntentVFX() {
    }

    public static SpireReturn Prefix(AbstractMonster __instance) {
        if (CustomIntent.intents.containsKey(__instance.intent)) {
            CustomIntent ci = CustomIntent.intents.get(__instance.intent);
            ci.updateVFX(__instance);
            return SpireReturn.Return(null);
        } else {
            return SpireReturn.Continue();
        }
    }
}
