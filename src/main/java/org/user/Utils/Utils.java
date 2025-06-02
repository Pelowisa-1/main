package org.user.Utils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

import net.md_5.bungee.api.ChatColor;

public class Utils {

    public static void console(String msg) {
        System.out.println(c(msg));
    }

    public static String ib(java.awt.Color Colora, java.awt.Color Colorb, String StringToGradient) {
        net.md_5.bungee.api.ChatColor Color2 = net.md_5.bungee.api.ChatColor.of((java.awt.Color) Colorb);
        net.md_5.bungee.api.ChatColor Color1 = ChatColor.of(Colora);
        int r = Color1.getColor().getRed();
        int g = Color1.getColor().getGreen();
        int b = Color1.getColor().getBlue();
        int r2 = Color2.getColor().getRed();
        int g2 = Color2.getColor().getGreen();
        int b2 = Color2.getColor().getBlue();
        String Hex = String.format("%02X%02X%02X", r, g, b);
        String Hex2 = String.format("%02X%02X%02X", r2, g2, b2);
        return IridiumColorAPI.process("<GRADIENT:" + Hex + ">" + StringToGradient + "</GRADIENT:" + Hex2 + ">");
    }

    public static String c(String s) {
        return s.replace("&", "§");
    }

    public static String stripColor(String msg) {
        return ChatColor.stripColor(msg);
    }

    public static int genRandom(int numMin, int numMax) {
        return (int) Math.floor(Math.random() * (numMax - numMin + 1) + numMin);
    }

    public static String getCustomCause(EntityDamageEvent e, String chatColor) {
        String reasonDeath = "Desconocido";
        switch (e.getCause()) {
            case FALL, FALLING_BLOCK -> reasonDeath = "Caída";
            case FIRE, FIRE_TICK, CAMPFIRE -> reasonDeath = "Fuego";
            case LAVA -> reasonDeath = "Lava";
            case DROWNING -> reasonDeath = "Ahogamiento";
            case BLOCK_EXPLOSION -> reasonDeath = "Explosion";
            case VOID -> reasonDeath = "Vacío";
            case LIGHTNING -> reasonDeath = "Rayo";
            case POISON -> reasonDeath = "Veneno";
            case MAGIC -> reasonDeath = "Magia";
            case WITHER -> reasonDeath = "Wither";
            case THORNS -> reasonDeath = "Espinas";
            case DRAGON_BREATH -> reasonDeath = "Aliento de dragón";
            case CONTACT -> reasonDeath = "Contacto";
            case CRAMMING -> reasonDeath = "Entity Cramming";
            case HOT_FLOOR -> reasonDeath = "Piso en llamas";
            case DRYOUT -> reasonDeath = "Secado";
            case STARVATION -> reasonDeath = "Hambre";
            case SUFFOCATION -> reasonDeath = "Asfixia";
            case FLY_INTO_WALL -> reasonDeath = "Colisión";
            case FREEZE -> reasonDeath = "Congelación";
            case ENTITY_ATTACK, ENTITY_SWEEP_ATTACK -> {
                if (e instanceof EntityDamageByEntityEvent) {
                    reasonDeath = Utils.c(chatColor + "Entidad ["
                            + ((EntityDamageByEntityEvent) e).getDamager().getName() + chatColor + "]");
                }
            }
            case PROJECTILE -> {
                if (e instanceof EntityDamageByEntityEvent) {
                    Projectile proj = (Projectile) ((EntityDamageByEntityEvent) e).getDamager();
                    if (proj.getShooter() != null) {
                        Entity shooter = (Entity) proj.getShooter();
                        reasonDeath = Utils.c(chatColor + "Proyectil [" + shooter.getName() + chatColor + "]");
                    } else {
                        reasonDeath = Utils.c(chatColor + "Proyectil ["
                                + ((EntityDamageByEntityEvent) e).getDamager().getName() + chatColor + "]");
                    }
                }
            }
            case SONIC_BOOM -> {
                if (e instanceof EntityDamageByEntityEvent) {
                    reasonDeath = Utils.c(chatColor + "Sonic Boom ["
                            + ((EntityDamageByEntityEvent) e).getDamager().getName() + chatColor + "]");
                }
            }
            case ENTITY_EXPLOSION -> {
                if (e instanceof EntityDamageByEntityEvent) {
                    if (((EntityDamageByEntityEvent) e).getDamager() instanceof Fireball ball
                            && ((Fireball) ((EntityDamageByEntityEvent) e).getDamager()).getShooter() != null) {
                        LivingEntity et = (LivingEntity) ball.getShooter();
                        reasonDeath = Utils.c(chatColor + "Explosión [" + et.getName() + chatColor + "]");
                    } else {
                        reasonDeath = Utils.c(chatColor + "Explosión ["
                                + ((EntityDamageByEntityEvent) e).getDamager().getName() + chatColor + "]");
                    }
                }
            }
            case SUICIDE -> reasonDeath = "???";
            case KILL -> reasonDeath = "Comando kill";
            case MELTING -> reasonDeath = "Melting";

            default -> reasonDeath = c("&8???");
        }
        return reasonDeath;
    }

    public static String getLocationString(Location location) {
        String x = (String.valueOf(location.getBlockX()));
        String y = (String.valueOf(location.getBlockY()));
        String z = (String.valueOf(location.getBlockZ()));
        return "X: " + x + " Y: " + y + " Z: " + z;
    }
}
