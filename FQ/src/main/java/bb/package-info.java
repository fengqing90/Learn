/**
 * @author FengQing
 * @date 2017年9月1日 下午6:10:37
 */
package bb;

import java.util.List;

import gs.utils.JsonMapper;

class a {
    public static void main(String[] args) {
        List<Integer> phiIds = new java.util.ArrayList<>();
        phiIds.add(1560);

        System.out.println(JsonMapper.defaultMapper().toJson(phiIds));
    }
}