package io.github.firefluxion.HttpPost;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendWebHookCommandExecutor implements CommandExecutor
{

	private final HttpPostRequest plugin;

	public SendWebHookCommandExecutor(HttpPostRequest plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		plugin.getLogger().info("SendWebHook onCommand got triggered");

		if (!command.getName().equalsIgnoreCase("sendpostrequest"))
		{
			return false;
		}

		plugin.getLogger().info("sendpostrequest correct Name");

		if (args.length != 2)
		{
			plugin.getLogger().info("You must give 2 arguments");
			return false;
		}

		plugin.getLogger().info("sendpostrequest correct amout of arguments");

		String webHookUrl = args[0];
		String message = args[1];

		plugin.getLogger().info("WebHookUrl:" + webHookUrl);
		plugin.getLogger().info("Message:" + message);

		// HttpClient httpClient = HttpClientBuilder.create().build(); //Use this
		// instead
		if(SendPostRequest(webHookUrl, message)) 
		{
			if(sender instanceof Player) 
			{
				Player player = (Player) sender;
				player.sendMessage(String.format("Executed post request: %s %s", webHookUrl, message));
			}
			return true;
		}
		else 
		{
			return false;
		}
	}

	public boolean SendPostRequest(String aUrl, String aData)
	{
		try
		{
			URL url = new URL(aUrl);
			URLConnection con = url.openConnection();
			HttpURLConnection http = (HttpURLConnection)con;
			http.setRequestMethod("POST"); // PUT is another valid option
			http.setDoOutput(true);
			
			byte[] out = aData.getBytes(StandardCharsets.UTF_8);
			int length = out.length;

			http.setFixedLengthStreamingMode(length);
			http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			http.connect();
			try(OutputStream os = http.getOutputStream()) {
			    os.write(out);
			}
			
			String x = String.format("Executed %s request: %s %s", http.getRequestMethod(), aUrl, aData);
			plugin.getLogger().info(x);
			
			return true;
			// handle response here...

		}
		catch (Exception ex)
		{
			return false;
			// handle exception here
		}
		finally
		{
		}
	}

}
