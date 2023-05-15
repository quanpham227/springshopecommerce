package com.springshopecommerce.controller.admin;

import com.springshopecommerce.dto.CategoryDTO;
import com.springshopecommerce.dto.ManufacturerDTO;
import com.springshopecommerce.entity.CategoryEntity;
import com.springshopecommerce.entity.ManufacturerEntity;
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
import org.springframework.web.multipart.MultipartFile;
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

    @RequestMapping ("")
    public String list (ModelMap model) {
        List<ManufacturerDTO> manufacturers = manufacturerService.getManufacturers();
        model.addAttribute("manufacturers", manufacturers);
        return "/admin/manufacturers/list";
    }

    @GetMapping("add")
    public String add (Model model) {
        model.addAttribute("manufacturer", new ManufacturerDTO());
        return "admin/manufacturers/addOrEdit";
    }
    @GetMapping ("edit/{id}")
    public ModelAndView edit (ModelMap model, @PathVariable ("id") Long id){
        Optional<ManufacturerDTO> manufacturer = Optional.ofNullable(manufacturerService.getManufacturerEntitiesById(id));
        ManufacturerDTO dto = new ManufacturerDTO();
        if(manufacturer.isPresent()){
            dto = manufacturer.get();
            dto.setIsEdit(true);
            model.addAttribute("manufacturer", dto);
            return new ModelAndView("admin/manufacturers/addOrEdit", model);
        }
        model.addAttribute("message","Category is not existed");
        return new ModelAndView("forward:/admin/manufacturers", model);
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate (ModelMap model, @Valid @ModelAttribute("manufacturer")ManufacturerDTO manufacturerDTO ,
                                       BindingResult result) {
        if(result.hasErrors()){
            return new ModelAndView("admin/manufacturers/addOrEdit");
        }
        if(manufacturerDTO.getId()== null){
            ManufacturerDTO manufacturer = manufacturerService.findByNameIgnoreCase(manufacturerDTO.getName());
            if(manufacturer.getId() != null){
                model.addAttribute("message","manufacturer already exists");
            }else {
                manufacturerService.createManufacturer(manufacturerDTO);
                model.addAttribute("message","Manufacturer is save");
            }
        }else {
            manufacturerService.updateManufacturer(manufacturerDTO);
            model.addAttribute("message","Category is update");
        }
        return new ModelAndView("forward:/admin/manufacturers", model);
    }

    @RequestMapping ("searchpaginated")
    public String search (ModelMap model, @RequestParam(value = "name", required = false) String name,
                          @RequestParam ("page") Optional<Integer> page,
                          @RequestParam ("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Pageable pageable = PageRequest.of(currentPage -1, pageSize, Sort.by("name"));
        Page<ManufacturerEntity> resultPage = null;

        if(StringUtils.hasText(name)){
            resultPage = manufacturerService.searchManufacturersPaginged(name, pageable);
            model.addAttribute("name",name);
        }else {
            resultPage = manufacturerService.findAllManufacturersPaginged(pageable);
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
        return "/admin/manufacturers/searchpaginated";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        if (id != null) {
            manufacturerService.deleteManufacturerEntityById(id);
            redirectAttributes.addFlashAttribute("message", "Manufacturer is deleted!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Manufacturer is not found!");
        }
        return "redirect:/admin/manufacturers/searchpaginated?name=";
    }

}
