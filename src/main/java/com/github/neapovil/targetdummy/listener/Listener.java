package com.github.neapovil.targetdummy.listener;

import java.text.DecimalFormat;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public final class Listener implements org.bukkit.event.Listener
{
    @EventHandler
    public void npcDamageByEntity(net.citizensnpcs.api.event.NPCDamageByEntityEvent event)
    {
        event.setCancelled(true);

        final LivingEntity player = (LivingEntity) event.getNPC().getEntity();

        final Location location = player.getLocation();

        location.getNearbyEntitiesByType(ArmorStand.class, 20).forEach(i -> {
            i.setCustomNameVisible(false);
            i.setHealth(0);
        });

        final ArmorStand armorstand = (ArmorStand) player.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

        armorstand.setInvisible(true);
        armorstand.customName(Component.text(new DecimalFormat("0.00").format(event.getDamage()), NamedTextColor.RED));
        armorstand.setCustomNameVisible(true);
    }

    @EventHandler
    public void npcDeath(net.citizensnpcs.api.event.NPCDeathEvent event)
    {
        event.getEvent().setCancelled(true);
    }
}
