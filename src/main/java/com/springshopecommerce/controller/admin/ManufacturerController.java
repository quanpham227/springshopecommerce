package com.springshopecommerce.controller.admin;

import com.springshopecommerce.dto.ManufacturerDTO;
import com.springshopecommerce.service.IManufacturerService;
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
@RequestMapping("admin/manufacturers")
public class ManufacturerController {
    @Autowired
    private IManufacturerService manufacturerService;


    @GetMapping("add")
    public String add (Model model) {
        model.addAttribute("manufacturer", new ManufacturerDTO());
        return "admin/manufacturers/addOrEdit";
    }
    @GetMapping ("edit/{id}")
    public ModelAndView edit (ModelMap model, @PathVariable ("id") Long id){
        ManufacturerDTO manufacturer = manufacturerService.getManufacturerEntitiesById(id);
        manufacturer.setIsEdit(true);
        model.addAttribute("manufacturer", manufacturer);
        return new ModelAndView("admin/manufacturers/addOrEdit", model);
    }

    @PostMapping("saveOrUpdate")
    public String saveOrUpdate ( @Valid @ModelAttribute("manufacturer")ManufacturerDTO manufacturerDTO ,
                                       BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()){
            return "admin/manufacturers/addOrEdit";
        }
        if(manufacturerDTO.getId()== null){
            manufacturerService.createManufacturer(manufacturerDTO);
            redirectAttributes.addFlashAttribute("message","Manufacturer is save");
        }else {
            manufacturerService.updateManufacturer(manufacturerDTO);
            redirectAttributes.addFlashAttribute("message","Category is update");
        }
        return "redirect:/admin/manufacturers/list?name=";
    }

    @RequestMapping ("list")
    public String search (ModelMap model, @RequestParam(value = "name", required = false) String name,
                          @RequestParam ("page") Optional<Integer> page,
                          @RequestParam ("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Pageable pageable = PageRequest.of(currentPage -1, pageSize, Sort.by("name"));
        Page<ManufacturerDTO> resultPage = null;

        if(StringUtils.hasText(name)){
            resultPage = manufacturerService.searchManufacturersByName(name, pageable);
            model.addAttribute("name",name);
        }else {
            resultPage = manufacturerService.findAllManufacturers(pageable);
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


        model.addAttribute("manufacturerPage", resultPage);
        return "/admin/manufacturers/list";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        manufacturerService.deleteManufacturerEntityById(id);
        redirectAttributes.addFlashAttribute("message", "Manufacturer is deleted!");
        return "redirect:/admin/manufacturers/list?name=";
    }

}
