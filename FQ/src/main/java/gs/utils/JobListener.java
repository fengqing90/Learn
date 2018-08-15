package gs.utils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameter.ParameterType;
import org.springframework.stereotype.Component;

/**
 * @author FengQing 2016年11月29日
 *         默认Job监听
 */
@Component
public class JobListener implements JobExecutionListener {

    /**
     * 日志记录器
     */
//    private static final UcMonitorLogger LOGGER = UcMonitorLoggerFactory
//        .getUcMonitorLogger(JobListener.class);
    /**
     * 日志记录器
     */
    private static final Logger LOGGER = LoggerFactory
        .getLogger(JobListener.class);

    /**
     * 只有失败的job才发信息,所以不做信息打印等处理
     */
    @Override
    public void beforeJob(JobExecution jobExecution) {
//      String address = "127.0.0.1";
//      try {
//          address = NetUtils.getLocalHostLANAddress().getHostAddress();
//      } catch (Exception ex) {
//          ex.printStackTrace();
//      }
//
//      String content = new StringBuilder(512).append('\n').append('【')
//          .append(jobExecution.getJobInstance().getJobName()).append('】')
//          .append("定时任务-开始").append('\n').append("参数:")
//          .append(JobListener.getJobParameters(jobExecution)).append('\n')
//          .append("IP地址:").append(address).append('\n').append("开始时间:")
//          .append(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"))
//          .append('\n').toString();
//
//
//      JobListener.LOGGER.info(content);
//
//      // 成功不发邮件
////      LogInfoHelper.infoWithMsg(content);
//
//      // java.lang.ClassCastException: [Ljava.lang.String; cannot be cast to java.lang.String ???
////      String[] toAddress = this.helper
////          .getSystemSettingValue(SystemSettingKey.KEPLER_GROUP_EMAIL);
////      this.mailService.sendMailAsync(MailType.DEFAULT, content, false,
////          toAddress);
//
//      // 由于这种发邮件有问题暂时注释
////      JobListener.LOGGER
////          .byInfo()
////          .mail()
////          .message(
////              "【" + jobExecution.getJobInstance().getJobName() + "-定时任务】-开始")
////          .param("IP地址", address)
////          .param("参数", jobExecution.getJobParameters().getParameters())
////          .param("开始时间",
////              DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"))
////          .monitor();

    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        BatchStatus status = jobExecution.getStatus();

        if (status != BatchStatus.COMPLETED) {
            long duration = jobExecution.getEndTime().getTime()
                - jobExecution.getStartTime().getTime();

            List<Throwable> failureExceptions = jobExecution
                .getAllFailureExceptions();
            StringBuilder sb = new StringBuilder(1024);
            for (Throwable th : failureExceptions) {
                sb.append(ExceptionUtils.getStackTrace(th)).append('\n');
            }

            String address = "127.0.0.1";

            try {
//                address = NetUtils.getLocalHostLANAddress().getHostAddress();
            } catch (Exception ex) {
                JobListener.LOGGER.error("获取IP地址错误，ERROR={}。", ex);
            }

            String content = new StringBuilder(512).append('\n').append('【')
                .append(jobExecution.getJobInstance().getJobName()).append('】')
                .append("定时任务-结束").append('\n').append("参数:")
                .append(JobListener.getJobParameters(jobExecution))
                .append('\n').append("IP地址:").append(address).append('\n')
//                .append("耗时:").append(DateUtils.formatDuration(duration))
                .append('\n').append("结果:").append(status).append('\n')
                .append(sb).toString();

            JobListener.LOGGER.error(content);
        }

    }

    private static List<String> getJobParameters(JobExecution jobExecution) {
        return jobExecution
            .getJobParameters()
            .getParameters()
            .entrySet()
            .stream()
            .map(
                p -> {
                    return p.getKey()
                        + "="
                        + (p.getValue().getType() != ParameterType.DATE ? p
                            .getValue().getValue() : DateFormatUtils.format(
                            (Date) p.getValue().getValue(),
                            "yyyy-MM-dd HH:mm:ss"));
                }).collect(Collectors.toList());
    }
}
