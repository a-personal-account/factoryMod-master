package myAct.dungeons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.MonsterInfo;
import com.megacrit.cardcrawl.rooms.EmptyRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import myAct.MyAct;
import myAct.monsters.FactoryEncounterIDList;
import myAct.patches.GetDungeonPatches;
import myAct.scenes.FactoryScene;

import java.util.ArrayList;

public class Factory extends AbstractDungeon {
    public static final String ID = MyAct.makeID("Factory");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String[] TEXT = uiStrings.TEXT;
    public static final String NAME = TEXT[0];
    public static final String NUM = TEXT[1];

    public Factory(AbstractPlayer p, ArrayList<String> emptyList) {
        super(NAME, ID, p, emptyList);

        if (scene != null) {
            scene.dispose();
        }
        scene = new FactoryScene(); // TODO
        fadeColor = Color.valueOf("0f220aff");

        initializeLevelSpecificChances();
        mapRng = new com.megacrit.cardcrawl.random.Random(Settings.seed + AbstractDungeon.actNum * 100);
        generateMap();

        CardCrawlGame.music.changeBGM("FACTORYMAIN");
        AbstractDungeon.currMapNode = new MapRoomNode(0, -1);
        AbstractDungeon.currMapNode.room = new EmptyRoom();
    }

    public Factory(AbstractPlayer p, SaveFile saveFile) {
        super(NAME, p, saveFile);

        CardCrawlGame.dungeon = this;
        if (scene != null) {
            scene.dispose();
        }
        scene = new FactoryScene(); // TODO
        fadeColor = Color.valueOf("0f220aff");

        initializeLevelSpecificChances();
        miscRng = new com.megacrit.cardcrawl.random.Random(Settings.seed + saveFile.floor_num);
        CardCrawlGame.music.changeBGM("FACTORYMAIN");
        mapRng = new com.megacrit.cardcrawl.random.Random(Settings.seed + saveFile.act_num * 100);
        generateMap();
        firstRoomChosen = true;

        populatePathTaken(saveFile);
    }

    public static GetDungeonPatches.AbstractDungeonBuilder builder() {
        return new GetDungeonPatches.AbstractDungeonBuilder() {
            @Override
            public AbstractDungeon build(AbstractPlayer p, ArrayList<String> theList) {
                return new Factory(p, theList);
            }

            @Override
            public AbstractDungeon build(AbstractPlayer p, SaveFile save) {
                return new Factory(p, save);
            }
        };
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
    protected void generateMonsters() {
        // TODO: This is copied from TheCity
        generateWeakEnemies(2);
        generateStrongEnemies(12);
        generateElites(10);
    }

    protected void generateWeakEnemies(int count) {
        // TODO: This is copied from TheCity
        ArrayList<MonsterInfo> monsters = new ArrayList<>();
        monsters.add(new MonsterInfo(FactoryEncounterIDList.SMALL_MINIBOT_GANG_ENCOUNTER_ID, 2.0F));
        monsters.add(new MonsterInfo(FactoryEncounterIDList.PERSONNEL_SMALL_ENCOUNTER_ID, 2.0F));
        monsters.add(new MonsterInfo(FactoryEncounterIDList.BIG_BOT_ENCOUNTER_ID, 2.0F));
        MonsterInfo.normalizeWeights(monsters);
        populateMonsterList(monsters, count, false);
    }

    protected void generateStrongEnemies(int count) {
        // TODO: This is copied from TheCity
        ArrayList<MonsterInfo> monsters = new ArrayList<>();
        monsters.add(new MonsterInfo(FactoryEncounterIDList.LARGE_MINIBOT_HORDE_ENCOUNTER_ID, 1.0F));
        monsters.add(new MonsterInfo(FactoryEncounterIDList.PERSONNEL_AND_BOTS_ENCOUNTER_ID, 1.0F));
        monsters.add(new MonsterInfo(FactoryEncounterIDList.SHRAPNEL_THROWER_ENCOUNTER_ID, 1.0F));
        monsters.add(new MonsterInfo(FactoryEncounterIDList.PERSONNEL_AND_ORB_ENCOUNTER_ID, 1.0F));
        monsters.add(new MonsterInfo(FactoryEncounterIDList.BOT_BUNDLE_ENCOUNTER_ID, 1.0F));
        monsters.add(new MonsterInfo(FactoryEncounterIDList.DEFECTIVE_SENTRY_ENCOUNTER_ID, 1.0F));
        monsters.add(new MonsterInfo(FactoryEncounterIDList.BORING_BASEGAME_ENCOUNTER_ID, 1.0F));
        monsters.add(new MonsterInfo(FactoryEncounterIDList.DOUBLE_BIG_BOT_ENCOUNTER_ID, 1.0F));
        monsters.add(new MonsterInfo(FactoryEncounterIDList.DECAYING_SENTINEL_ENCOUNTER_ID, 1.0F));

        MonsterInfo.normalizeWeights(monsters);
        populateFirstStrongEnemy(monsters, generateExclusions());
        populateMonsterList(monsters, count, false);
    }

    protected void generateElites(int count) {
        // TODO: This is copied from TheCity
        ArrayList<MonsterInfo> monsters = new ArrayList<>();
        monsters.add(new MonsterInfo(FactoryEncounterIDList.MANSERVANTES_ENCOUNTER_ID, 1.0F));
        monsters.add(new MonsterInfo(FactoryEncounterIDList.DRINK_BREWER_ENCOUNTER_ID, 1.0F));
        monsters.add(new MonsterInfo(FactoryEncounterIDList.SMOG_ELEMENTAL_ENCOUNTER_ID, 1.0f));
        MonsterInfo.normalizeWeights(monsters);
        populateMonsterList(monsters, count, true);
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
    protected void initializeBoss() {
        bossList.clear();
        // Bosses are added via BaseMod in MyAct.receivePostInitialize()
    }

    @Override
    protected void initializeEventList() {
        // Events are added via BaseMod in MyAct.receivePostInitialize()
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
