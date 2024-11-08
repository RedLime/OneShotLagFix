package com.redlimerl.oneshotfix.mixins;

import net.minecraft.entity.boss.dragon.EnderDragonFight;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EnderDragonFight.class)
public interface EnderDragonFightAccessor {

    @Accessor("dragonKilled")
    boolean hasDragonKilled();

}
