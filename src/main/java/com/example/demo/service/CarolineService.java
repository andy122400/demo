package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agile.api.ChangeConstants;
import com.agile.api.IAgileSession;
import com.agile.api.IChange;
import com.agile.api.IItem;
import com.agile.api.IRedlinedTable;
import com.agile.api.IRow;
import com.agile.api.ITable;
import com.agile.api.ItemConstants;
import com.example.demo.repository.Session;

@Service
public class CarolineService {

    @Autowired
    private Session session;

    public List<String> doAction(String changeNumber, String src, String replace) throws Exception {

        IAgileSession agileSession = session.connect();
        IChange change = (IChange) agileSession.getObject(IChange.OBJECT_TYPE, changeNumber);
        IItem item_src = (IItem) agileSession.getObject(IItem.OBJECT_TYPE, src);
        IItem item_replace = (IItem) agileSession.getObject(IItem.OBJECT_TYPE, replace);

        if (change == null)
            throw new Exception("系統沒這張單" + changeNumber);
        if (item_src == null)
            throw new Exception("系統沒這顆料" + src);
        if (item_replace == null)
            throw new Exception("系統沒這顆料" + replace);
        ITable afTable = change.getTable(ChangeConstants.TABLE_AFFECTEDITEMS);
        Iterator<?> it = afTable.iterator();
        // Map<String, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        while (it.hasNext()) {
            IRow row = (IRow) it.next();

            if (!row.isFlagSet(ChangeConstants.FLAG_AI_ROW_HAS_REDLINE_BOM)
                    && row.isFlagSet(ChangeConstants.FLAG_AI_ROW_HAS_BOM)) {
                IItem item = (IItem) row.getReferent();
                list.add(item.getName());
                ITable afRedlinedTable = item.getTable(ItemConstants.TABLE_REDLINEBOM);
                // afRedlinedTable.add(item_replace);

                Iterator<?> i = afRedlinedTable.iterator();
                // System.out.println(item + "新增" + item_replace);
                Map rows = new HashMap();
                while (i.hasNext()) {
                    IRow row2 = (IRow) i.next();
                    String bomitem = (String) row2.getValue(ItemConstants.ATT_BOM_ITEM_NUMBER);
                    if (bomitem.equals(item_src.getName())) {
                        Map<Integer, String> map = new HashMap<>();
                        map.put(ItemConstants.ATT_BOM_ITEM_NUMBER, replace);

                        rows.put(row2, map);
                        afRedlinedTable.updateRows(rows);
                        break;
                    }
                }
            }

        }
        return list;
    }
}