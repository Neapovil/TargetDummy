package com.github.neapovil.targetdummy;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import com.electronwill.nightconfig.core.file.FileConfig;

public final class TargetDummy extends JavaPlugin
{
    private static TargetDummy instance;
    private FileConfig config;
    
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
}
