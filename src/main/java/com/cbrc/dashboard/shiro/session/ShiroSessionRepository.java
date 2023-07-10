package com.cbrc.dashboard.shiro.session;

import org.apache.shiro.session.Session;

import java.io.Serializable;
import java.util.Collection;


public interface ShiroSessionRepository {

    /**
     * 存储Session
     *
     * @param session
     */
    void saveSession(Session session);

    /**
     * 删除session
     *
     * @param sessionId
     */
    void deleteSession(String sessionId);

    /**
     * 获取session
     *
     * @param sessionId
     * @return
     */
    Session getSession(String sessionId);

    /**
     * 获取所有sessoin
     *
     * @return
     */
    Collection<Session> getAllSessions();
}
