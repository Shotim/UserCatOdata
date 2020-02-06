package com.leverx.odata2train.odata.processor;

import com.leverx.odata2train.repository.CatRepository;
import com.leverx.odata2train.repository.UserRepository;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmLiteralKind;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties.ODataEntityProviderPropertiesBuilder;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataNotFoundException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static com.leverx.odata2train.model.constants.ModelConstants.ENTITY_SET_NAME_CATS;
import static com.leverx.odata2train.model.constants.ModelConstants.ENTITY_SET_NAME_USERS;
import static org.apache.olingo.odata2.api.ep.EntityProvider.writeEntry;
import static org.apache.olingo.odata2.api.ep.EntityProvider.writeFeed;
import static org.apache.olingo.odata2.api.exception.ODataNotFoundException.ENTITY;

public class AppODataSingleProcessor extends ODataSingleProcessor {

    private UserRepository userRepository = new UserRepository();
    private CatRepository catRepository = new CatRepository();

    @Override
    public ODataResponse readEntity(GetEntityUriInfo uriInfo, String contentType) throws ODataException {
        if (uriInfo.getNavigationSegments().size() == 0) {
            EdmEntitySet entitySet = uriInfo.getStartEntitySet();

            if (ENTITY_SET_NAME_USERS.equals(entitySet.getName())) {
                KeyPredicate key = uriInfo.getKeyPredicates().get(0);
                long id = getKeyValue(key);
                Map<String, Object> user = userRepository.getById(id);

                if (user != null) {
                    return getODataResponse(contentType, entitySet, user);
                }
            } else if (ENTITY_SET_NAME_CATS.equals(entitySet.getName())) {
                KeyPredicate key = uriInfo.getKeyPredicates().get(0);
                long id = getKeyValue(key);
                Map<String, Object> cat = catRepository.getById(id);

                if (cat != null) {
                    return getODataResponse(contentType, entitySet, cat);
                }
            }
            throw new ODataNotFoundException(ENTITY);
        } else if (uriInfo.getNavigationSegments().size() == 1) {

            EdmEntitySet entitySet = uriInfo.getTargetEntitySet();
            if (ENTITY_SET_NAME_USERS.equals(entitySet.getName())) {
                KeyPredicate key = uriInfo.getKeyPredicates().get(0);
                long catKey = getKeyValue(key);
                Map<String, Object> cat = catRepository.getById(catKey);
                return getODataResponse(contentType, entitySet, cat);
            }
            throw new ODataNotFoundException(ENTITY);
        }
        throw new ODataNotFoundException(ENTITY);
    }

    @Override
    public ODataResponse readEntitySet(GetEntitySetUriInfo uriInfo, String contentType) throws ODataException {
        if (uriInfo.getNavigationSegments().size() == 0) {
            EdmEntitySet entitySet = uriInfo.getStartEntitySet();
            if (ENTITY_SET_NAME_CATS.equals(entitySet.getName())) {
                List<Map<String, Object>> cats = catRepository.getAll();
                URI serviceRoot = getContext().getPathInfo().getServiceRoot();
                ODataEntityProviderPropertiesBuilder oDataEntityProviderPropertiesBuilder
                        = EntityProviderWriteProperties.serviceRoot(serviceRoot);
                return writeFeed(contentType, entitySet, cats, oDataEntityProviderPropertiesBuilder.build());
            } else if (ENTITY_SET_NAME_USERS.equals(entitySet.getName())) {
                List<Map<String, Object>> users = userRepository.getAll();
                URI serviceRoot = getContext().getPathInfo().getServiceRoot();
                ODataEntityProviderPropertiesBuilder oDataEntityProviderPropertiesBuilder
                        = EntityProviderWriteProperties.serviceRoot(serviceRoot);
                return writeFeed(contentType, entitySet, users, oDataEntityProviderPropertiesBuilder.build());
            }
            throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
        }
        throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
    }

    private ODataResponse getODataResponse(String contentType, EdmEntitySet entitySet, Map<String, Object> entity) throws ODataException {
        URI serviceRoot = getContext().getPathInfo().getServiceRoot();
        ODataEntityProviderPropertiesBuilder providerPropertiesBuilder
                = EntityProviderWriteProperties.serviceRoot(serviceRoot);
        return writeEntry(contentType, entitySet, entity, providerPropertiesBuilder.build());
    }

    private int getKeyValue(KeyPredicate key) throws ODataException {
        EdmProperty property = key.getProperty();
        EdmSimpleType type = (EdmSimpleType) property.getType();
        return type.valueOfString(key.getLiteral(), EdmLiteralKind.DEFAULT, property.getFacets(), Integer.class);
    }
}
