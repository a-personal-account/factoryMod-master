package myAct.monsters;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import myAct.MyAct;
import myAct.actions.SpawnSentinelSpawnAction;

public class DecayingSentinel extends AbstractPlaceholderMonster {
    public static final String ID = MyAct.makeID("DecayingSentinel");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 225;
    private static final int HP_MAX = 225;
    private static final int A_8_HP_MIN = 246;
    private static final int A_8_HP_MAX = 246;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 150.0F;

    public DecayingSentinel(float x, float y) {
        super(NAME, "DecayingSentinel", 25, HB_X, HB_Y, HB_W, HB_H, "superResources/images/monsters/hex.png", x, y);

        this.type = EnemyType.NORMAL;

        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(A_8_HP_MIN, A_8_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }

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
        this.setMove((byte) 1, Intent.UNKNOWN);
    }

    public void die() {
        super.die();
    }

}
