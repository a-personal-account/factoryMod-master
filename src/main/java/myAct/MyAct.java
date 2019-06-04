package myAct;

import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.helpers.RelicType;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.beyond.OrbWalker;
import com.megacrit.cardcrawl.monsters.exordium.Sentry;
import myAct.dungeons.Factory;
import myAct.events.*;
import myAct.monsters.*;
import myAct.patches.GetDungeonPatches;
import myAct.relics.ChargeChargeCharge;
import myAct.relics.Remembrance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpireInitializer
public class MyAct implements
        PostInitializeSubscriber,
        EditStringsSubscriber,
        EditRelicsSubscriber,
        CustomSavable<Boolean> {
    public static final Logger logger = LogManager.getLogger(MyAct.class.getSimpleName());

    public static boolean wentToTheFactory = false;

    public static void initialize() {
        BaseMod.subscribe(new MyAct());
    }

    public static String makeID(String id) {
        return "theFactory:" + id;
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

        // Weak Pool
        BaseMod.addMonster(FactoryEncounterIDList.SMALL_MINIBOT_GANG_ENCOUNTER_ID, "4 Mini-Bots", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new MiniBotBeamer(-385.0F, -15.0F),
                        new MiniBotBuilderBuilder(-133.0F, 0.0F),
                        new MiniBotRepair(100.0F, 224.0F),
                        new MiniBotDebuff(200.0F, 0.0F)
                }));
        BaseMod.addMonster(FactoryEncounterIDList.PERSONNEL_SMALL_ENCOUNTER_ID, "2 Experiment Personnel", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new ExpPersonnel(-200.0F, 0.0F, false),
                        new ExpPersonnel(100.0F, 0.0F, true)
                }
        ));
        BaseMod.addMonster(FactoryEncounterIDList.BIG_BOT_ENCOUNTER_ID, () -> new BigBot(0.0F, 100.0F));

        // Strong Pool
        BaseMod.addMonster(FactoryEncounterIDList.LARGE_MINIBOT_HORDE_ENCOUNTER_ID, "6 Mini-Bots", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new MiniBotVirus(-385.0F, -15.0F),
                        new MiniBotRepair(-166.0F, 187.0F),
                        new MiniBotDebuff(-120.0F, 0.0F),
                        new MiniBotBeamer(200.0F, 0.0F),
                        new MiniBotBuilderBuilder(0.0F, 220.0F),
                        new MiniBotBeamer(200.0F, 200.0F)
                }));
        BaseMod.addMonster(FactoryEncounterIDList.PERSONNEL_AND_BOTS_ENCOUNTER_ID, "Personnel and Mini-Bots", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new ExpPersonnel(-170.0F, 0.0F, false),
                        new ExpPersonnel(100.0F, 0.0F, true),
                        new MiniBotBeamer(-385.0F, -15.0F),
                        new MiniBotDebuff(166.0F, 200.0F)
                }));
        BaseMod.addMonster(FactoryEncounterIDList.PERSONNEL_AND_ORB_ENCOUNTER_ID, "Personnel and Big Bot", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new ExpPersonnel(0.0F, 50.0F, false),
                        new ExpPersonnel(-340.0F, 33.0F, true),
                        new MiniBotVirus(-133.0F, 250.0F),
                        new MiniBotRepair(100.0F, 300.0F)
                }
        ));
        BaseMod.addMonster(FactoryEncounterIDList.SHRAPNEL_THROWER_ENCOUNTER_ID, "Metal Thrower", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new ShrapnelHeap(-225.0F, 0.0F),
                        new ShrapnelTosser(50.F, 0.0F)
                }
        ));
        BaseMod.addMonster(FactoryEncounterIDList.BOT_BUNDLE_ENCOUNTER_ID, "Big Bot Bundle", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new BigBot(-175.0F, 125.0F),
                        new ToyOrb(100.0F, 260.0F, 0),
                        new Sentry(0.0F, 0.0F),
                        new MiniBotRepair(-200.0F, 325.0F)
                }
        ));
        BaseMod.addMonster(FactoryEncounterIDList.DEFECTIVE_SENTRY_ENCOUNTER_ID, () -> new DefectiveSentry(0.0F, 100.0F));
        BaseMod.addMonster(FactoryEncounterIDList.BORING_BASEGAME_ENCOUNTER_ID, "Orbs and Orb Walker", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new ToyOrb(-255.0F, 300.0F, 0),
                        new OrbWalker(0.0F, 0.0F),
                        new ToyOrb(200.0F, 250.0F, 1)
                }
        ));
        BaseMod.addMonster(FactoryEncounterIDList.DOUBLE_BIG_BOT_ENCOUNTER_ID, "Sentries and Big Bot", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new Sentry(-225.0F, 20.0F),
                        new BigBot(-133.0F, 0.0F),
                        new Sentry(175.0F, 175.0F)
                }
        ));
        BaseMod.addMonster(FactoryEncounterIDList.DECAYING_SENTINEL_ENCOUNTER_ID, "Decaying Sentinel", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new DecayingSentinel(60.0F, 125.0F),
                        new SentinelSpawn(-150.0F, 0.0F, 30)
                }
        ));

        // Elite Pool
        BaseMod.addMonster(FactoryEncounterIDList.MANSERVANTES_ENCOUNTER_ID, () -> new Manservantes(0.0F, 100.0F));
        BaseMod.addMonster(FactoryEncounterIDList.SMOG_ELEMENTAL_ENCOUNTER_ID, () -> new SmogElemental(0.0F, 100.0F));
        BaseMod.addMonster(FactoryEncounterIDList.DRINK_BREWER_ENCOUNTER_ID, () -> new DrinkBrewer(0.0F, 100.0F));

        // Bosses
        BaseMod.addMonster(FactoryEncounterIDList.GUARDIAN_2_ENCOUNTER_ID, () -> new Guardian2(0.0F, 100.0F));
        BaseMod.addMonster(FactoryEncounterIDList.EXPERIMENT_01_ENCOUNTER_ID, () -> new Experiment01(0.0F, 100.0F));
        BaseMod.addMonster(FactoryEncounterIDList.SPIDER_ENCOUNTER_ID, () -> new SPIDER(0.0F, 100.0F));

        // Add bosses here
        BaseMod.addBoss(Factory.ID, Guardian2.ID, "superResources/images/map/placeholder.png", "superResources/images/map/placeholderOutline.png");
        BaseMod.addBoss(Factory.ID, SPIDER.ID, "superResources/images/map/placeholder.png", "superResources/images/map/placeholderOutline.png"); // A R T
        BaseMod.addBoss(Factory.ID, Experiment01.ID, "superResources/images/map/placeholder.png", "superResources/images/map/placeholderOutline.png");

        // Add dungeon
        GetDungeonPatches.addDungeon(Factory.ID, Factory.builder());
        // GetDungeonPatches.addNextDungeon(Factory.ID, TheEnding.ID);

        //savable boolean
        BaseMod.addSaveField("wentToTheFactory", this);
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

    private void loadLocStrings(String language) {

        BaseMod.loadCustomStringsFile(EventStrings.class, "superResources/localization/eng/events.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, "superResources/localization/eng/ui.json");
        BaseMod.loadCustomStringsFile(MonsterStrings.class, "superResources/localization/eng/monsters.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "superResources/localization/eng/powers.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "superResources/localization/eng/relics.json");
        BaseMod.loadCustomStringsFile(ScoreBonusStrings.class, "superResources/localization/eng/score_bonuses.json");
        BaseMod.loadCustomStringsFile(BlightStrings.class, "superResources/localization/eng/blights.json");
    }

    @Override
    public void receiveEditStrings() {
        String language = languageSupport();

        // Load english first to avoid crashing if translation doesn't exist for something
        loadLocStrings("eng");
        loadLocStrings(language);
    }

    @Override
    public Boolean onSave() {
        logger.info("Saving wentToTheFactory boolean: " + wentToTheFactory);
        return wentToTheFactory;
    }

    @Override
    public void onLoad(Boolean loadedBoolean) {
        if (loadedBoolean != null) {
            wentToTheFactory = loadedBoolean;
        } else {
            wentToTheFactory = false;
        }
        logger.info("Loading wentToTheFactory boolean: " + wentToTheFactory);
    }

}
