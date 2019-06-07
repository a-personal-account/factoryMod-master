//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package myAct.patches;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import myAct.intents.CustomIntent;

@SpirePatch(
        clz = AbstractMonster.class,
        method = "getIntentImg"
)
public class AddCustomIntents {
    public AddCustomIntents() {
    }

    public static SpireReturn<Texture> Prefix(AbstractMonster __instance) {
        return CustomIntent.intents.containsKey(__instance.intent) ? SpireReturn.Return(CustomIntent.intents.get(__instance.intent).display) : SpireReturn.Continue();
    }
}
