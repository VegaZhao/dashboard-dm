package com.cbrc.dashboard.shiro.listenter;


import com.cbrc.dashboard.shiro.session.ShiroSessionRepository;
import com.cbrc.dashboard.utils.LoggerUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroSessionListener implements SessionListener {

    @Autowired
    private ShiroSessionRepository shiroSessionRepository;

    /**
     * 一个回话的生命周期开始
     */
    @Override
    public void onStart(Session session) {
        //TODO
        LoggerUtils.debug(ShiroSessionListener.class, "sessionId：" + session.getId() + " on start");
    }

    /**
     * 一个回话的生命周期结束
     */
    @Override
    public void onStop(Session session) {
        //TODO
        LoggerUtils.debug(ShiroSessionListener.class, "sessionId：" + session.getId() + "on stop");
    }

    @Override
    public void onExpiration(Session session) {
        shiroSessionRepository.deleteSession(session.getId().toString());
    }


}

