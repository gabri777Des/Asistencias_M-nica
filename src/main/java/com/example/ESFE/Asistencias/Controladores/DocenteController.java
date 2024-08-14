package com.example.ESFE.Asistencias.Controladores;

import com.example.ESFE.Asistencias.Entidades.Docente;
import com.example.ESFE.Asistencias.Entidades.Grupo;
import com.example.ESFE.Asistencias.Servicios.Interfaces.IDocenteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional; //sirve pa evitar un error y que puede ser null
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/Docentes")
public class DocenteController {

    @Autowired
    private IDocenteServices docenteServices;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<Docente> docentes = docenteServices.BuscarTodosPaginados(pageable);
        model.addAttribute("docentes", docentes);

        int totalPage = docentes.getTotalPages();
        if (totalPage > 0){
            List<Integer> pageNumber = IntStream.rangeClosed(1, totalPage)
                    .boxed()
                    .collect(Collectors.toList());

            model.addAttribute("pageNumber", pageNumber);
        }
        return "docente/index";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("docente", new Docente());
        return "docente/create";
    }
    @PostMapping("/save")
    public String save(Docente docente, BindingResult result, Model model, RedirectAttributes attributes){
        if (result.hasErrors()){
            model.addAttribute(docente);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "docente/create";
        }
        docenteServices.CrearOeditar(docente);
        attributes.addFlashAttribute("msg", "Docente creado correctamente");
        return "redirect:/Docentes";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model){
        Docente docente = docenteServices.BuscarPorId(id).get();
        model.addAttribute( "docente", docente);
        return "docente/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        Docente docente = docenteServices.BuscarPorId(id).get();
        model.addAttribute("docente", docente);
        return "docente/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model){
        Docente docente = docenteServices.BuscarPorId(id).get();
        model.addAttribute( "docente", docente);
        return "docente/delete";
    }

    @PostMapping("/delete")
    public String delete(Grupo grupo, RedirectAttributes attributes){
        docenteServices.EliminarPorId(grupo.getId());
        attributes.addFlashAttribute("msg", "Docente eliminado correctamente");
        return "redirect:/Docentes";
    }
}
