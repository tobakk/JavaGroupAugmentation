package com.groupaugmentation;

import com.groupaugmentation.FishType;

public class Individual {

    public Individual() {
        this.fishType = FishType.HELPER;
    }

    private FishType fishType;


    public FishType getFishType() {
        return fishType;
    }

    public void setFishType(FishType fishType) {
        this.fishType = fishType;
    }
}
