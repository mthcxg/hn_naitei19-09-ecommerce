package com.example.ecommerce.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecommerce.dao.ProductRepository;
import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.utils.ImageUpload;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageUpload imageUpload;

    /*Admin*/
    @Override
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtoList = transfer(products);
        return productDtoList;
    }

    @Override
    public Product save(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product product = new Product();
            if(imageProduct == null){
                product.setImage(null);;
            }else{
                if(imageUpload.uploadImage(imageProduct)){
                    System.out.println("Upload successfully");
                }
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));;
            }
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCategory(productDto.getCategory());
            product.setPrice(productDto.getPrice());
            product.setBrand(productDto.getBrand());
            product.setBuy_count(productDto.getBuy_count());
            product.setQuantity(productDto.getQuantity());
            product.setIs_activated(true);
            product.setIs_deleted(false);
            return productRepository.save(product);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Product update(MultipartFile imageProduct ,ProductDto productDto) {
        try {
            Product product = productRepository.getReferenceById(productDto.getId());
            if(imageProduct == null){
                product.setImage(product.getImage());
            }else{
                if(imageUpload.checkExisted(imageProduct) == false){
                    imageUpload.uploadImage(imageProduct);
                }
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setBrand(productDto.getBrand());
            product.setBuy_count(productDto.getBuy_count());
            product.setQuantity(productDto.getQuantity());
            product.setCategory(productDto.getCategory());
            return productRepository.save(product);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void deleteById(Long id) {
        Product product = productRepository.getReferenceById(id);
        product.setIs_deleted(true);
        product.setIs_activated(false);
        productRepository.save(product);
    }

    @Override
    public void enableById(Long id) {
        Product product = productRepository.getReferenceById(id);
        product.setIs_activated(true);
        product.setIs_deleted(false);
        productRepository.save(product);
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = productRepository.getReferenceById(id);
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setQuantity(product.getQuantity());
        productDto.setCategory(product.getCategory());
        productDto.setPrice(product.getPrice());
        productDto.setBuy_count(product.getBuy_count());
        productDto.setBrand(product.getBrand());
        productDto.setImage(product.getImage());
        productDto.setDeleted(product.isIs_deleted());
        productDto.setActivated(product.isIs_activated());
        return productDto;
    }

    @Override
    public Page<ProductDto> pageProducts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<ProductDto> products = transfer(productRepository.findAll());
        Page<ProductDto> productPages = toPage(products, pageable);
        return productPages;
    }

    @Override
    public Page<ProductDto> searchProducts(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<ProductDto> productDtoList = transfer(productRepository.searchProducts(keyword));
        Page<ProductDto> products = toPage(productDtoList, pageable);
        return products;
    }



    private Page toPage(List<ProductDto> list , Pageable pageable){
        if(pageable.getOffset() >= list.size()){
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size()
                : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }


    private List<ProductDto> transfer(List<Product> products){
        List<ProductDto> productDtoList = new ArrayList<>();
        for(Product product : products){
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setQuantity(product.getQuantity());
            productDto.setCategory(product.getCategory());
            productDto.setPrice(product.getPrice());
            productDto.setBuy_count(product.getBuy_count());
            productDto.setBrand(product.getBrand());
            productDto.setImage(product.getImage());
            productDto.setDeleted(product.isIs_deleted());
            productDto.setActivated(product.isIs_activated());
            productDtoList.add(productDto);
        }
        return productDtoList;
    }


    /*Customer*/

    @Override
    public List<Product> getAllProducts() {
        return productRepository.getAllProduct();
    }

    @Override
    public List<Product> listViewProducts() {
        return productRepository.listViewProduct();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.getReferenceById(id);
    }

    

    @Override
    public List<Product> getProductsInCategory(Long categoryId) {
        return productRepository.getProductByCategoryId(categoryId);
    }

	@Override
	public List<Product> getRelatedProducts(Long categoryId) {
		// TODO Auto-generated method stub
		return null;
	}


	
}