package com.github.neapovil.targetdummy;

import java.io.File;
import java.util.Set;

import org.bukkit.plugin.java.JavaPlugin;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.electronwill.nightconfig.core.UnmodifiableConfig.Entry;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.github.neapovil.targetdummy.command.CreateCommand;
import com.github.neapovil.targetdummy.command.DeleteCommand;
import com.github.neapovil.targetdummy.command.DespawnCommand;
import com.github.neapovil.targetdummy.command.SpawnCommand;
import com.github.neapovil.targetdummy.command.TeleportCommand;
import com.github.neapovil.targetdummy.listener.Listener;

import dev.jorel.commandapi.StringTooltip;

public final class TargetDummy extends JavaPlugin
{
    private static TargetDummy instance;
    private FileConfig config;
    public static final String ADMIN_COMMAND_PERMISSION = "targetdummy.command.admin";

    @Override
    public void onEnable()
    {
        instance = this;

        this.saveResource("targetdummy.json", false);

        this.config = FileConfig.builder(new File(this.getDataFolder(), "targetdummy.json"))
                .autoreload()
                .autosave()
                .build();
        this.config.load();

        CreateCommand.register();
        SpawnCommand.register();
        DeleteCommand.register();
        DespawnCommand.register();
        TeleportCommand.register();

        this.getServer().getPluginManager().registerEvents(new Listener(), this);
    }

    @Override
    public void onDisable()
    {
    }

    public static TargetDummy getInstance()
    {
        return instance;
    }

    public FileConfig getFileConfig()
    {
        return this.config;
    }

    public Set<? extends Entry> getDummies()
    {
        final UnmodifiableConfig config = this.config.get("targetdummy");
        final Set<? extends Entry> dummies = config.entrySet();

        return dummies;
    }

    public StringTooltip[] getDummiesAsTooltip()
    {
        return this.getDummies()
                .stream()
                .map(i -> StringTooltip.of(i.getKey(), i.getValue()))
                .toArray(StringTooltip[]::new);
    }
}
