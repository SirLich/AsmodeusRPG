package sirlich.listeners;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.tr7zw.itemnbtapi.NBTItem;
import net.md_5.bungee.api.ChatColor;

/**
 * This somewhat temp Listener handles the event where a player hits a mob.
 * Currently used to set damagage of custom weps and handle error message.
 * 
 * Right now, I have added loads of things. Not everything will happen on each weapon click.
 * Instead, some effects should get applied to some weapons so as to make
 * each one more custom.
**/
public class PlayerAttackListener implements Listener{
	@SuppressWarnings("unused")
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e){
		if(e instanceof EntityDamageByEntityEvent){
			EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) e;
			if(e.getEntity() instanceof Player){
				Player p = (Player) event.getEntity();
				if(p.getHealth() < 10){
					p.getInventory().setHelmet(new ItemStack(Material.PUMPKIN));
				}
			}
			/**
			 * All code should go in here. This makes sure the damage event stemmed from a player.
			**/
            if(event.getDamager() instanceof Player){
            	Player player = (Player) event.getDamager();
            	NBTItem item = new NBTItem(player.getInventory().getItemInMainHand());
            	
            	/**
            	 * Check if the item is a Weapon. 
            	**/
            	if(item.hasKey("damage")){
            		if(!item.getBoolean("broken") || true){
	            		//sets damage -> This is the RAW damage. It sets the damage. Ignoring armour... potions... sword swing.
	            		//This is good. This means we can implement our own damage system.
	            		event.setDamage((double)item.getInteger("damage"));
	            		e.getEntity().setInvulnerable(false);
	            		//Code that "Ghost weps" should have. Sets entity to invisi and outlines them for 0.1 seconds.
	            		e.getEntity().setGlowing(true);
	            		final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
	            		Runnable task = new Runnable() {
	            	        public void run() {
	            	            e.getEntity().setGlowing(false);
	            	        }
	            	    };
	            	    worker.schedule(task, 200, TimeUnit.MILLISECONDS);  
	            		((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 5, 1));
	            		
	            		//handle weps getting damaged.
	            		item.setInteger("durability", item.getInteger("durability") - 1);
	            		player.sendMessage("durability set to " + item.getInteger("durability"));
	            		
	            		
	            		player.getInventory().setItemInMainHand(item.getItem());
	            		
	            		if(item.getInteger("durability") < 0){
	                		item.setInteger("durability", item.getInteger("maxDurability"));
	                		ItemStack item2 = item.getItem();
	                		item2.setDurability((short) ((short) item2.getDurability() + 1));
	                		player.sendMessage("Dure set to: " + item2.getDurability());
	                		if(item2.getDurability() > 5){
		            			item.setBoolean("broken", true);
		            			item2 = item.getItem();
		            		}
	                		player.getInventory().setItemInMainHand(item2);
	            		}
	            	}
            		else{
            			event.setCancelled(true);
            			player.sendMessage(ChatColor.RED + "That weapon is broken!");
            		}
            	}
            }
		}
	}
}

//The line belows this might be usfull later
//String[] weaponList = {"DIAMOND_SWORD","DIAMOND_AXE","DIAMOND_SHOVEL"};
//boolean isWep = Arrays.asList(weaponList).contains(player.getInventory().getItemInMainHand().getType().name());
            
