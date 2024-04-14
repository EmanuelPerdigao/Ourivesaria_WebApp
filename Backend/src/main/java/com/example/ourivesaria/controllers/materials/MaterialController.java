package com.example.ourivesaria.controllers.materials;

import com.example.ourivesaria.dtos.materials.MaterialDto;
import com.example.ourivesaria.mappers.materials.MaterialMapper;
import com.example.ourivesaria.services.material.MaterialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/user/material")
public class MaterialController {


    final private MaterialService materialService;
    final private MaterialMapper materialMapper;

    public MaterialController(MaterialService materialService, MaterialMapper materialMapper) {
        this.materialService = materialService;

        this.materialMapper = materialMapper;
    }


    //#############################  LIST ALL MATERIALS AVAILABLE  ####################################
    @RequestMapping(method = RequestMethod.GET, path = {"/list"})
    public ResponseEntity<List<MaterialDto>> listMaterials() {

        List<MaterialDto> materialListDto = materialMapper.convertToDTOList(materialService.listAllMaterials());

        return new ResponseEntity<>(materialListDto, HttpStatus.OK);
    }


    //#############################  LIST ALL MATERIALS BY CATEGORY  ####################################
    @RequestMapping(method = RequestMethod.GET, path = {"/listByCategory/{categoryId}"})
    public ResponseEntity<List<MaterialDto>> listMaterialsByCategory(@PathVariable Long categoryId) {


        List<MaterialDto> materialListDtoByCategory = materialMapper.convertToDTOList(materialService.listAllMaterialsByCategoryId(categoryId));


        return new ResponseEntity<>(materialListDtoByCategory, HttpStatus.OK);
    }

}
