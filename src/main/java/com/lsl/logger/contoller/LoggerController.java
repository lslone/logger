package com.lsl.logger.contoller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//@Controller //返回页面
@RestController  //返回字符串    == @Controller + 方法上@ResponseBody
@Slf4j
public class LoggerController {

    @Autowired
    KafkaTemplate kafkaTemplate;

    @RequestMapping("/applog")
    public String applog(@RequestBody String logString){
        log.info(logString);
        JSONObject jsonObject = JSON.parseObject(logString);
        if (jsonObject.getString("start") != null && jsonObject.getString("start").length() > 0) {
            kafkaTemplate.send("gmall_start", logString);
        }else {
            kafkaTemplate.send("gmall_event", logString);
        }
        System.out.println(logString);
        return "success";
    }
}
