package myAct.monsters;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import myAct.MyAct;
import myAct.actions.SpawnSentinelSpawnAction;
import myAct.intents.IntentEnums;

public class DecayingSentinel extends AbstractMonster {
    public static final String ID = MyAct.makeID("DecayingSentinel");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 258;
    private static final int HP_MAX = 258;
    private static final int A_8_HP_MIN = 269;
    private static final int A_8_HP_MAX = 269;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 304.0F;
    private static final float HB_H = 326.0F;

    public DecayingSentinel(float x, float y) {
        super(NAME, "DecayingSentinel", 25, HB_X, HB_Y, HB_W, HB_H, "superResources/images/monsters/DecayingSentinel.png", x, y);

        this.type = EnemyType.NORMAL;

        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(A_8_HP_MIN, A_8_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }
    }

    @Override
    public void usePreBattleAction() {
        this.currentHealth = this.maxHealth - 30;
        this.healthBarUpdatedEvent();
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                int enemyCreationNum = AbstractDungeon.cardRandomRng.random(25, 50);
                AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this, this, enemyCreationNum));
                AbstractDungeon.actionManager.addToBottom(new SpawnSentinelSpawnAction(enemyCreationNum));
                break;
        }
    }

    protected void getMove(int num) {
        this.setMove((byte) 1, IntentEnums.SUMMON_MINI_BOT_INTENT);
    }

    public void die() {
        super.die();
    }

}
