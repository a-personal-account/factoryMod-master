package myAct.actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class MoveCreatureAction extends AbstractGameAction {
    AbstractCreature ac;

    float startX;
    float startY;
    float endX;
    float endY;

    float cur;
    float speed;

    Mode mode;

    public MoveCreatureAction(Mode mode, AbstractCreature ac, float x, float y, float speed) {
        this.ac = ac;
        this.startX = ac.drawX;
        this.startY = ac.drawY;
        this.endX = x;
        this.endY = y;
        this.mode = mode;

        switch (this.mode) {
            case Sigmoid:
                this.speed = speed / 2;
                this.cur = -this.speed;
                break;

            default:
                this.speed = speed;
                this.cur = 0;
        }
    }

    public MoveCreatureAction(Mode mode, AbstractCreature ac, float x, float y) {
        this(mode, ac, x, y, 2F);
    }

    public MoveCreatureAction(AbstractCreature ac, float x, float y, float speed) {
        this(Mode.Sigmoid, ac, x, y, speed);
    }

    public MoveCreatureAction(AbstractCreature ac, float x, float y) {
        this(Mode.Sigmoid, ac, x, y);
    }

    public void update() {
        cur += Gdx.graphics.getDeltaTime();

        if (cur >= speed) {
            ac.drawX = endX;
            ac.drawY = endY;
            this.isDone = true;
            return;
        }

        switch (mode) {
            case Sigmoid:
                ac.drawX = startX + this.sigmoid(endX - startX, speed, cur);
                ac.drawY = startY + this.sigmoid(endY - startY, speed, cur);
                break;
            case Parabolic:
                ac.drawX = startX + (endX - startX) * (1 - (float) Math.pow((speed - cur) / speed, 2));
                ac.drawY = startY + (endY - startY) * (1 - (float) Math.pow((speed - cur) / speed, 2));
                break;
            case Linear:
                ac.drawX = startX + (endX - startX) * ((speed - cur) / speed);
                ac.drawY = startY + (endY - startY) * ((speed - cur) / speed);
                break;
        }
    }


    private float sigmoid(float endvalue, float steepness, float curval) {
        return endvalue / (1 + (float) Math.pow(Math.E, -(6F / steepness) * curval));
    }

    public enum Mode {
        Linear,
        Parabolic,
        Sigmoid
    }
}