package myAct.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import myAct.MyAct;
import myAct.actions.SpawnBronzeOrbAction;
import myAct.intents.IntentEnums;

public class Manservantes extends AbstractMonster {
    public static final String ID = MyAct.makeID("Manservantes");
    private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterstrings.NAME;
    public static final String[] DIALOG = monsterstrings.DIALOG;
    private static final int HP_MIN = 195;
    private static final int HP_MAX = 200;
    private static final int A_8_HP_MIN = 200;
    private static final int A_8_HP_MAX = 210;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 309.0F;
    private static final float HB_H = 446.0F;
    private static final int ATTACK_DAMAGE = 17;
    private int attackDamage;
    private int turnNum;

    public Manservantes(float x, float y) {
        super(NAME, ID, HP_MAX, HB_X, HB_Y, HB_W, HB_H, "superResources/images/monsters/Manservantes.png", x, y);

        this.type = EnemyType.ELITE;

        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(A_8_HP_MIN, A_8_HP_MAX);
        } else {
            this.setHp(HP_MIN, HP_MAX);
        }

        if (AbstractDungeon.ascensionLevel >= 3) {
            this.attackDamage = ATTACK_DAMAGE;
        } else {
            this.attackDamage = ATTACK_DAMAGE;
        }

        this.damage.add(new DamageInfo(this, attackDamage));
    }

    @Override
    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("FACTORYELITE");
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 10), 10));
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                for (int i = 0; i < 3; i++) {
                    AbstractDungeon.actionManager.addToBottom(new SpawnBronzeOrbAction());
                }
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AttackEffect.SLASH_DIAGONAL));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new DexterityPower(AbstractDungeon.player, -1), -1));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new SpawnBronzeOrbAction());
                break;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num) {
        if (turnNum == 0) {
            this.setMove((byte) 1, IntentEnums.SUMMON_MINI_BOT_INTENT);
        } else if (turnNum == 1) {
            this.setMove((byte) 2, Intent.ATTACK, this.damage.get(0).base);
        } else if (turnNum == 2) {
            this.setMove((byte) 2, Intent.ATTACK, this.damage.get(0).base);
        } else if (turnNum == 3) {
            this.setMove((byte) 3, Intent.DEBUFF);
        } else if (turnNum == 4) {
            this.setMove((byte) 4, IntentEnums.SUMMON_MINI_BOT_INTENT);
        }
        turnNum++;
        if (turnNum == 5) {
            turnNum = 1;
        }
    }

    public void die() {
        super.die();
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDead && !m.isDying && m instanceof ToyOrb) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(m.hb.cX, m.hb.cY), 0.1F));
                AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(m));
                AbstractDungeon.actionManager.addToTop(new SuicideAction(m));
            }
        }
    }

}
