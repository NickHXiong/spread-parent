package com.wxd.spread.wechat.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.wxd.spread.core.mapper.WechatMenuMapper;
import com.wxd.spread.core.model.WechatMenu;
import com.wxd.spread.core.service.WechatService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMenuService;

@Component
public class InitMenuListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private WechatService wechatService;
	@Autowired
	private WechatMenuMapper wechatMenuMapper;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null){//root application context 没有parent，他就是root  
			System.out.println("----------------------------------------------------------------------------------------------------------------------");
			List<WechatMenu> allMenus = wechatMenuMapper.selectAll();
			
			try {
				// 删除所有菜单，重新创建
				WxMpMenuService menuService = wechatService.getMenuService();
				menuService.menuDelete();
				// 重新创建菜单
				JsonArray json = createMenuJson(allMenus);
				if (json != null && json.size() > 0) {
					JsonObject menuObject = new JsonObject();
					menuObject.add("button", json);
					System.out.println(menuObject.toString());
					menuService.menuCreate(menuObject.toString());
				}
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据数据库中的实体创建菜单json
	 * @param menus
	 * @return
	 */
	private JsonArray createMenuJson(List<WechatMenu> menus) {
		if (menus != null && menus.size() > 0) {
			JsonArray result = new JsonArray();
			
			Map<Integer,JsonObject> mapJson = new HashMap<>();
			JsonObject t ; 
			for (WechatMenu m:menus) {
				if (m.getParentId() == null) { // 如果是顶级菜单
					t = mapJson.get(m.getId());
					if (t == null) {
						t = new JsonObject();
						mapJson.put(m.getId(), t);
					}
					putProperties(t,m);
				} else { // 如果是二级菜单
					t = mapJson.get(m.getParentId());
					if (t == null) {
						t = new JsonObject();
						mapJson.put(m.getId(), t);
					}
					JsonArray jArr = t.getAsJsonArray("sub_button");
					if (jArr == null) {
						jArr = new JsonArray();
						t.add("sub_button", jArr);
					}
					
					
					JsonObject subJson = new JsonObject();
					putProperties(subJson,m);
					if (jArr.size() == 0) {
						jArr.add(subJson);
					} else {
						Integer order = m.getOrder();
						int index = 0;
						while (jArr.size() > index) {
							JsonObject neiJson = (JsonObject)jArr.get(index);
							if (neiJson.get("order").getAsInt() > order) {
								jArr.set(index, subJson);
								index = -1;
								break;
							}
							index++;
						}
						if (index != -1) {
							jArr.add(subJson);
						}
					}
				}
			}
			ArrayList<Integer> l = new ArrayList<Integer>(mapJson.keySet());
			Collections.sort(l);
			for (Integer x:l) {
				result.add(mapJson.get(x));
			}
			
			return result;
		}
		return null;
	}
	
	
	private void putProperties(JsonObject json,WechatMenu menu) {
		json.addProperty("order", menu.getOrder());
		json.addProperty("name", menu.getName());
		json.addProperty("type", menu.getType());
		json.addProperty("url", menu.getUrl());
		json.addProperty("key", menu.getKey());
		json.addProperty("media_id", menu.getMediaId());
		json.addProperty("appid", menu.getAppid());
		json.addProperty("pagepath", menu.getPagepath());
	}

}
