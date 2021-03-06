package sirlich.core;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import sirlich.commands.SpawnWeaponCommand;
import sirlich.commands.ToggleCommand;
import sirlich.commands.createMobCommand;
import sirlich.commands.openInvCmd;
import sirlich.config.CreatePlayerConfig;
import sirlich.handlers.ArtifactHandler;
import sirlich.handlers.PlayerJoinHandler;
import sirlich.handlers.StaminaHandler;
import sirlich.inventory.INVAction;
import sirlich.inventory.INVClickHandler;
import sirlich.inventory.INVList;
import sirlich.listeners.DoubleJumpListener;
import sirlich.listeners.ExpPickupListener;
import sirlich.listeners.FlashpaperListener;
import sirlich.listeners.LoseHungerListener;
import sirlich.listeners.PlayerAttackListener;
import sirlich.listeners.RPGInventoryListener;
import sirlich.listeners.SpellHitListener;

/**
 * This class is the "Heart of the beast". This is the main class that extends java plugin.
 * As the core, this plugin will handle things like the registration of events, and other
 * such things. 
**/
public class AsmodeusRPG extends JavaPlugin{
	private static AsmodeusRPG instance; //instance of the plugin
	
	/**
	 * This method is the basic built-in onEnable method. It will run once when the plugin is loaded.
	**/
    @Override
    public void onEnable() {
    	if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
            File dir = new File(getDataFolder().getPath() + "/players");
            System.out.println(getDataFolder().getPath());
            dir.mkdir();
            //File file = new File(getDataFolder().getPath() + "/players" + "/testfile");
            instance.saveResource(getDataFolder().getPath() + "/players" + "/testfile", true);
        }
    	instance = this;
    	registerListeners();
    	registerCommands();
    	registerButtons();
    	Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
            	StaminaHandler.run();
            }
    	}, 0, 1);
    	for(Player p : Bukkit.getOnlinePlayers()){
    		p.kickPlayer("Server reloading, please log-back on in a moment.");
    	}
    }
    
    /**
	 * This method is the basic built-in onDisable method. It will run once when the plugin is unloaded.
	**/
    @Override
    public void onDisable() {
    	instance = null;
    }
    
    /**
     * This method registers all listeners. 
    **/
    private void registerListeners(){
    	getServer().getPluginManager().registerEvents(new PlayerAttackListener(), this);
    	getServer().getPluginManager().registerEvents(new RPGInventoryListener(), this);
    	getServer().getPluginManager().registerEvents(new FlashpaperListener(), this);
    	getServer().getPluginManager().registerEvents(new ExpPickupListener(), this);
    	getServer().getPluginManager().registerEvents(new PlayerJoinHandler(), this);
    	getServer().getPluginManager().registerEvents(new LoseHungerListener(), this);
    	getServer().getPluginManager().registerEvents(new SpellHitListener(), this);
    	getServer().getPluginManager().registerEvents(new DoubleJumpListener(), this);
    	getServer().getPluginManager().registerEvents(new ArtifactHandler(), this);
    	getServer().getPluginManager().registerEvents(new CreatePlayerConfig(), this);
    	getServer().getPluginManager().registerEvents(new INVClickHandler(), this);

    }
    
    /**
     * This method registers all commands.
    **/
    private void registerCommands(){
    	this.getCommand("inv").setExecutor(new openInvCmd());
    	this.getCommand("create").setExecutor(new createMobCommand());
    	this.getCommand("spawn").setExecutor(new SpawnWeaponCommand());
    	this.getCommand("spawnMob").setExecutor(new SpawnWeaponCommand());
    	this.getCommand("toggle").setExecutor(new ToggleCommand());
    }
    
    /**
     * This method registers all buttons.
    **/
    private void registerButtons(){
    	INVList.addAction("example", new INVAction());
    }
    /**
     * This method returns an instance of the main plugin for use in other .java files.
    **/
    public static AsmodeusRPG instance(){
    	  return instance;
    }
    
}
