package mc.alk.mc;

import mc.alk.mc.command.MCCommandSender;
import mc.alk.mc.inventory.MCInventory;

public interface MCPlayer extends MCCommandSender {

	MCWorld getWorld();

	String getName();
	String getDiplayName();

	MCInventory getInventory();
	void updateInventory();
}
