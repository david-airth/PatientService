package com.flex.dhp.services;

/**
 * Created by david.airth on 7/13/17.
 */

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractEntity {

    @PrePersist
    protected void onCreate() {
        createdDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = new Date();
    }

    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    @Column
    @Temporal(TemporalType.DATE)
    private Date updatedDate;

    public Date getUpdatedDate() {
        return updatedDate;
    }

    @Column
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    public Date getCreatedDate() {
        return createdDate;
    }

    protected AbstractEntity() {
    } //JPA only
}
