package com.github.tanxinzheng.framework.web.config;

import com.github.tanxinzheng.framework.web.model.VerificationTypeEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "barrel.web")
public class WebProperties {

    private Verification verification;

    public Verification getVerification() {
        return verification;
    }

    public void setVerification(Verification verification) {
        this.verification = verification;
    }

    @Data
    public static class Verification {
        // 校验类型：数字校验码，
        private VerificationTypeEnum type;
        private boolean enable;
    }
}
