package com.github.neapovil.targetdummy.listener;

import java.text.DecimalFormat;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;

import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.event.NPCDespawnEvent;
import net.citizensnpcs.api.event.NPCTeleportEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public final class Listener implements org.bukkit.event.Listener
{
    @EventHandler
    public void npcDamageByEntity(NPCDamageByEntityEvent event)
    {
        final LivingEntity player = (LivingEntity) event.getNPC().getEntity();

        final Location location = player.getLocation();

        this.clearArmorStands(location);

        final ArmorStand armorstand = (ArmorStand) player.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

        armorstand.setInvisible(true);
        armorstand.customName(Component.text(new DecimalFormat("0.00").format(event.getDamage()), NamedTextColor.RED));
        armorstand.setCustomNameVisible(true);
    }

    @EventHandler
    public void npcDeath(NPCDeathEvent event)
    {
        event.getEvent().setCancelled(true);
    }

    @EventHandler
    public void npcTeleport(NPCTeleportEvent event)
    {
        this.clearArmorStands(event.getNPC().getStoredLocation());
    }

    @EventHandler
    public void npcDespawn(NPCDespawnEvent event)
    {
        this.clearArmorStands(event.getNPC().getStoredLocation());
    }

    private final void clearArmorStands(Location location)
    {
        location.getNearbyEntitiesByType(ArmorStand.class, 5).forEach(i -> {
            i.setCustomNameVisible(false);
            i.setHealth(0);
        });
    }
}
