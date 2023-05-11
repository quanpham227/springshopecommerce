package com.springshopecommerce.controller.admin;

import com.springshopecommerce.dto.ProductDTO;
import com.springshopecommerce.entity.ProductEntity;
import com.springshopecommerce.repository.ProductRepository;
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


    @GetMapping("add")
    public String add (Model model) {
        model.addAttribute("product", new ProductDTO());
        return "admin/products/addOrEdit";
    }
    @GetMapping ("edit/{id}")
    public ModelAndView edit (ModelMap model, @PathVariable ("id") Long id){
       Optional<ProductDTO> product = Optional.ofNullable(productService.findByProductId(id));
       ProductDTO dto = new ProductDTO();
        if(product.isPresent()){
            dto = product.get();
            dto.setIsEdit(true);
            model.addAttribute("product", dto);
            return new ModelAndView("admin/products/addOrEdit", model);
        }
        model.addAttribute("message","product is not existed");
        return new ModelAndView("forward:/admin/products", model);
    }


    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate (ModelMap model, @Valid @ModelAttribute("products")ProductDTO productDTO , BindingResult result) {
        if(result.hasErrors()){
            return new ModelAndView("admin/products/addOrEdit");
        }
        if(productDTO.getId()== null){
            ProductDTO product = productService.findByProductName(productDTO.getName());
            if(product.getId() != null){
                model.addAttribute("message","product already exists");
            }else {
                productService.createProduct(productDTO);
                model.addAttribute("message","product is save");
            }
        }else {
            productService.updateProduct(productDTO);
            model.addAttribute("message","product is update");
        }
        return new ModelAndView("forward:/admin/products", model);
    }

    @RequestMapping ("")
    public String list (ModelMap model) {
        List<ProductDTO> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "/admin/products/list";
    }
    @GetMapping ("search")
    public String search (ModelMap model, @RequestParam(value = "name", required = false) String name) {
        List<ProductDTO> list = null;
        if(StringUtils.hasText(name)){
            list = productService.findByNameContaining(name);
        }else {
            list = productService.getAllProducts();
        }
        model.addAttribute("products", list);
        return "/admin/products/search";
    }
    @GetMapping ("searchpaginated")
    public String search (ModelMap model, @RequestParam(value = "name", required = false) String name,
                          @RequestParam ("page") Optional<Integer> page,
                          @RequestParam ("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Pageable pageable = PageRequest.of(currentPage -1, pageSize, Sort.by("name"));
        Page<ProductEntity> resultPage = null;

        if(StringUtils.hasText(name)){
            resultPage = productService.searchProductPaginged(name, pageable);
            model.addAttribute("name",name);
        }else {
            resultPage = productService.findAllPaginged(pageable);
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
        return "/admin/products/searchpaginated";
    }

    @GetMapping("delete/{id}")
    public ModelAndView delete (ModelMap model ,@PathVariable ("id") Long id){
       if(id != null) {
           productService.deleteProductById(id);
           model.addAttribute("message", "product is deleted !");
       }else {
           model.addAttribute("message", "product is not found !");

       }
        return new ModelAndView("forward:/admin/products/search", model);
    }

}
