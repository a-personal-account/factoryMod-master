package myAct.blights;

import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.BlightStrings;
import myAct.util.TextureLoader;

public abstract class Blight extends AbstractBlight {

    public static BlightStrings STRINGS;

    public Blight(String id, String textureString) {
        super(id, "", "", "", true);

        STRINGS = CardCrawlGame.languagePack.getBlightString(id);
        description = getDescription();
        name = STRINGS.NAME;

        img = TextureLoader.getTexture("superResources/images/relics/placeholder_relic.png");
        outlineImg = TextureLoader.getTexture("superResources/images/relics/outline/placeholder_relic.png");

        this.tips.clear();
        this.tips.add(new PowerTip(name, description));
    }

    private static String getDescription() {
        return STRINGS.DESCRIPTION[0];
    }
}