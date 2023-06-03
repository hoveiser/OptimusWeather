package com.inpress.optimusweather.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

/**
 * The base entity containing the identifier and auditable fields.
 */
@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class BaseEntity extends AuditableEntity {

    /**
     * Represents the entity identifier in database.
     */
    @Id
    @Access(AccessType.PROPERTY)
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    protected Long id;
}
