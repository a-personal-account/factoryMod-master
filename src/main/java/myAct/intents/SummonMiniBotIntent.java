package myAct.intents;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import myAct.MyAct;

public class SummonMiniBotIntent extends CustomIntent {

    public static final String ID = MyAct.makeID("SUMMON_MINIBOT_INTENT");

    //First parameter (Intent) is your Intent from the class above
    //Second one (String) is the headline of your intent in its tooltip window when you hover over the enemy
    //Third one (String) contains the path to the image you want floating above the enemy head (128x128 with plenty of spacing, just like with custom relics)
    //Fourth one (String) contains the path to the image you want in the tooltip window (64x64, no spacing needed)
    //Fifth one (Optional String) is a String that is displayed in the tooltip window if you don't override the description() Method.
    public SummonMiniBotIntent() {
        super(IntentEnums.SUMMON_MINI_BOT_INTENT, "Summon",
                "superResources/images/ui/intent/ABOVE_ENEMY.png",
                "superResources/images/ui/intent/IN_TOOLTIP.png",
                "This enemy intends to summon another enemy into combat.");
    }

    @Override
    public float updateVFXInInterval(AbstractMonster mo) {
        return 2.0F;
    }

}
