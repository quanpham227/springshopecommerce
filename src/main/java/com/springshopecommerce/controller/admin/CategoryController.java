package com.springshopecommerce.controller.admin;

import com.springshopecommerce.dto.CategoryDTO;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        CategoryDTO category = categoryService.findByCategoryId(id);
        category.setIsEdit(true);
        model.addAttribute("category", category);
        return new ModelAndView("admin/categories/addOrEdit", model);

    }


    @PostMapping("saveOrUpdate")
    public String saveOrUpdate ( @Valid @ModelAttribute("category")CategoryDTO categoryDTO ,
                                      BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()){
            return "admin/categories/addOrEdit";
        }
        if(categoryDTO.getId()== null){
            categoryService.createCategory(categoryDTO);
            redirectAttributes.addFlashAttribute("message","Category is save");
        }else {
            categoryService.updateCategory(categoryDTO);
            redirectAttributes.addFlashAttribute("message","Category is update");
        }
        return "redirect:/admin/categories/list?name=";
    }

    @GetMapping ("list")
    public String search (ModelMap model, @RequestParam(value = "name", required = false) String name,
                          @RequestParam ("page") Optional<Integer> page,
                          @RequestParam ("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Pageable pageable = PageRequest.of(currentPage -1, pageSize, Sort.by("id"));
        Page<CategoryDTO> resultPage = null;

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
        return "/admin/categories/list";
    }

    @GetMapping("delete/{id}")
    public String delete ( @PathVariable ("id") Long id, RedirectAttributes redirectAttributes){
        categoryService.deleteCategoryById(id);
        redirectAttributes.addFlashAttribute("message","category is delete");
        return "redirect:/admin/categories/list?name=";
    }

}
