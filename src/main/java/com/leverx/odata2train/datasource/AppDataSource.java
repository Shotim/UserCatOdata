package com.leverx.odata2train.datasource;

import com.leverx.odata2train.model.Cat;
import com.leverx.odata2train.model.User;
import com.leverx.odata2train.repository.CatRepository;
import com.leverx.odata2train.repository.UserRepository;
import org.apache.olingo.odata2.annotation.processor.core.datasource.DataSource;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.exception.ODataNotFoundException;
import org.apache.olingo.odata2.api.exception.ODataNotImplementedException;

import java.util.List;
import java.util.Map;

import static com.leverx.odata2train.context.AppContext.getApplicationContext;
import static com.leverx.odata2train.model.constants.ModelConstants.ENTITY_SET_NAME_CATS;
import static com.leverx.odata2train.model.constants.ModelConstants.ENTITY_SET_NAME_USERS;
import static org.apache.olingo.odata2.api.exception.ODataNotFoundException.*;


public class AppDataSource implements DataSource {

    private final CatRepository catRepository;
    private final UserRepository userRepository;

    public AppDataSource() {
        catRepository = (CatRepository) getApplicationContext().getBean("catRepository");
        userRepository = (UserRepository) getApplicationContext().getBean("userRepository");
    }

    @Override
    public List<?> readData(EdmEntitySet entitySet) throws ODataNotFoundException, EdmException {
        String entitySetName = entitySet.getName();
        switch (entitySetName) {
            case ENTITY_SET_NAME_CATS:
                return catRepository.findAll();
            case ENTITY_SET_NAME_USERS:
                return userRepository.findAll();
            default:
                throw new ODataNotFoundException(ENTITY);
        }
    }

    @Override
    public Object readData(EdmEntitySet entitySet, Map<String, Object> keys) throws ODataNotFoundException, EdmException {
        String entitySetName = entitySet.getName();
        Long firstLayerEntityId = (Long) keys.get("Id");
        switch (entitySetName) {
            case ENTITY_SET_NAME_CATS:
                return catRepository.findById(firstLayerEntityId);
            case ENTITY_SET_NAME_USERS:
                return userRepository.findById(firstLayerEntityId);
            default:
                throw new ODataNotFoundException(ENTITY);
        }
    }

    @Override
    public Object readData(EdmFunctionImport function, Map<String, Object> parameters, Map<String, Object> keys) {
        return null;
    }

    @Override
    public Object readRelatedData(EdmEntitySet sourceEntitySet, Object sourceData, EdmEntitySet targetEntitySet, Map<String, Object> targetKeys) throws EdmException {
        String sourceEntityName = sourceEntitySet.getName();
        String targetEntityName = targetEntitySet.getName();
        switch (sourceEntityName) {
            case ENTITY_SET_NAME_USERS:
                User user = (User) sourceData;
                if (ENTITY_SET_NAME_CATS.equals(targetEntityName)) {
                    return user.getCats();
                }
            case ENTITY_SET_NAME_CATS:
                Cat cat = (Cat) sourceData;
                if (ENTITY_SET_NAME_USERS.equals(targetEntityName)) {
                    return cat.getOwner();
                }
        }

        return null;
    }

    @Override
    public BinaryData readBinaryData(EdmEntitySet entitySet, Object mediaLinkEntryData) throws ODataNotImplementedException {
        throw new ODataNotImplementedException();
    }

    @Override
    public Object newDataObject(EdmEntitySet entitySet) throws ODataNotImplementedException {
        throw new ODataNotImplementedException();
    }

    @Override
    public void writeBinaryData(EdmEntitySet entitySet, Object mediaLinkEntryData, BinaryData binaryData) throws ODataNotImplementedException {
        throw new ODataNotImplementedException();
    }

    @Override
    public void deleteData(EdmEntitySet entitySet, Map<String, Object> keys) throws ODataNotImplementedException {
        throw new ODataNotImplementedException();
    }

    @Override
    public void createData(EdmEntitySet entitySet, Object data) throws ODataNotImplementedException {
        throw new ODataNotImplementedException();
    }

    @Override
    public void deleteRelation(EdmEntitySet sourceEntitySet, Object sourceData, EdmEntitySet targetEntitySet, Map<String, Object> targetKeys) throws ODataNotImplementedException {
        throw new ODataNotImplementedException();
    }

    @Override
    public void writeRelation(EdmEntitySet sourceEntitySet, Object sourceData, EdmEntitySet targetEntitySet, Map<String, Object> targetKeys) throws ODataNotImplementedException {
        throw new ODataNotImplementedException();
    }
}
