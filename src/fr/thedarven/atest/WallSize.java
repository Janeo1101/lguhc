package fr.thedarven.atest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.thedarven.configuration.builders.InventoryGUI;
import fr.thedarven.configuration.builders.OptionNumeric;
import fr.thedarven.main.PlayerLG;

public class WallSize extends OptionNumeric {
	
	public WallSize(String pName, String pDescription, Material pItem, InventoryGUI pParent, int pPosition, int pMin, int pMax, int pValue, int pPas, int pMorePas, String pAfterName, int pDiviseur) {
		super(pName, pDescription, pItem, pParent, pPosition, pMin, pMax, pValue, pPas, pMorePas, pAfterName, pDiviseur);
	}
	
	public WallSize(String pName, String pDescription, Material pItem, InventoryGUI pParent, int pMin, int pMax, int pValue, int pPas, int pMorePas, String pAfterName, int pDiviseur) {
		super(pName, pDescription, pItem, pParent, pMin, pMax, pValue, pPas, pMorePas, pAfterName, pDiviseur);
	}
	
	@EventHandler
	public void clickInventory(InventoryClickEvent e){
		int operation = 0;
		int number = 0;
		if(e.getWhoClicked() instanceof Player && e.getClickedInventory() != null && e.getClickedInventory().equals(this.inventory)) {
			Player p = (Player) e.getWhoClicked();
			PlayerLG pl = PlayerLG.getPlayerManager(p.getUniqueId());
			e.setCancelled(true);	
			if(e.getCurrentItem().getType().equals(Material.REDSTONE) && e.getRawSlot() == this.getLines()*9-1 && e.getCurrentItem().getItemMeta().getDisplayName().equals("�cRetour")){
				p.openInventory(this.getParent().getInventory());
				return;
			}

			if(p.isOp() && !e.getCurrentItem().getType().equals(Material.AIR) && pl.getCanClick()) {
				if(e.getSlot() == 1 && this.morePas > 2) {
					operation = 1;
					number = this.pas*100;
				}else if(e.getSlot() == 2 && this.morePas > 1) {
					operation = 1;
					number = this.pas*10;
				}else if(e.getSlot() == 3) {
					operation = 1;
					number = this.pas;
				}else if(e.getSlot() == 5) {
					operation = 2;
					number = this.pas;
				}else if(e.getSlot() == 6 && this.morePas > 1) {
					operation = 2;
					number = this.pas*10;
				}else if(e.getSlot() == 7 && this.morePas > 2) {
					operation = 2;
					number = this.pas*100;
				}
				
				if(operation == 1) {
					if(this.min < this.value-number) {
						this.value = this.value-number;
					}else {
						this.value = this.min;
					}
					reloadItem();
				}else if(operation == 2) {
					if(this.max > this.value+number) {
						this.value = this.value+number;
					}else {
						this.value = this.max;
					}
					reloadItem();
				}
				
				Bukkit.getWorld("lguhc").getWorldBorder().setCenter(0, 0);
	    		Bukkit.getWorld("lguhc").getWorldBorder().setSize(value*2);
				
				delayClick(pl);
			}
		}
	}
	
}
