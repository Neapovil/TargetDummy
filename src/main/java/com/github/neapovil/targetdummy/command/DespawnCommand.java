package com.github.neapovil.targetdummy.command;

import com.github.neapovil.targetdummy.TargetDummy;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

public final class DespawnCommand
{
    private static final TargetDummy plugin = TargetDummy.getInstance();

    public static final void register()
    {
        new CommandAPICommand("targetdummy")
                .withPermission(TargetDummy.ADMIN_COMMAND_PERMISSION)
                .withArguments(new LiteralArgument("despawn"))
                .withArguments(new IntegerArgument("dummy").replaceSuggestions(ArgumentSuggestions.stringsWithTooltips(info -> {
                    return plugin.getDummiesAsTooltip();
                })))
                .executesPlayer((player, args) -> {
                    final int dummy = (int) args[0];

                    if (plugin.getFileConfig().get("targetdummy." + dummy) == null)
                    {
                        throw CommandAPI.fail("This dummy doesn't exist");
                    }

                    final NPC npc = CitizensAPI.getNPCRegistry().getById(dummy);

                    if (!npc.isSpawned())
                    {
                        throw CommandAPI.fail("This dummy is not spawned");
                    }

                    npc.despawn();

                    player.sendMessage("Dummy despawned");
                })
                .register();
    }
}
