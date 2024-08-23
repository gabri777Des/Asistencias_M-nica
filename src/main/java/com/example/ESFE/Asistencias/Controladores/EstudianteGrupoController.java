package com.example.ESFE.Asistencias.Controladores;

import com.example.ESFE.Asistencias.Entidades.*;
import com.example.ESFE.Asistencias.Servicios.Interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/EstudiantesGrupos")
public class EstudianteGrupoController {

    @Autowired
    private IEstudianteGrupoServices estudianteGrupoServices;
    @Autowired
    private IEstudianteServices estudianteServices;
    @Autowired
    private IGrupoServices grupoServices;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<EstudianteGrupo> estudianteGrupos = estudianteGrupoServices.BuscarTodosPaginados(pageable);
        model.addAttribute("estudianteGrupo", estudianteGrupos);

        int totalPages = estudianteGrupos.getTotalPages();
        if (totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "estudianteGrupo/index";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("estudianteGrupo", new EstudianteGrupo());
        model.addAttribute("estudiantes", estudianteServices.ObtenerTodos());
        model.addAttribute("grupos", grupoServices.ObtenerTodos());
        return "estudianteGrupo/create";
    }

    @PostMapping("/save")
    public String save(@RequestParam Integer estudianteId,
                       @RequestParam Integer grupoId,
                       RedirectAttributes attributes){
        Estudiante estudiante = estudianteServices.BuscarPorId(estudianteId).get();
        Grupo grupo = grupoServices.BuscarPorId(grupoId).get();

        if (estudiante != null && grupo != null){
            EstudianteGrupo estudianteGrupo = new EstudianteGrupo();
            estudianteGrupo.setEstudiante(estudiante);
            estudianteGrupo.setGrupo(grupo);
            estudianteGrupoServices.CrearOeditar(estudianteGrupo);
            attributes.addFlashAttribute("msg", "Estudiante grupo creado");
        }
        return "redirect:/EstudiantesGrupos";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model){
        EstudianteGrupo estudianteGrupo = estudianteGrupoServices.BuscarPorId(id).get();
        model.addAttribute("estudianteGrupo", estudianteGrupo);
        return "estudianteGrupo/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        EstudianteGrupo estudianteGrupo = estudianteGrupoServices.BuscarPorId(id).get();
        model.addAttribute("estudiantes", estudianteServices.ObtenerTodos());
        model.addAttribute("grupos", grupoServices.ObtenerTodos());
        model.addAttribute("estudianteGrupo", estudianteGrupo);
        return "estudianteGrupo/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model){
        EstudianteGrupo estudianteGrupo = estudianteGrupoServices.BuscarPorId(id).get();
        model.addAttribute("estudianteGrupo", estudianteGrupo);
        return "estudianteGrupo/delete";
    }

    @PostMapping("/delete")
    public String delete(EstudianteGrupo estudianteGrupo, RedirectAttributes attributes){
        estudianteGrupoServices.EliminarPorId(estudianteGrupo.getId());
        attributes.addFlashAttribute("msg", "Asignación eliminada correctamente");
        return "redirect:/EstudiantesGrupos";
    }

    @PostMapping("/update")
    public String update(@RequestParam Integer id, @RequestParam Integer estudianteId,
                         @RequestParam Integer grupoId,
                         RedirectAttributes attributes){
        Estudiante estudiante = estudianteServices.BuscarPorId(estudianteId).get();
        Grupo grupo = grupoServices.BuscarPorId(grupoId).get();

        if (estudiante != null && grupo != null){
            EstudianteGrupo estudianteGrupo = new EstudianteGrupo();
            estudianteGrupo.setId(id);
            estudianteGrupo.setEstudiante(estudiante);
            estudianteGrupo.setGrupo(grupo);

            estudianteGrupoServices.CrearOeditar(estudianteGrupo);
            attributes.addFlashAttribute("msg", "Asignación guardada correctamente");
        }
        return "redirect:/EstudiantesGrupos";
    }

}
