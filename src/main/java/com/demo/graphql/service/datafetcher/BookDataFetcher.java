package com.demo.graphql.service.datafetcher;

import com.demo.graphql.model.Book;
import com.demo.graphql.repository.BookRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookDataFetcher implements DataFetcher<Book> {
  @Autowired
  BookRepository bookRepository;
  @Override
  public Book get(DataFetchingEnvironment dataFetchingEnvironment) {
    String isn = dataFetchingEnvironment.getArgument("id");
    return bookRepository.findById(isn).get();
  }
}
