package com.leverx.odata2train.model;

import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.olingo.odata2.api.annotation.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.annotation.edm.EdmEntityType;
import org.apache.olingo.odata2.api.annotation.edm.EdmKey;
import org.apache.olingo.odata2.api.annotation.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.annotation.edm.EdmProperty;

import java.util.Collection;

import static com.leverx.odata2train.model.constants.ModelConstants.ASSOCIATION_USER_CAT;
import static com.leverx.odata2train.model.constants.ModelConstants.ENTITY_CONTAINER;
import static com.leverx.odata2train.model.constants.ModelConstants.ENTITY_NAME_USER;
import static com.leverx.odata2train.model.constants.ModelConstants.ENTITY_SET_NAME_USERS;
import static com.leverx.odata2train.model.constants.ModelConstants.NAMESPACE;
import static com.leverx.odata2train.model.constants.ModelConstants.ROLE_CAT;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.olingo.odata2.api.annotation.edm.EdmNavigationProperty.Multiplicity.MANY;
import static org.apache.olingo.odata2.api.annotation.edm.EdmType.INT64;
import static org.apache.olingo.odata2.api.annotation.edm.EdmType.STRING;

@Data
@FieldDefaults(level = PRIVATE)
@EdmEntitySet(name = ENTITY_SET_NAME_USERS, container = ENTITY_CONTAINER)
@EdmEntityType(name = ENTITY_NAME_USER, namespace = NAMESPACE)
public class User {

    @EdmKey
    @EdmProperty(type = INT64)
    long id;

    @EdmProperty(type = STRING)
    String name;

    @EdmNavigationProperty(toMultiplicity = MANY,
            toType = Cat.class,
            association = ASSOCIATION_USER_CAT,
            toRole = ROLE_CAT)
    Collection<Cat> cats;
}
