package learn.aop;

import java.lang.reflect.Method;
import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("logAround")
public class LogAroundInterceptor implements MethodInterceptor {
    @Autowired
    private SysLogService sysLogService;
    private Logger logger = LoggerFactory.getLogger(LogAroundInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object returnObject = invocation.proceed();
        System.out
            .println("METHOD:" + method + " \n\t RETURNOBJECT:" + returnObject);
        if (method.isAnnotationPresent(RecordLog.class)) {
            Object[] arguments = invocation.getArguments();
            Object saveObject = null;
            SysLog sysLog = new SysLog();
            if (null != arguments) {
                saveObject = arguments[0];
                sysLog = this.getSysLog(this.getOperaType(method.getName()),
                    saveObject);
                this.sysLogService.saveSysLog(sysLog);
            }
        }
        return returnObject;
    }

    public int getOperaType(String methodName) {
        int type = 0;
        if (methodName.startsWith("save") || methodName.startsWith("add")
            || methodName.startsWith("insert")
            || methodName.startsWith("update")) {
            type = 1;
        } else if (methodName.startsWith("delete")
            || methodName.startsWith("del")) {
            type = 2;
        }
        return type;
    }

    public SysLog getSysLog(int type, Object object) {
        SysLog sysLog = new SysLog();
        sysLog.setCreateTime(new Date());
        StringBuffer sysContent = new StringBuffer();
        sysContent.append("type:" + type + " | object:" + object);
        // if (type != 1 && type != 2) {
        // logger.warn("此方法不能被记录日志");
        // return null;
        // }
        // if (object instanceof User) {
        // User user = (User) object;
        // sysContent.append("用户:");
        // if (type == 1) {
        // sysContent.append(user.getUsername()).append("被保存.");
        // } else if (type == 2) {
        // sysContent.append(user.getDeleteUser()).append("删除了用户:" +
        // user.getUniqueUserName());
        // }
        // } else if (object instanceof Tribe) {
        // Tribe tribe = (Tribe) object;
        // if (type == 1) {
        // sysContent.append("用户:").append(tribe.getUser().getUniqueUserName()).append("保存了部落:")
        // .append(tribe.getName());
        // } else if (type == 2) {
        // sysContent.append("用户:").append(tribe.getDeleteUser()).append("删除了部落:")
        // .append(tribe.getUniqueTribeName());
        // }
        // } else if (object instanceof Assessment) {
        // Assessment assessment = (Assessment) object;
        // int assType = assessment.getType();
        // User user = assessment.getUserByUserId();
        // sysContent.append("用户:").append(user.getUniqueUserName()).append("举报了");
        // if (assType == 1) {
        // sysContent.append("资源").append(assessment.getResource().getTitle());
        // } else if (assType == 2) {
        // sysContent.append("部落").append(assessment.getTribeByAccTribeId().getUniqueTribeName());
        // } else if (assType == 3) {
        // sysContent.append("人").append(assessment.getUserByUserId().getUniqueUserName());
        // }
        // } else if (object instanceof Role) {
        // Role role = (Role) object;
        // sysContent.append("用户:").append(role.getOperaUser());
        // if (type == 1) {
        // sysContent.append("保存了").append(role.getRoleName());
        // } else if (type == 2) {
        // sysContent.append("删除了").append(role.getRoleName());
        // }
        // } else if (object instanceof Resource) {
        // Resource res = (Resource) object;
        // boolean isPass = res.getAppStatus() == 1;
        // sysContent.append("一级审核人员:").append(res.getAppUser().getUniqueUserName());
        // sysContent.append("审核").append(res.getUniqueResName());
        // if (!isPass) {
        // sysContent.append("未通过");
        // } else if (isPass) {
        // sysContent.append("通过");
        // }
        // }
        this.logger.info(sysContent.toString());
        sysLog.setContent(sysContent.toString());
        return sysLog;
    }
}