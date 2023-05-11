package com.springshopecommerce.controller.admin;

import com.springshopecommerce.dto.CategoryDTO;
import com.springshopecommerce.entity.CategoryEntity;
import com.springshopecommerce.repository.CategoryRepository;
import com.springshopecommerce.service.ICategoryService;
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
@RequestMapping("admin/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("add")
    public String add (Model model) {
        model.addAttribute("category", new CategoryDTO());
        return "admin/categories/addOrEdit";
    }
    @GetMapping ("edit/{id}")
    public ModelAndView edit (ModelMap model, @PathVariable ("id") Long id){
       Optional<CategoryDTO> category = Optional.ofNullable(categoryService.findByCategoryId(id));
       CategoryDTO dto = new CategoryDTO();
        if(category.isPresent()){
            dto = category.get();
            dto.setIsEdit(true);
            model.addAttribute("category", dto);
            return new ModelAndView("admin/categories/addOrEdit", model);
        }
        model.addAttribute("message","Category is not existed");
        return new ModelAndView("forward:/admin/categories", model);
    }


    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate (ModelMap model, @Valid @ModelAttribute("category")CategoryDTO categoryDTO , BindingResult result) {
        if(result.hasErrors()){
            return new ModelAndView("admin/categories/addOrEdit");
        }
        if(categoryDTO.getId()== null){
            CategoryDTO category = categoryService.findByCategoryName(categoryDTO.getName());
            if(category.getId() != null){
                model.addAttribute("message","Category already exists");
            }else {
                categoryService.createCategory(categoryDTO);
                model.addAttribute("message","Category is save");
            }
        }else {
            categoryService.updateCategory(categoryDTO);
            model.addAttribute("message","Category is update");
        }
        return new ModelAndView("forward:/admin/categories", model);
    }

    @RequestMapping ("")
    public String list (ModelMap model) {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "/admin/categories/list";
    }
    @GetMapping ("search")
    public String search (ModelMap model, @RequestParam(value = "name", required = false) String name) {
        List<CategoryDTO> list = null;
        if(StringUtils.hasText(name)){
            list = categoryService.findByNameContaining(name);
        }else {
            list = categoryService.getAllCategories();
        }
        model.addAttribute("categories", list);
        return "/admin/categories/search";
    }
    @GetMapping ("searchpaginated")
    public String search (ModelMap model, @RequestParam(value = "name", required = false) String name,
                          @RequestParam ("page") Optional<Integer> page,
                          @RequestParam ("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Pageable pageable = PageRequest.of(currentPage -1, pageSize, Sort.by("name"));
        Page<CategoryEntity> resultPage = null;

        if(StringUtils.hasText(name)){
            resultPage = categoryService.searchCategoryPaginged(name, pageable);
            model.addAttribute("name",name);
        }else {
            resultPage = categoryService.findAllPaginged(pageable);
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


        model.addAttribute("categoryPage", resultPage);
        return "/admin/categories/searchpaginated";
    }

    @GetMapping("delete/{id}")
    public ModelAndView delete (ModelMap model ,@PathVariable ("id") Long id){
       if(id != null) {
           categoryService.deleteCategoryById(id);
           model.addAttribute("message", "category is deleted !");
       }else {
           model.addAttribute("message", "category is not found !");

       }
        return new ModelAndView("forward:/admin/categories/search", model);
    }

}
