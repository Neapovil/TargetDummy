package com.github.neapovil.targetdummy.command;

import com.github.neapovil.targetdummy.TargetDummy;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

public final class SpawnCommand
{
    private static TargetDummy plugin = TargetDummy.getInstance();

    public static final void register()
    {
        new CommandAPICommand("targetdummy")
                .withPermission(TargetDummy.ADMIN_COMMAND_PERMISSION)
                .withArguments(new LiteralArgument("spawn"))
                .withArguments(new IntegerArgument("dummy").replaceSuggestionsT(info -> {
                    return plugin.getDummiesAsTooltip();
                }))
                .executesPlayer((player, args) -> {
                    final int dummy = (int) args[0];

                    if (plugin.getFileConfig().get("targetdummy." + dummy) == null)
                    {
                        CommandAPI.fail("This dummy doesn't exist");
                    }

                    final NPC npc = CitizensAPI.getNPCRegistry().getById(dummy);

                    if (npc.isSpawned())
                    {
                        CommandAPI.fail("This dummy is already spawned");
                    }

                    npc.spawn(player.getLocation().toCenterLocation());

                    player.sendMessage("Dummy spawned");
                })
                .register();
    }
}
