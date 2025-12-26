package com.EcomProject.EcomPro._L.service;

import com.EcomProject.EcomPro._L.model.Product;
import com.EcomProject.EcomPro._L.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
@Autowired
private ProductRepository repo;


    public List<Product> getAllProducts(){

        return repo.findAll();
    }

    public Product getProductById(int prodId) {

       return repo.findById(prodId).orElse(null);
    }


    public Product addProduct(Product prod, MultipartFile imageFile) throws IOException {

        prod.setImageName(imageFile.getOriginalFilename());
        prod.setImageType(imageFile.getContentType());
        prod.setImageData(imageFile.getBytes());
       return  repo.save(prod);

    }

    public Product updateProduct(int id, MultipartFile imageFile, Product product) throws IOException {

        product.setImageData(imageFile.getBytes());
        product.setImageType(imageFile.getContentType());
        product.setName(imageFile.getName());
        return repo.save(product);



    }

    public void deleteProduct(int id) {

        repo.deleteById(id);

    }

    public List<Product> searchProducts(String keyword) {

        return repo.searchProducts(keyword);
    }
}
