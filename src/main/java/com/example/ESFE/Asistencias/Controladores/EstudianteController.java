package com.example.ESFE.Asistencias.Controladores;

import com.example.ESFE.Asistencias.Entidades.Estudiante;
import com.example.ESFE.Asistencias.Servicios.Interfaces.IEstudianteServices;
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
@RequestMapping("/Estudiantes")
public class EstudianteController {
    @Autowired
    private IEstudianteServices estudianteServices;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<Estudiante> estudiantes = estudianteServices.BuscarTodosPaginados(pageable);
        model.addAttribute("estudiantes", estudiantes);

        int totalPage = estudiantes.getTotalPages();
        if (totalPage > 0){
            List<Integer> pageNumber = IntStream.rangeClosed(1, totalPage)
                    .boxed()
                    .collect(Collectors.toList());

            model.addAttribute("pageNumber", pageNumber);
        }
        return "estudiante/index";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("estudiante", new Estudiante());
        return "estudiante/create";
    }
    @PostMapping("/save")
    public String save(Estudiante estudiante, BindingResult result, Model model, RedirectAttributes attributes){
        if (result.hasErrors()){
            model.addAttribute(estudiante);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "estudiante/create";
        }
        estudianteServices.CrearOeditar(estudiante);
        attributes.addFlashAttribute("msg", "Docente creado correctamente");
        return "redirect:/Estudiantes";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model){
        Estudiante estudiante = estudianteServices.BuscarPorId(id).get();
        model.addAttribute( "estudiante", estudiante);
        return "estudiante/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        Estudiante estudiante = estudianteServices.BuscarPorId(id).get();
        model.addAttribute("estudiante", estudiante);
        return "estudiante/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model){
        Estudiante estudiante = estudianteServices.BuscarPorId(id).get();
        model.addAttribute( "estudiante", estudiante);
        return "estudiante/delete";
    }

    @PostMapping("/delete")
    public String delete(Estudiante estudiante, RedirectAttributes attributes){
        estudianteServices.EliminarPorId(estudiante.getId());
        attributes.addFlashAttribute("msg", "Docente eliminado correctamente");
        return "redirect:/Estudiantes";
    }
}
