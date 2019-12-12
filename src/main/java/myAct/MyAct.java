package myAct;

import actlikeit.dungeons.CustomDungeon;
import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import myAct.dungeons.Factory;
import myAct.events.*;
import myAct.intents.CustomIntent;
import myAct.intents.SummonMiniBotIntent;
import myAct.relics.ChargeChargeCharge;
import myAct.relics.Remembrance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpireInitializer
public class MyAct implements
        PostInitializeSubscriber,
        EditStringsSubscriber,
        EditRelicsSubscriber {
    public static final Logger logger = LogManager.getLogger(MyAct.class.getSimpleName());

    public static void initialize() {
        BaseMod.subscribe(new MyAct());
    }

    public static String makeID(String id) {
        return "theFactory:" + id;
    }

    public static void addIntent(CustomIntent ci) {
        CustomIntent.intents.put(ci.intent, ci);
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelic(new ChargeChargeCharge(), RelicType.SHARED);
        BaseMod.addRelic(new Remembrance(), RelicType.SHARED);
    }

    private String languageSupport() {
        switch (Settings.language) {
            case ZHS:
                return "zhs";
            default:
                return "eng";
        }
    }

    @Override
    public void receivePostInitialize() {
        /*
        // Settings panel
        ModPanel settingsPanel = new ModPanel();

        BaseMod.registerModBadge(ImageMaster.loadImage(assetPath("modBadge.png")), "Factory Act", "Vex'd", "An act.", settingsPanel);
        */

        // Add events here
        BaseMod.addEvent(Flying.ID, Flying.class, Factory.ID);
        BaseMod.addEvent(BodyBloom.ID, BodyBloom.class, Factory.ID);
        BaseMod.addEvent(MemoryStone.ID, MemoryStone.class, Factory.ID);
        BaseMod.addEvent(SharkMouth.ID, SharkMouth.class, Factory.ID);
        BaseMod.addEvent(TroubledShopkeep.ID, TroubledShopkeep.class, Factory.ID);
        BaseMod.addEvent(WindingCorridors.ID, WindingCorridors.class, Factory.ID);

        // Add intents
        MyAct.addIntent(new SummonMiniBotIntent());

        // Add dungeon
        CustomDungeon.addAct(CustomDungeon.THEBEYOND, new Factory());
    }

    private void loadLocStrings(String language) {
        BaseMod.loadCustomStringsFile(EventStrings.class, "superResources/localization/" + language + "/events.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, "superResources/localization/" + language + "/ui.json");
        BaseMod.loadCustomStringsFile(MonsterStrings.class, "superResources/localization/" + language + "/monsters.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "superResources/localization/" + language + "/powers.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "superResources/localization/" + language + "/relics.json");
        BaseMod.loadCustomStringsFile(ScoreBonusStrings.class, "superResources/localization/" + language + "/score_bonuses.json");
        BaseMod.loadCustomStringsFile(BlightStrings.class, "superResources/localization/" + language + "/blights.json");
    }

    @Override
    public void receiveEditStrings() {
        String language = languageSupport();

        // Load english first to avoid crashing if translation doesn't exist for something
        loadLocStrings("eng");
        if(!language.equals("eng")) {
            loadLocStrings(language);
        }
    }
}
