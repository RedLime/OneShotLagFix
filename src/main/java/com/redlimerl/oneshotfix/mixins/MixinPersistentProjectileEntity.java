package com.redlimerl.oneshotfix.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PersistentProjectileEntity.class)
public abstract class MixinPersistentProjectileEntity extends Entity {

    public MixinPersistentProjectileEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;add(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;"))
    public Vec3d wrapVelocity(Vec3d instance, Vec3d vec, Operation<Vec3d> original) {
        double v = new Vec3d(vec.getX(), 0, vec.getZ()).length();
        if (!this.world.isClient() && v > 16) return original.call(instance, vec.multiply(16 / v));
        return original.call(instance, vec);
    }

}
