package com.wxd.spread.core.handler;


import org.apache.log4j.Logger;

import me.chanjar.weixin.mp.api.WxMpMessageHandler;

/**
 * 
 * @author Binary Wang
 *
 */
public abstract class AbstractHandler implements WxMpMessageHandler {
    protected Logger logger = Logger.getLogger(getClass());
}
