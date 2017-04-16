package sirlich.chat;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class ChatUtils {
	public static void chatInfo(Player p, String m){
		p.sendMessage(ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "!" + ChatColor.DARK_GREEN + "] " + ChatColor.WHITE + m);
	}
	public static void chatWarning(Player p, String m){
		p.sendMessage(ChatColor.GOLD + "[" + ChatColor.YELLOW + "!" + ChatColor.GOLD + "] " + ChatColor.WHITE + m);
	}
	public static void chatError(Player p, String m){
		p.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "!" + ChatColor.DARK_RED + "] " + ChatColor.WHITE + m);
	}
}
