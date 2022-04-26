package dev.oussama.blogu.model;

import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class Publication extends AbstractAuditingEntity {
    private long likes, dislikes;
}
