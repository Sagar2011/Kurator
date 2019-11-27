package com.kurator.kuratorUserService.service;

import com.kurator.kuratorUserService.model.ResultModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@FeignClient(name = "kuratorSemanticService")
public interface SemanticServiceProxy {
    @GetMapping("/documents/users")
    ArrayList<ResultModel> getDocuments(@RequestParam ArrayList<String> documentId);

    @GetMapping("/concepts/user")
    ArrayList<String> getReviewedConcepts();

    @GetMapping("/concept/documents")
    ArrayList<ResultModel> getDocumentByConcept(@RequestParam String concept);
}
