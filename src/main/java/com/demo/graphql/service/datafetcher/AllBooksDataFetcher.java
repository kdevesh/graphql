package com.demo.graphql.service.datafetcher;

import com.demo.graphql.model.Book;
import com.demo.graphql.repository.BookRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllBooksDataFetcher implements DataFetcher<List<Book>> {

  @Autowired
  BookRepository bookRepository;
  @Override
  public List<Book> get(DataFetchingEnvironment dataFetchingEnvironment) {
    return bookRepository.findAll();
  }
}
