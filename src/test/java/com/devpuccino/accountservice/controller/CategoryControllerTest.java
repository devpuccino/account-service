package com.devpuccino.accountservice.controller;

import com.devpuccino.accountservice.entity.CategoryEntity;
import com.devpuccino.accountservice.repository.CategoryRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class CategoryControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    private WebApplicationContext context;
    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("{method-name}/{step}/"))
                .build();
    }


    @Test
    public void shouldResponseSuccessWhenInsertCategory() throws Exception {
        //Given
        String requestBody = """
                {
                    "category_name": "Waters",
                    "is_active": true
                }
                """;
        CategoryEntity expected = new CategoryEntity();
        expected.setCategoryName("Waters");
        expected.setActive(true);

        Mockito.when(categoryRepository.findByCategoryName(Mockito.anyString())).thenReturn(new ArrayList<>());

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                //Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200-000"))
                .andExpect(jsonPath("$.message").value("Success"));

        Mockito.verify(categoryRepository,Mockito.times(1))
                .save(expected);
    }

    @Test
    public void shouldResponseDuplicateDataErrorWhenInsertCategoryNameExist() throws Exception {
        //Give
        String requestBody = """
                {
                    "category_name": "Water",
                    "is_active": true
                }
                """;
        CategoryEntity entity = new CategoryEntity();
        entity.setCategoryName("Water");
        entity.setActive(true);

        List<CategoryEntity> categoryEntityList = Arrays.asList(entity);

        Mockito.when(categoryRepository.findByCategoryName(Mockito.anyString())).thenReturn(categoryEntityList);
        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        mockMvc.perform(request).andDo(MockMvcResultHandlers.print())
                // then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400-001"))
                .andExpect(jsonPath("$.message").value("Duplicate data"));
    }

    @Test
    public void shouldResponseInternalServerErrorWhenInsertCategoryThenDatabaseException() throws Exception {
        String requestBody = """
                {
                    "category_name": "Water",
                    "is_active": true
                }
                """;
        Mockito.when(categoryRepository.findByCategoryName(Mockito.anyString())).thenThrow(new Exception());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody);
        mockMvc.perform(request)
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("500-001"))
                .andExpect(jsonPath("$.message").value("Internal server error"));
    }

    @Test
    public void shouldResponseCategoryListWhenGetAllCategory() throws Exception {

        List<CategoryEntity> categoryEntityList = new ArrayList<>();

        CategoryEntity categoryEntity1 = new CategoryEntity();
        categoryEntity1.setId(1);
        categoryEntity1.setCategoryName("Coffee");
        categoryEntity1.setActive(true);
        categoryEntityList.add(categoryEntity1);

        CategoryEntity categoryEntity2 = new CategoryEntity();
        categoryEntity2.setId(2);
        categoryEntity2.setCategoryName("Internet");
        categoryEntity2.setActive(false);
        categoryEntityList.add(categoryEntity2);

        Mockito.when(categoryRepository.findAll()).thenReturn(categoryEntityList);
        //when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/category")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200-000"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data[0].id").value("1"))
                .andExpect(jsonPath("$.data[0].category_name").value("Coffee"))
                .andExpect(jsonPath("$.data[0].active").value(true))
                .andExpect(jsonPath("$.data[1].id").value("2"))
                .andExpect(jsonPath("$.data[1].category_name").value("Internet"))
                .andExpect(jsonPath("$.data[1].active").value(false));
    }

    @Test
    public void shouldResponseEmptyCategoryListWhenGetAllCategory() throws Exception {
        Mockito.when(categoryRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/category")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200-000"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isEmpty());
    }
    @Test
    public void shouldResponseCategoryWhenGetById() throws Exception {
        CategoryEntity category = new CategoryEntity();
        category.setId(1);
        category.setCategoryName("Coffee");
        category.setActive(true);
        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(category));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/category/1")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200-000"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.data.category_name").value("Coffee"))
                .andExpect(jsonPath("$.data.active").value(true));
    }
    @Test
    public void shouldResponseDataNotFoundWhenGetByIdThatNotExistInDatabase() throws Exception {
        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/category/1")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404-001"))
                .andExpect(jsonPath("$.message").value("Data not found"))
                .andExpect(jsonPath("$.data").value(Matchers.nullValue()));

    }

    @Test
    public void shouldResponseSuccessWhenDeleteCategory() throws Exception {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(1);
        entity.setCategoryName("Coffee");
        entity.setActive(true);
        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(entity));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/category/1")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200-000"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data").value(Matchers.nullValue()));

        CategoryEntity expectedEntity = new CategoryEntity();
        expectedEntity.setId(entity.getId());
        expectedEntity.setCategoryName(entity.getCategoryName());
        expectedEntity.setActive(false);

        Mockito.verify(categoryRepository,Mockito.times(1)).save(expectedEntity);
    }
    @Test
    public void shouldResponseDataNotFoundWhenDeleteCategoryById() throws Exception {
        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/category/1")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404-001"))
                .andExpect(jsonPath("$.message").value("Data not found"))
                .andExpect(jsonPath("$.data").value(Matchers.nullValue()));

        Mockito.verify(categoryRepository,Mockito.times(0)).save(Mockito.any(CategoryEntity.class));
    }
}