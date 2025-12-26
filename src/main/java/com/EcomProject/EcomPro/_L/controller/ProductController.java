package com.EcomProject.EcomPro._L.controller;

import com.EcomProject.EcomPro._L.model.Product;
import com.EcomProject.EcomPro._L.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService service;
    @RequestMapping("/")
    public String greet(){return "Hello";}

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){

        List<Product> productsFromService = service.getAllProducts();

        if(productsFromService!=null)
            return new ResponseEntity<>(productsFromService,HttpStatus.OK);

        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


//        HttpStatus ok = HttpStatus.OK;

    }

    @GetMapping("/product/{prodId}")
    public ResponseEntity<Product> getProductById(@PathVariable int prodId){

        Product prod = service.getProductById(prodId);
        if(prod!=null) return new ResponseEntity<>(prod, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product prod, @RequestPart MultipartFile imageFile){

       try{
           Product prod1 = service.addProduct(prod, imageFile);
           return new ResponseEntity<>(prod1, HttpStatus.CREATED);
       }catch (Exception e){
           return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
       }

    }

    @GetMapping("/product/{prodId}/image")
    public ResponseEntity<byte[]> getImageByProdId(@PathVariable int prodId) {
        Product prod = service.getProductById(prodId);

        if (prod == null || prod.getImageData() == null) {
            return ResponseEntity.notFound().build();
        }

        MediaType mediaType;
        try {
            mediaType = MediaType.valueOf(prod.getImageType());
        } catch (Exception e) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }

        return ResponseEntity
                .ok()
                .contentType(mediaType)
                .body(prod.getImageData());
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String>updateProduct(@PathVariable int id, @RequestPart MultipartFile imageFile, @RequestPart Product product) throws IOException {

        Product prod = null;

        prod = service.updateProduct(id,imageFile, product);

        if(prod!=null) return new ResponseEntity<>("updated", HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("/product/{id}")

    public ResponseEntity<String> deleteProduct(@PathVariable int id){

    Product prod = service.getProductById(id);
    if(prod==null)return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
    else{
        service.deleteProduct(id);
        return  new ResponseEntity<>("Deleted", HttpStatus.OK);

    }


    }
@GetMapping("/product/search")
    public void searchProducts(String keyword){

        List<Product> products = service.searchProducts(keyword);
        return new ResponseEntity<>("OKAY", HttpStatus.OK);
    }





}
