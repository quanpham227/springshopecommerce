package com.springshopecommerce.controller.admin;

import com.springshopecommerce.dto.CategoryDTO;
import com.springshopecommerce.dto.ManufacturerDTO;
import com.springshopecommerce.dto.ProductDTO;
import com.springshopecommerce.dto.ProductImageDTO;
import com.springshopecommerce.service.ICategoryService;
import com.springshopecommerce.service.IManufacturerService;
import com.springshopecommerce.service.IProductImageService;
import com.springshopecommerce.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("admin/products")
public class ProductController {

    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    IManufacturerService manufacturerService;

    @Autowired
    private IProductImageService productImageService;


    @ModelAttribute("categories")
    public List<CategoryDTO> getCategories(){
        return categoryService.getIdAndNameCategory();
    }
    @ModelAttribute("manufacturers")
    public List<ManufacturerDTO> getManufactures(){
        return manufacturerService.getIdAndNameAndLogoManufacturer();
    }

    @GetMapping("add")
    public String add (Model model) {
        ProductDTO product = new ProductDTO();
        model.addAttribute("product", product);
        return "admin/products/addOrEdit";
    }
    @GetMapping ("edit/{id}")
    public ModelAndView edit (ModelMap model, @PathVariable ("id") Long id){
        ProductDTO product = productService.findByProductId(id);
        product.setIsEdit(true);
        List<ProductImageDTO> products = productImageService.getProductImagesByProductId(id);

        model.addAttribute("product", product);
        model.addAttribute("products", products);

        return new ModelAndView("admin/products/addOrEdit", model);
    }


    @PostMapping("saveOrUpdate")
    public String saveOrUpdate ( @Valid @ModelAttribute("product")ProductDTO productDTO ,
                                BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()){
            return "admin/products/addOrEdit";
        }
        if(productDTO.getId()== null){
                productService.createProduct(productDTO);
            redirectAttributes.addFlashAttribute("message","Product is save");
        }else {
            productService.updateProduct(productDTO);
            redirectAttributes.addFlashAttribute("message","Product is update");
        }
        return "redirect:/admin/products/list?name=";
    }
    @GetMapping ("list")
    public String listProducts (ModelMap model, @RequestParam(value = "name", required = false) String name,
                          @RequestParam ("page") Optional<Integer> page,
                          @RequestParam ("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Pageable pageable = PageRequest.of(currentPage -1, pageSize, Sort.by("name"));
        Page<ProductDTO> resultPage = null;

        if(StringUtils.hasText(name)){
            resultPage = productService.findByNameContainsIgnoreCase(name, pageable);
            model.addAttribute("name",name);
        }else {
            resultPage = productService.findAllProductsPaginged(pageable);
        }

        int totalPages = resultPage.getTotalPages();
        if(totalPages > 0) {
            int start = Math.max(1, currentPage-2);
            int end = Math.min(currentPage + 2, totalPages);

            if(totalPages > 5) {
                if(end == totalPages) start = end -5;
                else if (start == 1) end = start + 5;
            }
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }


        model.addAttribute("productPage", resultPage);
        return "/admin/products/listProducts";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        productService.deleteProductById(id);
        redirectAttributes.addFlashAttribute("message", "product is deleted!");
        return "redirect:/admin/products/list?name=";
    }

}
