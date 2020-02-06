package com.leverx.odata2train.odata.service.factory;

import com.leverx.odata2train.odata.processor.AppODataSingleProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.olingo.odata2.annotation.processor.core.edm.AnnotationEdmProvider;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;

@Slf4j
public class AppODataServiceFactory extends ODataServiceFactory {

    private static final String MODEL_PACKAGE = "com.leverx.odata2train.model";

    @Override
    public ODataService createService(ODataContext ctx) throws ODataException {
        EdmProvider edmProvider = new AnnotationEdmProvider(MODEL_PACKAGE);
        ODataSingleProcessor oDataSingleProcessor = new AppODataSingleProcessor();
        return createODataSingleProcessorService(edmProvider, oDataSingleProcessor);
    }
}
