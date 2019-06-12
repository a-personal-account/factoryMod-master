package myAct.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.Instanceof;
import myAct.MyAct;


@SpirePatch(clz = com.megacrit.cardcrawl.rooms.AbstractRoom.class, method = "update")
public class RoomRewardsBossPatch
{
    static int q = 0;

    public static ExprEditor Instrument() {
        return new ExprEditor()
        {
            public void edit(Instanceof i) throws CannotCompileException {
                try {
                    if (i.getType().getName().equals(com.megacrit.cardcrawl.dungeons.TheBeyond.class.getName())) {
                        RoomRewardsBossPatch.q++;
                        if (RoomRewardsBossPatch.q == 3) {
                            i.replace("$_ = false;");
                        }
                    }
                } catch (NotFoundException e) {
                    MyAct.logger.error("Room rewards boss patch broken.", e);
                }
            }
        };
    }
}
