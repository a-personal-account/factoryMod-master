package myAct.util;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import theAct.dungeons.Jungle;


public class JungleChecks {

    public static boolean ISJUNGLEFROM() {
        return (CardCrawlGame.dungeon instanceof Jungle);
    }

}
