package mc.alk.mc.inventory;

import java.util.Map;

public interface MCItemStack {

	void setType(String type);
	String getType();

	void setDataValue(short value);
	short getDataValue();

	void setQuantity(int quantity);
	int getQuantity();

	Map<String, Integer> getEnchantments();
	void addEnchantment(String ench, int level);

	boolean hasMetaData();

	String getCommonName();

	MCItemStack clone();
	int isSpecial();
}