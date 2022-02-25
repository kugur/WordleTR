package com.kolip.wordletr.store;


public class Achievements {

    final static String BOX_ENABLE_PREFIX = "BoxEnable";
    final static int ENABLE_THRESHOLD = 10;

    private boolean sixBoxEnable;
    private boolean fourBoxEnable;
    private boolean sevenBoxEnable;
    private boolean eightBoxEnable;
    private boolean nineBoxEnable;
    private boolean tenBoxEnable;

    public boolean isTenBoxEnable() {
        return tenBoxEnable;
    }

    public void setTenBoxEnable(boolean tenBoxEnable) {
        this.tenBoxEnable = tenBoxEnable;
    }

    public boolean isSixBoxEnable() {
        return sixBoxEnable;
    }

    public void setSixBoxEnable(boolean sixBoxEnable) {
        this.sixBoxEnable = sixBoxEnable;
    }

    public boolean isFourBoxEnable() {
        return fourBoxEnable;
    }

    public void setFourBoxEnable(boolean fourBoxEnable) {
        this.fourBoxEnable = fourBoxEnable;
    }

    public boolean isSevenBoxEnable() {
        return sevenBoxEnable;
    }

    public void setSevenBoxEnable(boolean sevenBoxEnable) {
        this.sevenBoxEnable = sevenBoxEnable;
    }

    public boolean isEightBoxEnable() {
        return eightBoxEnable;
    }

    public void setEightBoxEnable(boolean eightBoxEnable) {
        this.eightBoxEnable = eightBoxEnable;
    }

    public boolean isNineBoxEnable() {
        return nineBoxEnable;
    }

    public void setNineBoxEnable(boolean nineBoxEnable) {
        this.nineBoxEnable = nineBoxEnable;
    }
}
