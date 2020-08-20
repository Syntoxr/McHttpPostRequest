package io.github.firefluxion.HttpPost;

import org.bukkit.plugin.java.JavaPlugin;

public final class HttpPostRequest extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		getLogger().info("onEnable has been invoked!");
		this.getCommand("sendpostrequest").setExecutor(new SendWebHookCommandExecutor(this));
		getLogger().info("Registered command sendpostrequest");
		// TODO Insert logic to be performed when the plugin is enabled
	}

	@Override
	public void onDisable()
	{
		getLogger().info("onDisable has been invoked!");
		// TODO Insert logic to be performed when the plugin is disabled
	}
}
