package dev.oussama.blogu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Moderation {
    @OneToOne(fetch = FetchType.LAZY)
    private User moderator;
    private String reason;
    private boolean blocked;
}