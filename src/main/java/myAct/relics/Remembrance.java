package myAct.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import myAct.MyAct;
import myAct.util.TextureLoader;

public class Remembrance extends CustomRelic {

    public static final String ID = MyAct.makeID("Remembrance");

    private static final Texture IMG = TextureLoader.getTexture("superResources/images/relics/remembrance.png");
    private static final Texture OUTLINE = TextureLoader.getTexture("superResources/images/relics/outline/remembrance.png");

    public Remembrance() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
