package com.cbrc.dashboard.shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;

/**
 * Copyright © 2018 mall Info. Tech Ltd. All rights reserved.
 *
 * @Package: com.doit.mall.shiro.session
 * @author: herry
 * @date: 2018-07-19  下午3:45
 * @Description: TODO
 */

public class MallSessionFactory implements SessionFactory {

    @Override
    public Session createSession(SessionContext initData) {
        if (initData != null) {
            String host = initData.getHost();
            if (host != null) {
                return new MallSession(host);
            }
        }
        return new MallSession();
    }

}
