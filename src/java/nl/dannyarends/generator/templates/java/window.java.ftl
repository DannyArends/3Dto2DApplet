/* \file ${file}
 * \copyright Danny Arends 2011-${year?c}, all rights reserved
 * \date ${date}
 * \brief Generated file from template ${template}
 */
<#if window.javanamespace != " ">
package ${window.javanamespace};
</#if>

import nl.dannyarends.rendering.objects.hud.HudWindow;
import nl.dannyarends.eventHandling.ServerConnection;
<#if window.hasTexts>import nl.dannyarends.rendering.objects.hud.HudText;</#if>
<#if window.hasIcons>import nl.dannyarends.rendering.IconLoader;</#if>

class ${window.name} extends HudWindow{
	
	public ${window.name}(ServerConnection s){
		super(s, ${window.x}, ${window.y}, ${window.w}, ${window.h}, "${window.name}");
		addStaticHudObjects();
	}
	
	public void addStaticHudObjects(){
		<#foreach text in window.getTexts()>
  		addChild(new HudText(${text.x},${text.y},"${text.string}"));
  		</#foreach>
  		<#foreach icon in window.getIcons()>
  		addChild(IconLoader.getIcon(${icon.x},${icon.y},"${icon.filename}"));
  		</#foreach>
	}
	
	<#if window.onopen>
	//Function we can overwrite to get a custom handler at each window open event
	//Useful for displaying one time server information
	@Override
	public void onOpen(){
		clearChildren();
		addStaticHudObjects();
		<#foreach text in window.getServerTexts()>
		addChild(new HudText(${text.x},${text.y},s.commandToServer("window=${window.name}&sid=${text.string}")));
		</#foreach>
	}
	</#if>
	
	<#if window.update>
	//Function we can overwrite to get a custom handler at each window update event
	//Useful for displaying real time server information
	@Override
	public void update(){
		clearChildren();
		addStaticHudObjects();
		<#foreach text in window.getLiveTexts()>
		addChild(new HudText(${text.x},${text.y},s.commandToServer("window=${window.name}&lid=${text.string}")));
		</#foreach>
	}
	</#if>
}