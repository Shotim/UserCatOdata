package com.leverx.odata2train.model.constants;

import org.apache.olingo.odata2.api.edm.FullQualifiedName;

public class ModelConstants {

    public static final String ENTITY_SET_NAME_USERS = "Users";
    public static final String ENTITY_SET_NAME_CATS = "Cats";
    public static final String ENTITY_NAME_USER = "User";
    public static final String ENTITY_NAME_CAT = "Cat";

    public static final String NAMESPACE = "org.apache.olingo.odata2.ODataCats";

    private static final FullQualifiedName ENTITY_TYPE_CAT = new FullQualifiedName(NAMESPACE, ENTITY_NAME_CAT);
    private static final FullQualifiedName ENTITY_TYPE_USER = new FullQualifiedName(NAMESPACE, ENTITY_NAME_USER);

    public static final String ASSOCIATION_USER_CAT = "User_Cats_Cat_User";

    private static final FullQualifiedName ASSOCIATION_CAT_USER = new FullQualifiedName(NAMESPACE, "Cat_User_User_Cats");

    public static final String ROLE_CAT = "Cat_User";
    public static final String ROLE_USER = "User_Cats";

    public static final String ENTITY_CONTAINER = "ODataCatsEntityContainer";

    private static final String ASSOCIATION_SET = "Cats_Users";
}
