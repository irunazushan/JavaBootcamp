package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private final DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Optional<Product> findById(Long id) {
        String query = "SELECT * from product WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                long price = rs.getLong("price");
                Product product = new Product(id, name, price);
                return Optional.of(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public List<Product> findAll() {
        List <Product> products = null;
        String query = "SELECT * from product";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            products = new ArrayList<>();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                long price = rs.getLong("price");
                Product product = new Product(id, name, price);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public void update(Product product) {
        String query = "UPDATE product SET name=?, price=? WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(3, product.getId());
            ps.setString(1, product.getName());
            ps.setLong(2, product.getPrice());
            if (ps.executeUpdate() > 0) {
                System.out.println("Product with id " + product.getId() + " updated successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(Product product) {
        String query = "INSERT INTO product (name, price) VALUES (?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, product.getName());
            ps.setLong(2, product.getPrice());
            product.setId(getLastProductId() + 1);
            if (ps.executeUpdate() > 0) {
                System.out.println("New product with id " + product.getId() + " inserted successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        String query = "DELETE FROM product WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            if (ps.executeUpdate() > 0) {
                System.out.println("Product with id " + id + " deleted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private long getLastProductId() {
        String query = "SELECT id FROM product ORDER BY id DESC LIMIT 1";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1L;
    }

}
