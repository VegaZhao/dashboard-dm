package com.cbrc.dashboard.shiro.session;

import com.cbrc.dashboard.utils.LoggerUtils;
import com.cbrc.dashboard.cache.RedisOps;
import com.cbrc.dashboard.contants.RedisPrefixConstants;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.*;
import java.util.Collection;
import java.util.HashSet;

public class ShiroSessionRepositoryImpl implements ShiroSessionRepository {


    @Autowired
    private RedisOps redisOps;

    @Override
    public void saveSession(Session session) {
        if (session == null || session.getId() == null)
            throw new NullPointerException("session is empty");
        try {
            //不存在才添加。
            if (null == session.getAttribute(CustomSessionManager.SESSION_STATUS)) {
                //Session
                SessionStatus sessionStatus = new SessionStatus();
                session.setAttribute(CustomSessionManager.SESSION_STATUS, sessionStatus);
            }

            /**
             *
             * 注意： 这里我们配置 redis的TTL单位是秒。
             long sessionTimeOut = session.getTimeout() / 1000;
             Long expireTime = sessionTimeOut + SESSION_VAL_TIME_SPAN + (5 * 60);
             */

            /*
            直接使用 (int) (session.getTimeout() / 1000) 的话，session失效和redis的TTL 同时生效
             */
            byte[] sessionArray = mallSession2Byte((MallSession) session);
            redisOps.setByteEx(buildRedisSessionKey(session.getId()), sessionArray, session.getTimeout() / 1000);
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "存储session异常，id:[%s]", buildRedisSessionKey(session.getId()));
        }
    }

    @Override
    public void deleteSession(String id) {
        if (id == null) {
            throw new NullPointerException("session id 为空");
        }
        try {
            redisOps.delByte(buildRedisSessionKey(id));
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "删除session出现异常，id:[%s]", id);
        }
    }


    @Override
    public Session getSession(String id) {
        Session session = null;
        if (id == null)
            throw new NullPointerException("session id is empty");
        try {
            byte[] sessionArray = redisOps.getByte(buildRedisSessionKey(id));
            if (null == sessionArray)
                LoggerUtils.fmtDebug(getClass(), "获取session为空，id:[%s]", buildRedisSessionKey(id));
            session = byte2MallSession(sessionArray);
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "获取session异常，id:[%s]", buildRedisSessionKey(session.getId()));
        }
        return session;
    }

    @Override
    public Collection<Session> getAllSessions() {
        Collection<Session> sessions = new HashSet<>();
        try {

            Collection<byte[]> sessionBytes = redisOps.getByteByPattern("*" + RedisPrefixConstants.REDIS_SHIRO_SESSION + "*");
            for (byte[] v : sessionBytes) {
                sessions.add(byte2MallSession(v));
            }
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "获取全部session异常");
        }
        return sessions;
    }

    /**
     * MallSession 序列化
     *
     * @param session
     * @return
     */
    private byte[] mallSession2Byte(MallSession session) {
        if (null == session)
            return null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        byte[] sessionArray = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            session.writeObject(oos);
            sessionArray = bos.toByteArray();
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), "序列化MallSession失败 %s", e.toString());
        } finally {
            closeStream(oos);
            closeStream(bos);
        }
        return sessionArray;
    }

    /**
     * MallSession 反序列化
     *
     * @param in
     * @return
     */
    private MallSession byte2MallSession(byte[] in) {
        if (null == in)
            return null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        MallSession session = null;
        try {
            bis = new ByteArrayInputStream(in);
            ois = new ObjectInputStream(bis);
            session = new MallSession();
            session.readObject(ois);
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), "反序列化MallSession失败 %s", e.toString());
        } finally {
            closeStream(ois);
            closeStream(bis);
        }
        return session;
    }

    private String buildRedisSessionKey(Serializable sessionId) {
        return RedisPrefixConstants.REDIS_SHIRO_SESSION + sessionId;
    }

    private void closeStream(Closeable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), "session 读写流关闭失败");
            e.printStackTrace();
        }
    }
}
