package myAct.dungeons;

import actlikeit.dungeons.CustomDungeon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.beyond.OrbWalker;
import com.megacrit.cardcrawl.monsters.city.SphericGuardian;
import com.megacrit.cardcrawl.monsters.exordium.Sentry;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import myAct.MyAct;
import myAct.monsters.*;
import myAct.scenes.FactoryScene;

import java.util.ArrayList;

public class Factory extends CustomDungeon {
    public static final String ID = MyAct.makeID("Factory");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;
    public static final String NAME = TEXT[0];
    public static final String NUM = TEXT[1];

    public Factory() {
        super(NAME, ID);
        setMainMusic("superResources/audio/music/factory_main.ogg");

        addTempMusic("FACTORYELITE", "superResources/audio/music/factory_elite.ogg");
        addTempMusic("BOSS_FACTORY", "superResources/audio/music/boss_factory.ogg");

        // Weak Pool
        addMonster(FactoryEncounterIDList.SMALL_MINIBOT_GANG_ENCOUNTER_ID, "4 Mini-Bots", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new MiniBotBeamer(-400.0F, 0.0F),
                        new MiniBotBuilderBuilder(-200.0F, 0.0F),
                        new MiniBotRepair(0.0F, 0.0F),
                        new MiniBotDebuff(200.0F, 0.0F)
                }));
        addMonster(FactoryEncounterIDList.PERSONNEL_SMALL_ENCOUNTER_ID, "2 Experiment Personnel", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new ExpPersonnel(-250.0F, 0.0F, false),
                        new ExpPersonnel(150.0F, 0.0F, true)
                }
        ));
        addMonster(FactoryEncounterIDList.BIG_BOT_ENCOUNTER_ID, () -> new BigBot(10.0F, 0.0F));

        // Strong Pool
        addStrongMonster(FactoryEncounterIDList.LARGE_MINIBOT_HORDE_ENCOUNTER_ID, "6 Mini-Bots", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new MiniBotVirus(-450.0F, 0.0F),
                        new MiniBotRepair(-300.0F, 250.0F),
                        new MiniBotBuilderBuilder(-150.0F, 0.0F),
                        new MiniBotDebuff(0.0F, 250.0F),
                        new MiniBotBeamer(150.0F, 0.0F),
                }));
        addStrongMonster(FactoryEncounterIDList.PERSONNEL_AND_BOTS_ENCOUNTER_ID, "Personnel and Mini-Bots", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new MiniBotDebuff(-500.0F, 0.0F),
                        new MiniBotBeamer(-250.0F, 0.0F),
                        new ExpPersonnel(0.0F, 0.0F, true),
                        new ExpPersonnel(250.0F, 0.0F, false)
                }));
        addStrongMonster(FactoryEncounterIDList.PERSONNEL_AND_ORB_ENCOUNTER_ID, "Personnel and Big Bot", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new ExpPersonnel(-500.0F, 0.0F, true),
                        new MiniBotVirus(-250.0F, 0.0F),
                        new ExpPersonnel(0.0F, 0.0F, false),
                        new MiniBotRepair(250.0F, 0.0F)
                }
        ));
        addStrongMonster(FactoryEncounterIDList.SHRAPNEL_THROWER_ENCOUNTER_ID, "Metal Thrower", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new ShrapnelHeap(-300.0F, 0.0F),
                        new ShrapnelTosser(50.F, 0.0F)
                }
        ));
        addStrongMonster(FactoryEncounterIDList.BOT_BUNDLE_ENCOUNTER_ID, "Big Bot Bundle", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new MiniBotRepair(-500.0F, 0.0F),
                        new SphericGuardian(-250.0F, 0.0F),
                        new ToyOrb(-175.0F, 350.0F, 0),
                        new Sentry(100.0F, 0.0F)
                }
        ));
        addStrongMonster(FactoryEncounterIDList.DEFECTIVE_SENTRY_ENCOUNTER_ID, () -> new DefectiveSentry(0.0F, 0.0F));
        addStrongMonster(FactoryEncounterIDList.BORING_BASEGAME_ENCOUNTER_ID, "Orbs and Orb Walker", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new ToyOrb(-255.0F, 300.0F, 0),
                        new OrbWalker(0.0F, 0.0F),
                        new ToyOrb(200.0F, 250.0F, 1)
                }
        ));
        addStrongMonster(FactoryEncounterIDList.DOUBLE_BIG_BOT_ENCOUNTER_ID, "Sentries and Big Bot", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new Sentry(-450.0F, 0.0F),
                        new Sentry(-150.0F, 0.0F),
                        new BigBot(150.0F, 0.0F)
                }
        ));
        addStrongMonster(FactoryEncounterIDList.DECAYING_SENTINEL_ENCOUNTER_ID, "Decaying Sentinel", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new SentinelSpawn(-200.0F, 0.0F, 30),
                        new DecayingSentinel(100.0F, 0.0F)
                }
        ));
        addStrongMonster(FactoryEncounterIDList.SENTRY_GUARDS_ENCOUNTER_ID, "Sentry and Guards", () -> new MonsterGroup(
                new AbstractMonster[]{
                        new MiniBotDebuff(-450.0F, 0.0F),
                        new BigBot(-150.0F, 0.0F),
                        new MiniBotRepair(150.0F, 0.0F)
                }
        ));

        // Elite Pool
        addEliteEncounter(FactoryEncounterIDList.MANSERVANTES_ENCOUNTER_ID, () -> new Manservantes(0.0F, 0.0F));
        addEliteEncounter(FactoryEncounterIDList.SMOG_ELEMENTAL_ENCOUNTER_ID, () -> new SmogElemental(-50.0F, 75.0F));
        addEliteEncounter(FactoryEncounterIDList.DRINK_BREWER_ENCOUNTER_ID, () -> new DrinkBrewer(0.0F, 0.0F));

        // Bosses
        addBoss(FactoryEncounterIDList.GUARDIAN_2_ENCOUNTER_ID, () -> new Guardian2(0.0F, 0.0F), "superResources/images/map/placeholder.png", "superResources/images/map/placeholderOutline.png");
        addBoss(FactoryEncounterIDList.EXPERIMENT_01_ENCOUNTER_ID, () -> new Experiment01(0.0F, 0.0F), "superResources/images/map/placeholder.png", "superResources/images/map/placeholderOutline.png"); // A R T
        addBoss(FactoryEncounterIDList.SPIDER_ENCOUNTER_ID, () -> new SPIDER(0.0F, 100.0F), "superResources/images/map/placeholder.png", "superResources/images/map/placeholderOutline.png");
    }

    public Factory(CustomDungeon cd, AbstractPlayer p, ArrayList<String> emptyList)
    {
        super(cd, p, emptyList);
    }
    public Factory(CustomDungeon cd, AbstractPlayer p, SaveFile saveFile)
    {
        super(cd, p, saveFile);
    }

    @Override
    public AbstractScene DungeonScene() {
        return new FactoryScene();
    }

    @Override
    protected void initializeLevelSpecificChances() {
        shopRoomChance = 0.05F;
        restRoomChance = 0.12F;
        treasureRoomChance = 0.0F;
        eventRoomChance = 0.22F;
        eliteRoomChance = 0.08F;
        smallChestChance = 50;
        mediumChestChance = 33;
        largeChestChance = 17;
        commonRelicChance = 50;
        uncommonRelicChance = 33;
        rareRelicChance = 17;
        colorlessRareChance = 0.3F;
        if (AbstractDungeon.ascensionLevel >= 12) {
            cardUpgradedChance = 0.25F;
        } else {
            cardUpgradedChance = 0.5F;
        }
    }

    @Override
    protected ArrayList<String> generateExclusions() {
        // TODO: This is copied from TheCity
        ArrayList<String> retVal = new ArrayList<>();
        /* switch (monsterList.get(monsterList.size() - 1)) {
            case "Spheric Guardian":
                retVal.add("Sentry and Sphere");
                break;
            case "3 Byrds":
                retVal.add("Chosen and Byrds");
                break;
            case "Chosen":
                retVal.add("Chosen and Byrds");
                retVal.add("Cultist and Chosen");
                break;
        } */
        // TODO: THIS IS EXCLUSIVE ENCOUNTERS
        return retVal;
    }

    @Override
    protected void initializeEventImg() {
        if (eventBackgroundImg != null) {
            eventBackgroundImg.dispose();
            eventBackgroundImg = null;
        }
        eventBackgroundImg = ImageMaster.loadImage("images/ui/event/panel.png");
    }

    @Override
    protected void initializeShrineList() {
        // TODO: This is copied from TheCity
        shrineList.add("Match and Keep!");
        shrineList.add("Wheel of Change");
        shrineList.add("Golden Shrine");
        shrineList.add("Transmorgrifier");
        shrineList.add("Purifier");
        shrineList.add("Upgrade Shrine");
    }
}
