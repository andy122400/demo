package com.example.demo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.IDataObject;
import com.agile.api.IRow;
import com.agile.api.ITable;
import com.agile.api.IUser;
import com.agile.api.IUserGroup;
import com.agile.api.UserGroupConstants;
import com.example.demo.repository.Session;

@Service
public class Test {

    @Autowired
    private Session session;

    public void test() throws APIException {

        IAgileSession s = null;
        try {
            s = session.connect();

            IUserGroup userGroup = (IUserGroup) s.getObject(IUserGroup.OBJECT_TYPE, "MFG Manager WF Group");
            ITable t = userGroup.getTable(UserGroupConstants.TABLE_USERS);
            Iterator it = t.iterator();
            IRow user = null;
            while (it.hasNext()) {
                user = (IRow) it.next();
                System.out.println(user.getReferent().getName());
                if ("ben_cheng".equals(user.getReferent().getName())) {
                    break;
                }
            }
            if (user != null)
                t.removeRow(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
