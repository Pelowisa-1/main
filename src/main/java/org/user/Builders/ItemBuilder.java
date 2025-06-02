package org.user.Builders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.user.Utils.Utils;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Consumable;

public class ItemBuilder {
    protected ItemStack is;
    protected ItemMeta im;
    protected LeatherArmorMeta leather;

    public ItemBuilder() {
        this(Material.AIR);
    }

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(Material material, int amount) {
        this(new ItemStack(material, amount));
    }

    public ItemBuilder(ItemStack itemStack) {
        this.is = itemStack;
    }

    public ItemBuilder setAmount(int amount) {
        this.is.setAmount(amount);
        return this;
    }

    public ItemBuilder setArrowPotion(PotionEffectType potionEffect, int duration, int amplifier) {
        PotionMeta meta = (PotionMeta) this.is.getItemMeta();
        meta.addCustomEffect(new PotionEffect(potionEffect, duration, amplifier), true);
        this.is.setItemMeta((ItemMeta) meta);
        return this;
    }

    public ItemBuilder setTemplate(TrimPattern pattern, TrimMaterial material) {
        ArmorMeta m = (ArmorMeta) this.is.getItemMeta();
        if (pattern != null) {
            m.setTrim(new ArmorTrim(material, pattern));
            this.is.setItemMeta(m);
        }

        return this;
    }

    public ItemBuilder setSize(int size) {
        ItemMeta m = this.is.getItemMeta();
        if (m != null) {
            m.setMaxStackSize(size);
            this.is.setItemMeta(m);
        }

        return this;
    }

    public ItemBuilder setConsumable(boolean consumable, float eatTime) {
        ItemMeta m = this.is.getItemMeta();
        if (m != null) {
            if (consumable) {
                FoodComponent food = m.getFood();
                food.canAlwaysEat();
                food.setSaturation(1);
                m.setFood(food);

                Consumable c2 = Consumable.consumable()
                        .consumeSeconds(eatTime)
                        .build();

                this.is.setData(DataComponentTypes.CONSUMABLE, c2);
            }
        }
        return this;
    }

    public ItemBuilder addBookEnchant(Enchantment enchantment, int level) {
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) this.is.getItemMeta();
        meta.addStoredEnchant(enchantment, level, true);
        this.is.setItemMeta((ItemMeta) meta);
        return this;
    }

    public ItemBuilder setPlayerSkullOwner(String owner) {
        OfflinePlayer target = Bukkit.getOfflinePlayer((String) owner);
        SkullMeta meta = (SkullMeta) this.is.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer((UUID) target.getUniqueId()));
        this.is.setItemMeta((ItemMeta) meta);
        return this;
    }

    public ItemBuilder addCustomPotionEffect(PotionEffectType potionType, int duration, int level,
            boolean hasExtendedDuration) {
        PotionMeta meta = (PotionMeta) this.is.getItemMeta();
        meta.addCustomEffect(new PotionEffect(potionType, duration * 20, level, false, hasExtendedDuration), true);
        this.is.setItemMeta((ItemMeta) meta);
        return this;
    }

    public ItemBuilder setPotionColorRGB(int R, int G, int B) {
        PotionMeta meta = (PotionMeta) this.is.getItemMeta();
        meta.setColor(Color.fromRGB((int) R, (int) G, (int) B));
        this.is.setItemMeta((ItemMeta) meta);
        return this;
    }

    public ItemBuilder setCustomModelData(int data) {
        this.im = this.is.getItemMeta();
        this.im.setCustomModelData(Integer.valueOf(data));
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean b) {
        this.im = this.is.getItemMeta();
        this.im.setUnbreakable(b);
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder setDisplayName(String name) {
        this.im = this.is.getItemMeta();
        this.im.setDisplayName(Utils.c(name));
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        this.im = this.is.getItemMeta();
        if (this.im instanceof EnchantmentStorageMeta) {
            ((EnchantmentStorageMeta) this.im).addStoredEnchant(enchantment, level, true);
        } else {
            this.im.addEnchant(enchantment, level, true);
        }
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder addEnchants(Map<Enchantment, Integer> enchantments) {
        this.im = this.is.getItemMeta();
        if (!enchantments.isEmpty()) {
            for (Enchantment ench : enchantments.keySet()) {
                this.im.addEnchant(ench, enchantments.get(ench).intValue(), true);
            }
        }
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... itemflag) {
        this.im = this.is.getItemMeta();
        this.im.addItemFlags(itemflag);
        this.is.setItemMeta(this.im);
        return this;
    }

    public static List<String> buildLore(String... lore) {
        ArrayList<String> loreList = new ArrayList<String>();
        for (String s : lore) {
            loreList.add(Utils.c(s));
        }
        return loreList;
    }

    public ItemBuilder setListLore(List<String> lore) {
        this.im = this.is.getItemMeta();
        this.im.setLore(lore);
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        this.im = this.is.getItemMeta();
        ArrayList<String> finalLore = new ArrayList<String>();
        for (String s : lore) {
            finalLore.add(Utils.c(s));
        }
        this.im.setLore(finalLore);
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder addAttributeModifier(Attribute attribute, AttributeModifier attributeModifier) {
        this.im = this.is.getItemMeta();
        this.im.addAttributeModifier(attribute, attributeModifier);
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder addAttributeModifier(Attribute attribute, double value, AttributeModifier.Operation operation,
            EquipmentSlot slot) {
        this.im = this.is.getItemMeta();
        NamespacedKey key = new NamespacedKey("template",
                "attribute_modifier_" + attribute.getKey().getKey() + "_" + UUID.randomUUID().toString());
        AttributeModifier modifier = new AttributeModifier(key, value, operation);
        this.im.addAttributeModifier(attribute, modifier);
        this.is.setItemMeta(this.im);
        return this;
    }

    public ItemBuilder setLeatherColor(int red, int green, int blue) {
        this.leather = (LeatherArmorMeta) this.is.getItemMeta();
        this.leather.setColor(Color.fromRGB((int) red, (int) green, (int) blue));
        this.is.setItemMeta((ItemMeta) this.leather);
        return this;
    }

    public ItemStack build() {
        return this.is;
    }
}
