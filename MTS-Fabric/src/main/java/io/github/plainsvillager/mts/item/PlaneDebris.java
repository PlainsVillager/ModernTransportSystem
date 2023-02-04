package io.github.plainsvillager.mts.item;

import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class PlaneDebris extends Item {
    public PlaneDebris() {
        super(new Settings().maxCount(24).rarity(Rarity.UNCOMMON));
    }
}
