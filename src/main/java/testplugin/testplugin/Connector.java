package testplugin.testplugin;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.logging.Level;

public class Connector {

    Connector(){}

    public void http(Player player, Block block) throws IOException {
        URL url = new URL("http","127.0.0.1",4000,"");
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)con;
        http.setRequestMethod("POST");
        http.setDoOutput(true);

        Map<String,String> arguments = new HashMap<>();
        String playerID = player.getUniqueId().toString();
        String materialString = block.getType().toString();
        arguments.put("playerid", playerID);
        arguments.put("block", materialString);
        StringJoiner sj = new StringJoiner("&");
        for(Map.Entry<String,String> entry : arguments.entrySet())
            sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                    + URLEncoder.encode(entry.getValue(), "UTF-8"));
        byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
        int length = out.length;
        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        http.connect();
        try(OutputStream os = http.getOutputStream()) {
            os.write(out);
        }
        catch(Exception e){
            Bukkit.getLogger().log(Level.WARNING,e.getMessage());
        }
        Bukkit.getLogger().log(Level.INFO,out.toString());
    }


}
