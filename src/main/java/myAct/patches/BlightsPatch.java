package myAct.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.helpers.BlightHelper;
import myAct.blights.EndlessReinforcements;
import myAct.blights.OppressiveHeat;
import myAct.blights.PainfulGloom;

@SpirePatch(
        clz = BlightHelper.class,
        method = "getBlight"
)
public class BlightsPatch {
    public static SpireReturn<AbstractBlight> Prefix(String id) {
        if (EndlessReinforcements.ID.equals(id)) {
            return SpireReturn.Return(new EndlessReinforcements());
        }
        if (OppressiveHeat.ID.equals(id)) {
            return SpireReturn.Return(new OppressiveHeat());
        }
        if (PainfulGloom.ID.equals(id)) {
            return SpireReturn.Return(new PainfulGloom());
        }
        return SpireReturn.Continue();
    }
}