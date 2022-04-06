package com.github.neapovil.targetdummy.command;

import org.bukkit.entity.EntityType;

import com.github.neapovil.targetdummy.TargetDummy;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPC.Metadata;

public final class CreateCommand
{
    private static final TargetDummy plugin = TargetDummy.getInstance();

    public static final void register()
    {
        new CommandAPICommand("targetdummy")
                .withPermission(TargetDummy.ADMIN_COMMAND_PERMISSION)
                .withArguments(new LiteralArgument("create"))
                .withArguments(new StringArgument("displayName"))
                .executesPlayer((player, args) -> {
                    final String displayname = (String) args[0];

                    final NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, displayname);

                    npc.data().setPersistent(Metadata.DAMAGE_OTHERS, false);
                    npc.data().setPersistent(Metadata.DEFAULT_PROTECTED, true);
                    npc.data().setPersistent(Metadata.DROPS_ITEMS, false);
                    npc.data().setPersistent(Metadata.SHOULD_SAVE, true);
                    npc.data().setPersistent(Metadata.TARGETABLE, false);
                    npc.data().setPersistent(Metadata.COLLIDABLE, false);

                    plugin.getFileConfig().set("targetdummy." + npc.getId(), displayname);

                    player.sendMessage("Dummy created");
                })
                .register();
    }
}
