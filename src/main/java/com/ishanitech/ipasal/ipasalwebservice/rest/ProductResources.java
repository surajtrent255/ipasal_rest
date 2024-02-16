package com.ishanitech.ipasal.ipasalwebservice.rest;

import com.ishanitech.ipasal.ipasalwebservice.FileUtils.FileUploder;
import com.ishanitech.ipasal.ipasalwebservice.Services.ProductService;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductCategoryDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.Response;
import com.ishanitech.ipasal.ipasalwebservice.dto.SearchResult;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.BadRequestException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomFileSystemException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomSqlException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Yoomes <yoomesbhujel@gmail.com>
 */

@RestController
@RequestMapping("api/v1/products")
public class ProductResources {
    Logger logger = LoggerFactory.getLogger(ProductResources.class);
    private ProductService productService;

    @Value("${com.ipasal.fileUploadingDirectory}")
    private String UPLOAD_DIR;

    @Autowired
    FileUploder fileUploder;

    @Autowired
    public ProductResources(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Response<?> addProducts(@RequestBody ProductDTO productDTO) {
        Integer result = null;
        try {
            result = productService.addProduct(productDTO);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong while getting data from database.");
        }

        return Response.ok(result, HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    @RequestMapping(method = RequestMethod.GET)
    public Response<?> getAllProducts() {

        List<ProductDTO> productList = null;
        try {
            productList = productService.getAllProducts();

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong while getting data from database.");
        }

        if (productList != null) {
            return Response.ok(productList, HttpStatus.OK.value(), HttpStatus.OK.name());
        } else {
            throw new ResourceNotFoundException("No products found in database.");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{productId}")
    public Response<?> getProductById(@PathVariable("productId") Integer productId) {
        ProductDTO product = null;
        try {
            product = productService.getProductById(productId);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CustomSqlException("Someting went wrong while getting data from database");
        }

        if (product != null) {
            return Response.ok(Arrays.asList(product), HttpStatus.OK.value(), HttpStatus.OK.name());
        } else {
            throw new ResourceNotFoundException("No product found.");
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{productId}")
    public Response<?> updateProduct(@PathVariable("productId") Integer productId, @RequestBody ProductDTO productDTO) {
        try {
            productService.updateProduct(productId, productDTO);
            return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "Product info update successfully!");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong updating product info.");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{productId}")
    public Response<?> removeProduct(@PathVariable("productId") Integer productId) {
        try {
            productService.removeProduct(productId);
            return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "Product has been deleted successfully");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong deleting product from database!");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/productCategory")
    public Response<?> addProductCategory(@RequestBody ProductCategoryDTO productCategoryDTO) {
        try {
            productService.addProductCategory(productCategoryDTO);
            return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "Product category is added.");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong while getting data from database!");
        }
    }

    @GetMapping("/category/{categoryId}")
    public Response<?> getProductsByCategoryId(@PathVariable("categoryId") Integer categoryId,
                                               HttpServletRequest request) {
        List<ProductDTO> products = new ArrayList<>();
        try {
            products = productService.getProductsByCategory(categoryId, request);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong while getting data from databse!");
        }

        if (products != null && products.size() > 0) {
            return Response.ok(products, HttpStatus.OK.value(), HttpStatus.OK.name());
        }

        throw new ResourceNotFoundException("No product found in this category.");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload/{productId}")
    public Response<?> uploadImage(@RequestParam("file") MultipartFile[] imageFiles,
                                   @RequestParam("fileName") String[] fileName, @PathVariable("productId") Integer productId) {

        String returnedFile = "";
        if ((imageFiles != null) && imageFiles.length == fileName.length) {
            try {
                for (int i = 0; i < imageFiles.length; i++) {
                    MultipartFile file = imageFiles[i];
                    returnedFile = fileUploder.saveUploadedFiles(Arrays.asList(file), fileName[i]);
                    productService.addProductImage(productId, returnedFile);
                }

                return Response.ok("Images are inserted", HttpStatus.OK.value(), HttpStatus.OK.name());
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new CustomFileSystemException(e.getMessage());
            }

        } else {
            throw new BadRequestException("Some informations are missing in your request object!",
                    HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/featured")
    public Response<?> getFeaturedProducts() {
        List<ProductDTO> featuredProducts = new ArrayList<>();
        try {
            featuredProducts = productService.getFeaturedProducts();

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException(e.getMessage());
            // return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "The file is
            // not valid");
        }

        if (featuredProducts != null && featuredProducts.size() > 0) {
            return Response.ok(featuredProducts, HttpStatus.OK.value(), HttpStatus.OK.name());
        } else {
            throw new ResourceNotFoundException("Nothing found for featured products.");
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/featured/{productId}")
    public Response<?> featureProducts(@PathVariable Integer productId) {
        try {
            productService.featureProduct(productId);
            return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), HttpStatus.OK.name());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong while getting data from featured product");
            // return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "The file is
            // not valid");
        }
    }

    @GetMapping("/parentCategory/{parentCategoryId}")
    public Response<?> getProductsByParentCategoryId(@PathVariable("parentCategoryId") Integer parentCategoryId,
                                                     HttpServletRequest request) {
        List<ProductDTO> products = new ArrayList<>();
        try {
            products = productService.getProductsByParentCategoryId(parentCategoryId, request);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong while gettting data from database!");
        }

        if (products != null && products.size() > 0) {
            return Response.ok(products, HttpStatus.OK.value(), HttpStatus.OK.name());
        }

        throw new ResourceNotFoundException("Couldn't find product in this category.");

    }

    @GetMapping("/search")
    public Response<?> searchProduct(@RequestParam("searchKey") String searchKey, HttpServletRequest request) {
        List<ProductDTO> searchResults = new ArrayList<>();
        try {
            searchResults = productService.searchProduct(request);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CustomSqlException("Something went wrong while getting data from databse.");
        }

        if (searchResults != null && searchResults.size() > 0) {
            return Response.ok(searchResults, HttpStatus.OK.value(), HttpStatus.OK.name());
        }

        throw new ResourceNotFoundException(
                "Couldn't find product with searchKey: " + request.getParameter("searchKey"));
        // return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "No data
        // found.");
    }

    @GetMapping("/search/all")
    public Response<?> wholeSearchProduct(@RequestParam("searchKey") String searchKey,
                                          HttpServletRequest request) {
        SearchResult searchResults = new SearchResult();

        try {
            searchResults = productService.getWholeSearchProducts(request);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CustomSqlException("Something went wrong while getting data from databse.");
        }

        if (searchResults != null && !searchResults.getCategories().isEmpty() && !searchResults.getProducts().isEmpty()) {
            return Response.ok(searchResults, HttpStatus.OK.value(), HttpStatus.OK.name());
        }

        throw new ResourceNotFoundException(
                "Couldn't find product with searchKey: " + searchKey);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/sales")
    public Response<?> getSaleProductsforIndex() {
        List<ProductDTO> saleProducts = new ArrayList<>();
        try {
            saleProducts = productService.getSaleProductsforIndex();

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException(e.getMessage());
        }

        if (saleProducts != null && saleProducts.size() > 0) {
            return Response.ok(saleProducts, HttpStatus.OK.value(), HttpStatus.OK.name());
        } else {
            throw new ResourceNotFoundException("No sale products at the moment.");
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/allSales")
    public Response<?> getAllSaleProducts(HttpServletRequest request) {
		List<ProductDTO> saleProducts;
		try {
			saleProducts = productService.getAllSaleProducts(request);
		} catch (Exception ex) {
			throw new CustomSqlException("Something went wrong while getting products from database!");
		}
		if (saleProducts != null && saleProducts.size() > 0) {
			return Response.ok(saleProducts, HttpStatus.OK.value(), HttpStatus.OK.name());
		}
		if(request.getParameter("action").equals("first")) {
			throw new ResourceNotFoundException("Currently there are no sale products in database!");
		}
		throw new ResourceNotFoundException("No more data found! :(");
    }
    
}
