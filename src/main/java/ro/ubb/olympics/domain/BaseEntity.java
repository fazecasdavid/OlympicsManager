package ro.ubb.olympics.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base entity to be extended by identifiable objects.
 *
 * @author radu.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity<ID> {

    /**
     * The ID of the entity.
     */
    protected ID id;

}