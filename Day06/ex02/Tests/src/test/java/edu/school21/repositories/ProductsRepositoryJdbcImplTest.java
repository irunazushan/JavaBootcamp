package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import java.util.NoSuchElementException;
import javax.sql.DataSource;
import java.util.*;

public class ProductsRepositoryJdbcImplTest {
    ProductsRepositoryJdbcImpl pr;
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(1L, "IPod", 4500L);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(1L, "Sony MP3 Player", 7200L);
    final Product EXPECTED_SAVED_PRODUCT = new Product(5L, "PS5", 65500L);
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = new ArrayList<>(
            Arrays.asList(
                    new Product(0L, "Keyword", 3000L),
                    new Product(1L, "IPod", 4500L),
                    new Product(2L, "Laptop", 53500L),
                    new Product(3L, "Screen", 12500L),
                    new Product(4L, "IPhone", 100000L))
    );

    @BeforeEach
    public void init() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL)
                .setScriptEncoding("UTF-8")
                .addScript("schema.sql")
                .addScripts("data.sql")
                .build();

        pr = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @Test
    public void updateShouldBeExpected() {
        Product updatedProduct = new Product(1L, "Sony MP3 Player", 7200L);
        pr.update(updatedProduct);
        Assertions.assertEquals(EXPECTED_UPDATED_PRODUCT, pr.findById(1L).get());
    }

    @Test
    public void findByIdProductShouldBeExpected() {
        Optional<Product> optional = pr.findById(1L);
        Product product = null;
        if (optional.isPresent()) {
            product = optional.get();
        }
        Assertions.assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, product);
    }

    @Test
    public void savedProductShouldBeExpected() {
        Product product = new Product(null, "PS5", 65500L);
        pr.save(product);
        Assertions.assertEquals(EXPECTED_SAVED_PRODUCT, pr.findById(5L).get());
    }

    @Test
    public void deleteProductShouldBeDeleted() {
        pr.delete(3L);
        Assertions.assertThrows(NoSuchElementException.class,  () -> pr.findById(3L).get());
    }

    @Test
    public void findAllShouldBeExpected() {
        List<Product> products = pr.findAll();
        Assertions.assertEquals(EXPECTED_FIND_ALL_PRODUCTS, products);
    }
}
