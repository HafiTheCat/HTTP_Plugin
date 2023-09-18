package testplugin.testplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;

public final class TestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().log(Level.INFO, "TestPlugin Activated" + getDescription().getVersion());
        WebBukkit wb = new WebBukkit();
        try {
            wb.start(5000);
            wb.getRequestHandler();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getServer().getPluginManager().registerEvents(new myListener(), this);

    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Deactivated" + getDescription().getVersion());
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("http"))
            Bukkit.getLogger().log(Level.INFO,"1");
        return false;
    }

    class myListener implements Listener {

        @EventHandler
        public void onBlockBreak(BlockBreakEvent e){
            Bukkit.broadcastMessage("blockBreakEventCall");
            Bukkit.getLogger().log(Level.INFO,Bukkit.getServer().getIp());
            Connector c = new Connector();
            try{
                c.http(e.getPlayer(),e.getBlock());
            } catch(IOException ee){
                Bukkit.getLogger().log(Level.INFO,ee.getMessage());
            }


        }

    }
}
