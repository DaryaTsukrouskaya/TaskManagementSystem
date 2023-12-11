package ru.effectivemobile.taskmanagementsystem;

import com.c4_soft.springaddons.security.oauth2.test.annotations.OpenIdClaims;
import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockJwtAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import ru.effectivemobile.taskmanagementsystem.dto.CreateTaskDto;
import ru.effectivemobile.taskmanagementsystem.dto.TaskDto;
import ru.effectivemobile.taskmanagementsystem.dto.converters.TaskConverter;
import ru.effectivemobile.taskmanagementsystem.services.TaskService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@ComponentScan(basePackages = "ru.effectivemobile.taskmanagementsystem.*")
@WithMockJwtAuth(authorities = {"USER"}, claims = @OpenIdClaims(preferredUsername = "Anton Pirate"))
class TaskControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    TaskConverter taskConverter;

    @MockBean
    private TaskService taskService;

    private static final String END_POINT_PATH_CREATE = "/task/create";

    @Test
    @WithMockJwtAuth(authorities = {"USER"}, claims = @OpenIdClaims(preferredUsername = "Anton Pirate"))
    public void testAddShouldReturn201Created() throws Exception {
        CreateTaskDto taskDto = new CreateTaskDto();
        taskDto.setTitle("Домашнее задание");
        taskDto.setDescription("описание");
        taskDto.setStatus("назначено");
        taskDto.setPriority("средний");
        taskDto.setPerformers(List.of("darya.raikhert.31@mail.ru"));
        TaskDto dto = new TaskDto();
        Mockito.when(taskService.createTask(taskDto)).thenReturn(dto);
        String requestBody = objectMapper.writeValueAsString(taskDto);
        mockMvc.perform(post(END_POINT_PATH_CREATE).contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated());
    }

}

