package dev.oussama.blogu.models;

import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class Publication extends AbstractAuditingEntity{
    private long likes, dislikes;
}
