package com.redlimerl.oneshotfix;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public class OneShotFix {
    public static boolean shouldCheckVelocityInUnloadedChunk(ServerWorld world, Vec3d vec3d) {
        return world.getEnderDragonFight() != null && !world.getEnderDragonFight().hasPreviouslyKilled() && new Vec3d(vec3d.getX(), 0, vec3d.getZ()).length() > 16;
    }

    public static Vec3d maximumLoadedPosVelocity(ServerWorld world, Vec3d pos, Vec3d velocity) {
        int viewDistance = world.getServer().getPlayerManager().getViewDistance() - 1;
        for (ServerPlayerEntity player : world.getPlayers()) {
            ChunkPos centerPos = new ChunkPos(player.getBlockPos());
            ChunkPos minPos = new ChunkPos(centerPos.x - viewDistance, centerPos.z - viewDistance);
            ChunkPos maxPos = new ChunkPos(centerPos.x + viewDistance, centerPos.z + viewDistance);

            Box chunkBox = new Box(minPos.getStartX() + 0.5, 0, minPos.getStartZ() + 0.5, maxPos.getEndX() + 0.5, 256, maxPos.getEndZ() + 0.5);
            Optional<Vec3d> rayTrace = chunkBox.rayTrace(pos.add(velocity), pos);
            if (rayTrace.isPresent()) return rayTrace.get().subtract(pos);
        }
        return velocity;
    }
}
