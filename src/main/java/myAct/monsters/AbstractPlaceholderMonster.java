package myAct.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractPlaceholderMonster extends AbstractMonster {

    int red = 0;
    int blue = 0;
    int green = 0;
    public Color myColor;

    public AbstractPlaceholderMonster(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
        byte[] nameBytes = name.getBytes();
        {
            for (int i = 0; i < nameBytes.length; i++) {
                switch (i % 3) {
                    case 0:
                        red += nameBytes[i];
                        break;
                    case 1:
                        blue += nameBytes[i];
                        break;
                    case 2:
                        green += nameBytes[i];
                        break;
                }
            }
            red %= 256;
            blue %= 256;
            green %= 256;
            myColor = new Color(red / 255F, green / 255F, blue / 255F, 1F);
        }
    }
}
