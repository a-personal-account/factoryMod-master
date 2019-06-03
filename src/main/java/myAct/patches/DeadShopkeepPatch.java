package myAct.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.shop.Merchant;
import myAct.relics.Remembrance;

public class DeadShopkeepPatch {
    @SpirePatch(
            clz = Merchant.class,
            method = "update"
    )
    public static class StopClickMerchant {
        public static SpireReturn Prefix(Merchant __instance) {
            if (AbstractDungeon.player.hasRelic(Remembrance.ID)) {
                return SpireReturn.Return(null);
            } else {
                return SpireReturn.Continue();
            }
        }
    }
}