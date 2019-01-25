package fr.thedarven.roles;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.thedarven.game.enums.EnumGame;
import fr.thedarven.game.enums.EnumTime;
import fr.thedarven.main.LGUHC;
import fr.thedarven.main.PlayerLG;

public class Voyante extends RolesBis<Boolean>{
	
	public Voyante() {
		this.active = true;
		this.infecte = false;
		this.kit = true;
		this.name = "Voyante";
		this.pouvoir = true;
		this.taupelist = false;
		this.maxhealth = 20.0;
		
		this.effect = new ArrayList<EffetClass>();
		effect.add(new EffetClass(EnumTime.NIGHT,new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 40000, 0, true, false),false,true));
		effect.add(new EffetClass(EnumTime.NIGHT,new PotionEffect(PotionEffectType.NIGHT_VISION, 40000, 0, true, false),false,true));
		effect.add(new EffetClass(EnumTime.NIGHT,new PotionEffect(PotionEffectType.NIGHT_VISION, 40000, 0, true, false),true,false));
	}

	@Override
	public void messageRole(PlayerLG pl) {
		if(pl.isOnline()){
			pl.getPlayer().sendMessage("�6[LGUHC] �9Tu es la Voyante, ton but est de gagner avec le village. Tu poss�des un kit ainsi que l�effet �bNight vision chaque nuit�9. Tu peux aussi, � l�aube, espionner le r�le d�un joueur � l�aide de la commande �b/lg look <player>�9.");
			super.messageRole(pl);
		}
	}
	
	@Override
	public void startRole(PlayerLG pl) {
		if(LGUHC.etat.equals(EnumGame.MIDDLEGAME) && pl.isOnline() && kit){	
			messageRole(pl);
			ItemStack bookshelf = new ItemStack(Material.BOOKSHELF, 4);
			ItemStack obsidian = new ItemStack(Material.OBSIDIAN, 4);
			Bukkit.getWorld("lguhc").dropItemNaturally(pl.getPlayer().getLocation(), bookshelf);
			Bukkit.getWorld("lguhc").dropItemNaturally(pl.getPlayer().getLocation(), obsidian);
			kit = false;
		}
	}

	public void verifRole(PlayerLG pl) {
		super.verifRole(pl);
		if(LGUHC.etat.equals(EnumGame.MIDDLEGAME) && LGUHC.timer%1200 == 0){
			if(pl.isOnline() && pl.isAlive() && active){
				pouvoir = true;
				pl.getPlayer().sendMessage("�6[LGUHC]�e Tu as 5 minutes pour espionner quelqu'un � l'aide de la commande /lg look <pseudo>.");
			}
		}
	}

	@Override
	public void endRole(PlayerLG pl) {	}
	
	@Override
	public boolean verifCommand(PlayerLG pl) {
		if(LGUHC.etat.equals(EnumGame.STARTGAME) || LGUHC.timer%1200 > 300 || !pl.isAlive() || !pouvoir || !active){
			return false;
		}
		return true;
	}
}
