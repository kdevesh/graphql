package com.demo.graphql.service;

import com.demo.graphql.model.Book;
import com.demo.graphql.repository.BookRepository;
import com.demo.graphql.service.datafetcher.AllBooksDataFetcher;
import com.demo.graphql.service.datafetcher.BookDataFetcher;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@Getter
public class GraphQlService {
  @Value("classpath:books.graphql")
  private Resource resource;
  private GraphQL graphQL;
  @Autowired
  private AllBooksDataFetcher allBooksDataFetcher;
  @Autowired
  private BookDataFetcher bookDataFetcher;
  @Autowired
  BookRepository bookRepository;

  @PostConstruct
  private void loadSchema() throws IOException {
    loadData();
    //get the schema file
    File file = resource.getFile();

    //parse the schema
    TypeDefinitionRegistry typeResgistry = new SchemaParser().parse(file);
    RuntimeWiring runtimeWiring = buildRuntimeWiring();
    GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeResgistry, runtimeWiring);
    graphQL = GraphQL.newGraphQL(schema).build();
  }

  private void loadData() {
    Stream.of(
            new Book("123", "Book of Clouds", "Kindle Edition",
                new String[] {
                    "Chloe Aridjis"
                }, "Nov 2017"),
            new Book("124", "Cloud Arch & Engineering", "Orielly",
                new String[] {
                    "Peter", "Sam"
                }, "Jan 2015"),
            new Book("125", "Java 9 Programming", "Orielly",
                new String[] {
                    "Venkat", "Ram"
                }, "Dec 2016")
        )
        .forEach(book -> bookRepository.save(book));
  }

  private RuntimeWiring buildRuntimeWiring() {
    return RuntimeWiring.newRuntimeWiring()
        .type("Query", typeWiring ->
            typeWiring
                .dataFetcher("allBooks", allBooksDataFetcher)
                .dataFetcher("book", bookDataFetcher))
        .build();
  }
}
