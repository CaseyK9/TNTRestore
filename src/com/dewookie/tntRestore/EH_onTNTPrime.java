package com.dewookie.tntRestore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;

public class EH_onTNTPrime implements Listener {
  private TNT_Restore plugin;
  
  int count;
  
  Boolean tntsched;
  
  List<Material> material;
  
  List<Location> locations;
  
  List<BlockData> data;
  
  public EH_onTNTPrime(TNT_Restore wpl) {
    this.count = 0;
    this.tntsched = Boolean.valueOf(false);
    this.material = new ArrayList<>();
    this.locations = new ArrayList<>();
    this.data = new ArrayList<>();
    this.plugin = wpl;
  }
  
@EventHandler
  public void onTNTPrime(EntityExplodeEvent e) {
    Entity en = e.getEntity();
    if (en instanceof org.bukkit.entity.TNTPrimed || 
      en instanceof org.bukkit.entity.Minecart || 
      en instanceof org.bukkit.entity.Fireball || 
      en instanceof org.bukkit.entity.Creeper) {
      if (e.isCancelled())
        return; 
      Iterator<Block> blocki = e.blockList().iterator();
      while (blocki.hasNext()) {
        if (((Block)blocki.next()).getType().equals(Material.TNT))
          blocki.remove(); 
      } 
      List<Block> blocklist = e.blockList();
      int i = 0;
      for (i = 0; i < blocklist.size(); i++) {
        this.material.add(((Block)blocklist.get(i)).getType());
        this.locations.add(((Block)blocklist.get(i)).getLocation());
        this.data.add(((Block)blocklist.get(i)).getBlockData());
      } 
      final int[] ID = new int[1];
      ID[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable() {
            public void run() {
              if (EH_onTNTPrime.this.material.size() > EH_onTNTPrime.this.count) {
                ((Location)EH_onTNTPrime.this.locations.get(EH_onTNTPrime.this.count)).getBlock().setType(EH_onTNTPrime.this.material.get(EH_onTNTPrime.this.count));
                ((Location)EH_onTNTPrime.this.locations.get(EH_onTNTPrime.this.count)).getBlock().setBlockData((EH_onTNTPrime.this.data.get(EH_onTNTPrime.this.count)));
                Location l = EH_onTNTPrime.this.locations.get(EH_onTNTPrime.this.count);
                l.getWorld().playEffect(l, Effect.MOBSPAWNER_FLAMES, 4, 50);
                EH_onTNTPrime.this.count++;
                if (EH_onTNTPrime.this.material.size() > EH_onTNTPrime.this.count) {
                  ((Location)EH_onTNTPrime.this.locations.get(EH_onTNTPrime.this.count)).getBlock().setType(EH_onTNTPrime.this.material.get(EH_onTNTPrime.this.count));
                  ((Location)EH_onTNTPrime.this.locations.get(EH_onTNTPrime.this.count)).getBlock().setBlockData((EH_onTNTPrime.this.data.get(EH_onTNTPrime.this.count)));
                  Location l1 = EH_onTNTPrime.this.locations.get(EH_onTNTPrime.this.count);
                  l1.getWorld().playEffect(l1, Effect.MOBSPAWNER_FLAMES, 4, 50);
                  EH_onTNTPrime.this.count++;
                } 
              } else {
                Bukkit.getScheduler().cancelTask(ID[0]);
              } 
            }
          }, 0, 0);
    } 
  }
}