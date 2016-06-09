/**
 * Created by Igor1 on 13/04/2016.
 */

package rtrk.pnrs.statistics;

import android.graphics.drawable.Drawable;

public class OneItem {

    public Drawable imageCenter;
    public String textLeft;
    public String textCenter;
    public String textRight;

    public OneItem(String textLeft, String textCenter, String textRight) {
        this.textLeft = textLeft;
        this.textCenter = textCenter;
        this.textRight = textRight;
    }
}
