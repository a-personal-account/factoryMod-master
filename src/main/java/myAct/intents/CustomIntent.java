//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package myAct.intents;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;

import java.util.HashMap;
import java.util.Map;

public abstract class CustomIntent {
    public static Map<Intent, CustomIntent> intents = new HashMap();
    public Intent intent;
    public Texture display;
    public Texture tip;
    public String header;
    public String simpledescription;

    public CustomIntent(Intent intent, String header, String display, String tip, String simpledescription) {
        this.header = header;
        this.display = ImageMaster.loadImage(display);
        this.tip = ImageMaster.loadImage(tip);
        this.simpledescription = simpledescription;
        this.intent = intent;
    }

    public CustomIntent(Intent intent, String header, String display, String tip) {
        this(intent, header, display, tip, null);
    }

    public String description(AbstractMonster am) {
        return this.simpledescription;
    }

    public void updateVFX(AbstractMonster am) {
        if (am.intentAlpha > 0.0F) {
            float intentParticleTimer = (Float) ReflectionHacks.getPrivate(am, AbstractMonster.class, "intentParticleTimer");
            intentParticleTimer -= Gdx.graphics.getDeltaTime();
            if (intentParticleTimer <= 0.0F) {
                intentParticleTimer = this.updateVFXInInterval(am);
            }

            ReflectionHacks.setPrivate(am, AbstractMonster.class, "intentParticleTimer", intentParticleTimer);
        }

    }

    public float updateVFXInInterval(AbstractMonster am) {
        return 1.0F;
    }
}
