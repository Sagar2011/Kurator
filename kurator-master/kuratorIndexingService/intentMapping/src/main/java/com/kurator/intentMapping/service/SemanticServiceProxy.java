package com.kurator.intentMapping.service;

import com.kurator.library.model.SemanticDocument;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@FeignClient(name = "kuratorSemanticService")
public interface SemanticServiceProxy {
    @GetMapping("/concepts")
    ArrayList<String> getConcepts();

    @GetMapping("/terms")
    ArrayList<String> getTerms();

    @GetMapping("/contexts")
    HashMap<String, ArrayList<String>> getCommonIntentWithTerms(@RequestParam ArrayList<String> terms);

    @PostMapping("/documents")
    ResponseEntity<?> addDocumentsFromIndexingService(@RequestBody SemanticDocument document);

    @PostMapping("/terms")
    HashMap<String,Float> addTermsFromIndexingService(@RequestParam UUID docId);

    @GetMapping("/intents/terms")
    ArrayList<String> getTermsWithIntents(@RequestParam String intent);

    @GetMapping("/intents/index")
    ArrayList<String> getIntents();
}
