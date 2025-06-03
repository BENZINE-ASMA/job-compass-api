package com.dauphine.jobCompass.mapper;

import com.dauphine.jobCompass.dto.Category.CategoryDTO;
import com.dauphine.jobCompass.model.JobCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toDTO(JobCategory category);

    JobCategory toEntity(CategoryDTO categoryDTO);
}