package io.github.wkktoria.edux.audit;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
class EduxInfoContributor implements InfoContributor {
    @Override
    public void contribute(final Info.Builder builder) {
        Map<String, String> info = new HashMap<>();
        info.put("App Name", "Edux");
        info.put("App Version", "1.0.0");
        info.put("App Description", "Simple learning management system");

        builder.withDetail("edux-info", info);
    }
}
