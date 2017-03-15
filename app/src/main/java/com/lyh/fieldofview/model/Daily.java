package com.lyh.fieldofview.model;

import java.util.List;

/**
 * Created by lyh on 2017/3/15.
 */

public class Daily {

    public String nextPageUrl;
    public List<IssueList> issueLists;

    public static class IssueList{
        public long releaseTime;
        public String type;
        public long date;
        public long publishTime;
        public int count;
        public List<ItemList> itemLists;
    }
}
