package dev.oussama.blogu.config;

import dev.oussama.blogu.model.AbstractAuditingEntity;
import dev.oussama.blogu.model.Moderation;
import dev.oussama.blogu.model.Post;
import dev.oussama.blogu.model.PreviewPost;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ArtSoukUtils {

    public static <T extends AbstractAuditingEntity> void setAuditingAttributes(T t) {
        t.setCreatedBy("SYSTEM");
        t.setCreatedDate(new java.util.Date());
    }

    public static PreviewPost toPreviewPost(Post p) {
        PreviewPost previewPost = new PreviewPost();
        previewPost.setId(p.getId());
        previewPost.setTitle(p.getTitle());
        previewPost.setCreatedBy(p.getUser().getUsername());
        previewPost.setCreatedAt(p.getCreatedDate());
        previewPost.setMimeType(p.getThumbnailMimeType());
        previewPost.setViews(p.getViews());

        Moderation m = p.getModeration();
        previewPost.setModerationReason(m.getReason());
        try {
            previewPost.setThumbnail(Files.readAllBytes(Paths.get(p.getThumbnailPath())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return previewPost;
    }

    public interface JwtConstants {
        long EXPIRATION_TIME = 3_600_000;
        String TOKEN_PREFIX = "Bearer ";
        String REQ_HEADER = "Authorization";
    }
}
