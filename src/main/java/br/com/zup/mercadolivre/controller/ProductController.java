package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.NewProductImageRequest;
import br.com.zup.mercadolivre.controller.request.NewProductRequest;
import br.com.zup.mercadolivre.controller.response.CreatedProductResponse;
import br.com.zup.mercadolivre.exception.NotFoundException;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.CategoryRepository;
import br.com.zup.mercadolivre.repository.ProductRepository;
import br.com.zup.mercadolivre.util.FileUploader;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final FileUploader uploader;

    public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository, FileUploader uploader) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.uploader = uploader;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CreatedProductResponse> create(@RequestBody @Valid NewProductRequest request, @AuthenticationPrincipal User owner) {
        var product = productRepository.save(request.toModel(categoryRepository, owner));
        return ResponseEntity.ok().body(new CreatedProductResponse(product));
    }

    @PostMapping("/{id}/images")
    @Transactional
    public ResponseEntity<?> addImages(@PathVariable Long id, @Valid NewProductImageRequest request) {
        List<String> links = uploader.send(request.getImages());
        System.out.println(links);
        var product = productRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        product.addImages(links);
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }
}
