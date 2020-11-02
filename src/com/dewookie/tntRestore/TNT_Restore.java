package com.dewookie.tntRestore;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TNT_Restore extends JavaPlugin {
  public HashMap<String, Integer> mute = new HashMap<>();
  
  public String noperm = "hast keine Berechtigung!";
  
  public String pre = "Restore";
  
  private static TNT_Restore pl;
  
  public void onEnable() {
    registerEvents();
    Bukkit.getConsoleSender().sendMessage(String.valueOf(this.pre) + "Erfolgreich Geladen");
  }
  
  public void onDisable() {
    Bukkit.getConsoleSender().sendMessage(String.valueOf(this.pre) + "Erfolgreich deaktiviert!");
    pl = null;
  }
  
  public static TNT_Restore getPlugin() {
    return pl;
  }
  
  private void registerEvents() {
    pl = this;
    PluginManager pm = Bukkit.getServer().getPluginManager();
    pm.registerEvents(new EH_onTNTPrime(this), (Plugin)this);
  }
}
