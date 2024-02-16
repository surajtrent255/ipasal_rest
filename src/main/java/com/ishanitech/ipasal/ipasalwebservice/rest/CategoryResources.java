package com.ishanitech.ipasal.ipasalwebservice.rest;

import com.ishanitech.ipasal.ipasalwebservice.FileUtils.FileUploder;
import com.ishanitech.ipasal.ipasalwebservice.Services.CategoryService;
import com.ishanitech.ipasal.ipasalwebservice.dto.CategoryDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.ProductDTO;
import com.ishanitech.ipasal.ipasalwebservice.dto.Response;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.BadRequestException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomFileSystemException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.CustomSqlException;
import com.ishanitech.ipasal.ipasalwebservice.exception.model.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequestMapping("api/v1/category")
@RestController
public class CategoryResources {
	private final Logger logger = LoggerFactory.getLogger(CategoryResources.class);
	@Autowired
	private FileUploder fileUploder;
	private CategoryService categoryService;

	@Autowired
	public CategoryResources(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public Response<?> getAllCategories() {
		List<CategoryDTO> categories;
		try {
			categories = categoryService.getAllCategories();
			
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new CustomSqlException("Couldn't get categories from database.");
		}

		if (categories != null && categories.size() > 0) {
			return Response.ok(categories, HttpStatus.OK.value(), "Success");
		}

		throw new ResourceNotFoundException("Currently, there are no categories in database.");
	}
	

	@RequestMapping(method = RequestMethod.POST)
	public Response<?> addCategory(@RequestBody CategoryDTO categoryDTO) {
		Integer result = null;
		try {
			result = categoryService.addCategory(categoryDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Something went wrong while adding category!");
		}
		return Response.ok(result, HttpStatus.OK.value(), HttpStatus.OK.name());

	}

	@RequestMapping(method = RequestMethod.GET, value = "/parent/{parentId}")
	public Response<?> getCategoryByParentId(@PathVariable Integer parentId) {
		List<CategoryDTO> categories;
		try {
			categories = categoryService.getCategoryByParentId(parentId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException(
					"Something went wrong while getting category");
		}

		if (categories != null && categories.size() > 0) {
			return Response.ok(categories, HttpStatus.OK.value(), HttpStatus.OK.name());

		}

		throw new ResourceNotFoundException("No category found.");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/image/{parentId}")
	public Response<?> getCategoryImagesById(@PathVariable Integer parentId) {
		List<CategoryDTO> categoryDTOS;
		try {
			categoryDTOS = categoryService.getCategoryImages(parentId);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException(
					"Something went wrong while getting category Images.");
		}

		if (categoryDTOS != null) {
			return Response.ok(categoryDTOS, HttpStatus.OK.value(), HttpStatus.OK.name());

		}

		throw new ResourceNotFoundException("No category found.");
	}


	@RequestMapping(method = RequestMethod.DELETE, value = "/{categoryId}")
	public Response<?> deleteCategory(@PathVariable Integer categoryId) {
		try {
			categoryService.deleteCategory(categoryId);
			return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "Category has been removed successfully.");
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Unable to delete category!");
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/featured")
	public Response<?> getFeaturedCategories() {
		List<CategoryDTO> featuredCategories;
		try {
			featuredCategories = categoryService.getFeaturedCategories();
		} catch(NullPointerException nex) {
			throw new ResourceNotFoundException("No data for featured category is found.");
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Unable to get featured categories!");
		}

		return Response.ok(featuredCategories, HttpStatus.OK.value(), HttpStatus.OK.name());
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/offered")
	public Response<?> getOfferedCategories() {
		List<CategoryDTO> offeredCategories;
		try {
			offeredCategories = categoryService.getOfferedCategories();
		} catch(NullPointerException nex) {
			throw new ResourceNotFoundException("No data for offered category is found.");
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Unable to get offered categories!");
		}

		return Response.ok(offeredCategories, HttpStatus.OK.value(), HttpStatus.OK.name());
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/featured/{categoryId}")
	public Response<?> featureCategory(@PathVariable Integer categoryId) {
		try {
			categoryService.featureCategory(categoryId);
			return Response.ok(new ArrayList<>(), HttpStatus.OK.value(),
					"Made this selected category a featured category!");
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Unable to make this category a featured category!");
		}
	}
	
	@GetMapping("/{categoryId}/product")
	public Response<?> searchProductInCategory(@PathVariable("categoryId") Integer categoryId, @RequestParam("searchKey") String searchKey) {
		try {
			List<ProductDTO> productResults = categoryService.searchProductInCategory(categoryId, searchKey);
			
			return Response.ok(productResults, HttpStatus.OK.value(), HttpStatus.OK.name());
		} catch(Exception e) {
			logger.error(e.getMessage());
			throw new CustomSqlException("Did not find any related products");
		}
	}

	//Getting the products between two price range
    @GetMapping("/{categoryId}/product/rate")
    public Response<?> getProductsBetweenRange(@PathVariable("categoryId") Integer categoryId,
                                               @RequestParam("min") Float minRate, @RequestParam("max") Float maxRate){
	    try{
	        List<ProductDTO> productList = categoryService.getProductsBetweenRange(categoryId, minRate, maxRate);
	        return Response.ok(productList, HttpStatus.OK.value(), HttpStatus.OK.name());
        }catch (Exception e){
	        logger.error(e.getMessage());
	        throw new CustomSqlException("No products available between that range");
        }
    }

	//adding the offer product images
	@RequestMapping(method = RequestMethod.POST, value = "/featured/upload/{categoryId}")
	public Response<?> uploadCategoryImage(@RequestParam("file") MultipartFile[] imageFile,
											   @RequestParam("fileName") String[] fileNames,
											   @PathVariable("categoryId") Integer categoryId){
		logger.info("Image upload called.");
		String fileReturned = "";
		String fileOddImage = "";
		String fileEvenImage = "";
		String bannerReturned = "";
		if (imageFile != null && imageFile.length>0) {
			try {
				for (int i = 0; i <imageFile.length; i++) {
					MultipartFile file = imageFile[i];
					if (i == 0) {
						fileReturned = fileUploder.saveUploadedFiles(Arrays.asList(file), fileNames[i]);
						String imageName = fileNames[i];
						if (fileReturned != null) {
							fileOddImage = fileUploder.resizeUploadedFiles(fileReturned, "odd");
							fileEvenImage = fileUploder.resizeUploadedFiles(fileReturned, "even");
						}
					}else {
						bannerReturned = fileUploder.saveUploadedFiles(Arrays.asList(file), fileNames[1]);
					}
				}
				categoryService.uploadCategoryImage(categoryId, fileReturned, fileOddImage, fileEvenImage, bannerReturned);

				return Response.ok("Category Image is inserted", HttpStatus.OK.value(), HttpStatus.OK.name());
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new CustomFileSystemException(e.getMessage());
			}
		} else {
			throw new BadRequestException("Some information are missing in your request object!",
					HttpStatus.BAD_REQUEST);
		}


	}

	// Retrieving all parent category
	@GetMapping("/parent/all")
	public Response<?> getAllParentCategory(){
		try{
			List<CategoryDTO> allParentCategory = categoryService.getAllParentCategory();
			return Response.ok(allParentCategory, HttpStatus.OK.value(), HttpStatus.OK.name());
		}catch (Exception e){
			throw new CustomSqlException("No Parent Categories retrieved.");
		}
	}
	
	// Retrieving category by its category Id
	@GetMapping("/{categoryId}")
	public Response<?> getCategoryByCategoryId(@PathVariable("categoryId") Integer categoryId) {
		CategoryDTO category = null;
        try {
            category = categoryService.getCategoryByCategoryId(categoryId);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CustomSqlException("Someting went wrong while getting data from database");
        }

        if (category != null) {
            return Response.ok(Arrays.asList(category), HttpStatus.OK.value(), HttpStatus.OK.name());
        } else {
            throw new ResourceNotFoundException("Not category found.");
        }
	}
	
	
	@DeleteMapping("/delete")
	public Response<?> deleteParentandChildCategories(@RequestBody List<Integer> categoryIds) {
        try {
            categoryService.deleteParentandChildCategories(categoryIds);
            return Response.ok(new ArrayList<>(), HttpStatus.OK.value(), "The categories has been deleted successfully");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new CustomSqlException("Something went wrong deleting product from database!");
        }
		
	}
	
}
