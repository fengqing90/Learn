package learn.aop;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("SysLogService")
@Transactional
public class SysLogServiceImpl implements SysLogService {

    @Override
    public void saveSysLog(SysLog log) {

        System.out.println("--------------saveSysLog------------S");
        System.out.println(log.getContent());
        System.out.println("--------------saveSysLog------------E");
    }
}
