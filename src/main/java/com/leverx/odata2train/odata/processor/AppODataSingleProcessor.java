package com.leverx.odata2train.odata.processor;

import com.leverx.odata2train.repository.CatRepository;
import com.leverx.odata2train.repository.UserRepository;
import lombok.AllArgsConstructor;
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
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static com.leverx.odata2train.model.constants.ModelConstants.ENTITY_SET_NAME_CATS;
import static com.leverx.odata2train.model.constants.ModelConstants.ENTITY_SET_NAME_USERS;
import static org.apache.olingo.odata2.api.ep.EntityProvider.writeEntry;
import static org.apache.olingo.odata2.api.ep.EntityProvider.writeFeed;
import static org.apache.olingo.odata2.api.exception.ODataNotFoundException.ENTITY;

@Component
@AllArgsConstructor
public class AppODataSingleProcessor extends ODataSingleProcessor {

    private UserRepository userRepository;
    private CatRepository catRepository;

    @Override
    public ODataResponse readEntity(GetEntityUriInfo uriInfo, String contentType) throws ODataException {
        if (uriInfo.getNavigationSegments().size() == 0) {
            EdmEntitySet entitySet = uriInfo.getStartEntitySet();
            String entitySetName = entitySet.getName();
            KeyPredicate key = uriInfo.getKeyPredicates().get(0);
            long id = getKeyValue(key);
            switch (entitySetName) {
                case ENTITY_SET_NAME_USERS:
                    Map<String, Object> user = userRepository.getById(id);
                    if (user != null) {
                        return getODataResponse(contentType, entitySet, user);
                    }
                    break;
                case ENTITY_SET_NAME_CATS:
                    Map<String, Object> cat = catRepository.getById(id);
                    if (cat != null) {
                        return getODataResponse(contentType, entitySet, cat);
                    }
                    break;
                default:
                    throw new ODataNotFoundException(ENTITY);
            }
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
            String entitySetName = entitySet.getName();
            switch (entitySetName) {
                case ENTITY_SET_NAME_CATS:
                    List<Map<String, Object>> cats = catRepository.getAll();
                    return getODataResponse(contentType, entitySet, cats);
                case ENTITY_SET_NAME_USERS:
                    List<Map<String, Object>> users = userRepository.getAll();
                    return getODataResponse(contentType, entitySet, users);
                default:
                    throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
            }
        }
        throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
    }

    private ODataResponse getODataResponse(String contentType, EdmEntitySet entitySet, List<Map<String, Object>> entityList) throws ODataException {
        URI serviceRoot = getContext().getPathInfo().getServiceRoot();
        ODataEntityProviderPropertiesBuilder oDataEntityProviderPropertiesBuilder
                = EntityProviderWriteProperties.serviceRoot(serviceRoot);
        return writeFeed(contentType, entitySet, entityList, oDataEntityProviderPropertiesBuilder.build());
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
