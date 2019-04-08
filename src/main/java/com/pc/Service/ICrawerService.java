package com.pc.Service;

import java.util.List;
import java.util.Map;

public interface ICrawerService {

    public boolean getUrlAndNameAndDate(Map<String, List<String>> map);

    public boolean insertAllData(Map<String, List<String>> map);
}
