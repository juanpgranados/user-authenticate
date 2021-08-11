package com.challenge.userauthenticate.job;

import com.challenge.userauthenticate.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class UnlockUser {
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    UserService userService;

    // job runs every hour to unlock users who have 24 hours locked
    @Scheduled(cron = "0 0 * ? * * ")
    public void unlockUsers () {
        logger.info("Unlock users job started...");
        LocalDateTime datetime = LocalDateTime.now().minusHours(24);
        Date dateInit = Date.from(datetime.atZone(ZoneId.systemDefault()).toInstant());
        userService.unlockUsers(dateInit);
    }
}
