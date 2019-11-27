package com.kurator.intentMapping.intentMappingProcessor;

import java.util.UUID;

public interface IRelationMappingService {

    void updateDocumentTitle(UUID docId);

    void updateDocumentIntent(UUID docId);

    void gettingIntentFromTerms(UUID docId);

    void updateDocumentInsideSemanticLibrary(UUID docId);

    void updateConfidenceRating(UUID docId);

    void updateStatus(UUID docId);
}
