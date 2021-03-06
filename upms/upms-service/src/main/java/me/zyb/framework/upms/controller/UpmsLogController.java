package me.zyb.framework.upms.controller;

import lombok.extern.slf4j.Slf4j;
import me.zyb.framework.core.base.BaseController;
import me.zyb.framework.upms.condition.UpmsLogCondition;
import me.zyb.framework.upms.dict.UpmsPermissionCode;
import me.zyb.framework.upms.dict.UpmsRabbitMqConst;
import me.zyb.framework.upms.model.UpmsLogModel;
import me.zyb.framework.upms.service.UpmsLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyingbin
 */
@Slf4j
@RequestMapping("/upmsLog")
@RestController
public class UpmsLogController extends BaseController {
    @Autowired
    private UpmsLogService upmsLogService;

    /**
     * 分页数据
     * @param condition 查询条件
     * @return Object
     */
    @RequiresPermissions(UpmsPermissionCode.LOG_QUERY)
    @RequestMapping("/queryByCondition")
    public Object queryByCondition(@RequestBody UpmsLogCondition condition) {
        Page<UpmsLogModel> page = upmsLogService.queryByCondition(condition);
        return rtSuccess(page);
    }

	/**
	 * 监听日志队列
	 * @param model 消息模板
	 */
	@RabbitListener(queues = UpmsRabbitMqConst.QUEUE_UPMS_LOG)
	public void queueUpmsLog(UpmsLogModel model) {
    	log.info("接收到日志消息：{}", model.toString());
    	try{
		    upmsLogService.save(model, null);
	    }catch (Exception e){
    		log.error(e.getMessage());
	    }
    }
}
