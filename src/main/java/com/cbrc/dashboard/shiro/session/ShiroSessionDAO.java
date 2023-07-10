package com.cbrc.dashboard.shiro.session;

import com.cbrc.dashboard.utils.LoggerUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;

public class ShiroSessionDAO extends AbstractSessionDAO {

    @Autowired
    private ShiroSessionRepository shiroSessionRepository;

    @Override
    public void update(Session session) throws UnknownSessionException {
        shiroSessionRepository.saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if (session == null) {
            LoggerUtils.error(getClass(), "Session 不能为null");
            return;
        }
        Serializable id = session.getId();
        if (id != null)
            shiroSessionRepository.deleteSession((String) id);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return shiroSessionRepository.getAllSessions();
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        shiroSessionRepository.saveSession(session);
        return sessionId;
    }

    @Override
    protected void assignSessionId(Session session, Serializable sessionId) {
        ((MallSession) session).setId(sessionId);
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return shiroSessionRepository.getSession((String) sessionId);
    }

}
