package com.example.demo.repository;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.IChange;

public interface Session {
    public IAgileSession connect() throws APIException;

    // public IChange getObject(int objectType, String oldName);

}
