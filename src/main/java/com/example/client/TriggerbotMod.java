package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

import java.util.Random;

public class TriggerbotMod implements ClientModInitializer {
    private int delay = 0;
    private final Random random = new Random();

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;

            if (delay > 0) {
                delay--;
                return;
            }

            HitResult hit = client.crosshairTarget;
            if (hit != null && hit.getType() == HitResult.Type.ENTITY) {
                EntityHitResult entityHit = (EntityHitResult) hit;
                
                if (client.interactionManager != null) {
                    client.interactionManager.attackEntity(client.player, entityHit.getEntity());
                    client.player.swingHand(client.player.getActiveHand());
                    
                    delay = 10 + random.nextInt(11);
                }
            }
        });
    }
}
}
}
