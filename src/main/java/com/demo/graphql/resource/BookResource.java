package com.demo.graphql.resource;

import com.demo.graphql.service.GraphQlService;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/books")
@RestController
public class BookResource {
  @Autowired
  GraphQlService graphQlService;

  @PostMapping
  public ResponseEntity<ExecutionResult> getAllBooks(@RequestBody String query){
    ExecutionResult result = graphQlService.getGraphQL().execute(query);
    return ResponseEntity.ok(result);
  }
}
