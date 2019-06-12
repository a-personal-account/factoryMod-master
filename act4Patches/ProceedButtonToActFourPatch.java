package myAct.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.Instanceof;
import myAct.MyAct;



@SpirePatch(clz = com.megacrit.cardcrawl.ui.buttons.ProceedButton.class, method = "update")
public class ProceedButtonToActFourPatch
{
    static int q = 0;

    public static ExprEditor Instrument() {
        return new ExprEditor()
        {
            public void edit(Instanceof i) throws CannotCompileException {
                try {
                    if (i.getType().getName().equals(com.megacrit.cardcrawl.rooms.MonsterRoomBoss.class.getName()) &&
                            ProceedButtonToActFourPatch.q == 0) {
                        i.replace("$_ = false;");
                        ProceedButtonToActFourPatch.q = 1;
                    }

                } catch (NotFoundException e) {
                    MyAct.logger.error("Act 3 Proceed Button patch borken.", e);
                }
            }
        };
    }
}
