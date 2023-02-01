package com.cybage.uhs.model;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipPK implements Serializable {
	private static final long serialVersionUID = 1L;

    private Long doctorsSpecializationId;
    private Long usersId;

    @Override
    public boolean equals(Object object) {
        if ( this == object ) {
            return true;
        }
        if ( object == null || getClass() != object.getClass() ) {
            return false;
        }
        RelationshipPK pk = (RelationshipPK) object;
        return Objects.equals( doctorsSpecializationId, pk.doctorsSpecializationId ) &&
                Objects.equals( usersId, pk.usersId );
    }

    @Override
    public int hashCode() {
        return Objects.hash( doctorsSpecializationId, usersId );
    }
}