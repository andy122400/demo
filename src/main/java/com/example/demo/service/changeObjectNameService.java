package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agile.api.ChangeConstants;
import com.agile.api.IAgileSession;
import com.agile.api.IChange;
import com.example.demo.repository.Session;

@Service
public class changeObjectNameService {
    @Autowired
    private Session session;

    public void changeObjectName(String oldName, String newName) throws Exception {
        IAgileSession agileSession = session.connect();
        IChange change = (IChange) agileSession.getObject(IChange.OBJECT_TYPE, oldName);
        if (change == null) {
            throw new Exception("系統沒這張單" + oldName);
        }
        change.getCell(ChangeConstants.ATT_COVER_PAGE_NUMBER).setValue(newName);
    }

}
