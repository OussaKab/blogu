package dev.oussama.blogu.config;

import dev.oussama.blogu.model.AbstractAuditingEntity;

public class ArtSoukUtils {

    public static <T extends AbstractAuditingEntity> void setAuditingAttributes(T t) {
        t.setCreatedBy("SYSTEM");
        t.setCreatedDate(new java.util.Date());
    }

    public interface JwtConstants {
        long EXPIRATION_TIME = 1_800_000;
        String TOKEN_PREFIX = "Bearer ";
        String REQ_HEADER = "Authorization";
    }
}
