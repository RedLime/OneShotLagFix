package com.redlimerl.oneshotfix.mixins;

import com.mojang.authlib.GameProfile;
import com.redlimerl.oneshotfix.OneShotFix;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayerEntity.class)
public abstract class MixinServerPlayerEntity extends PlayerEntity {

    @Shadow public abstract ServerWorld getServerWorld();

    public MixinServerPlayerEntity(World world, BlockPos blockPos, GameProfile gameProfile) {
        super(world, blockPos, gameProfile);
    }

    @Override
    protected Vec3d adjustMovementForSneaking(Vec3d movement, MovementType type) {
        if (OneShotFix.shouldCheckVelocityInUnloadedChunk(this.getServerWorld(), movement)) {
            return super.adjustMovementForSneaking(OneShotFix.maximumLoadedPosVelocity(this.getServerWorld(), this.getPos(), movement), type);
        }
        return super.adjustMovementForSneaking(movement, type);
    }

}
